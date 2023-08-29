package com.putoet.day13;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Firewall {
    public static class FirewallLayer implements Layer {
        private final int range;
        private int scanner = 0;
        private boolean caught = false;
        private boolean entered = false;
        private boolean down = true;

        public FirewallLayer(int range) {
            assert range > 1;

            this.range = range;
        }

        @Override
        public void reset() {
            scanner = 0;
            caught = false;
            entered = false;
            down = true;
        }

        @Override
        public void init(long delay) {
            delay = delay % (range * 2L - 2);
            while (delay > 0) {
                delay--;
                next();
            }
        }

        @Override
        public void next() {
            if (down) {
                scanner++;
                if (scanner == range) {
                    down = false;
                    scanner = range - 2;
                }
            } else {
                scanner--;

                if (scanner == -1) {
                    down = true;
                    scanner = 1;
                }
            }
        }

        @Override
        public void enter() {
            entered = true;
            if (!caught)
                caught = scanner == 0;
        }

        @Override
        public void leave() {
            entered = false;
        }

        @Override
        public boolean caught() {
            return caught;
        }

        @Override
        public int scanner() {
            return scanner;
        }

        @Override
        public int range() { return range; }

        @Override
        public String toString() {
            final var sb = new StringBuilder();
            for (int i = 0; i < range; i++) {
                if (i == scanner)
                    sb.append(i == 0 && entered ? "[@] " : "[.] ");
                else
                    sb.append(i == 0 && entered ? ".@. " : "... ");

            }

            return sb.toString();
        }
    }

    private final List<Layer> layers;

    private Firewall(List<Layer> layers) {
        this.layers = layers;
    }

    @Override
    public String toString() {
        final var sb = new StringBuilder();
        for (var idx = 0; idx < layers.size(); idx++)
            sb.append(idx).append(": ").append(layers.get(idx)).append("\n");

        return sb.toString();
    }

    public void pass() {
        pass(0);
    }

    public void pass(long delay) {
        layers.forEach(layer -> layer.init(delay));

        for (var position = 0; position < layers.size(); position++) {
            layers.get(position).enter();
            layers.forEach(Layer::next);
            layers.get(position).leave();

            // break the loop to speed things up
            if (delay > 0 && caught())
                return;
        }
    }

    public int severity() {
        var severity = 0;
        for (var idx = 0; idx < layers.size(); idx++) {
            final var layer = layers.get(idx);

            if (layers.get(idx).caught())
                severity += (idx * layer.range());
        }

        return severity;
    }

    public boolean caught() {
        return layers.stream().anyMatch(Layer::caught);
    }

    public void reset() {
        layers.forEach(Layer::reset);
    }

    public List<Layer> layers() { return Collections.unmodifiableList(layers); }

    public static Firewall of(List<String> lines) {
        final var layers = new ArrayList<Layer>();
        lines.forEach(line -> {
            final var numbers = line.split(": ");
            if (numbers.length != 2)
                throw new IllegalArgumentException("Invalid firewall definition '" + line + "'");

            final var depth = Integer.parseInt(numbers[0]);
            final var range = Integer.parseInt(numbers[1]);

            while (depth > layers.size())
                layers.add(emptyLayer());

            layers.add(new FirewallLayer(range));
        });

        return new Firewall(layers);
    }

    private static Layer emptyLayer() {
        return new Layer() {
            private boolean entered = false;

            @Override
            public void init(long delay) {}

            @Override
            public void reset() {
                entered = false;
            }

            @Override
            public void next() {}

            @Override
            public void enter() {
                entered = true;
            }

            @Override
            public void leave() {
                entered = false;
            }

            @Override
            public boolean caught() {
                return false;
            }

            @Override
            public String toString() {
                return entered ? "(.) " : "... ";
            }

            @Override
            public int scanner() {
                return -1;
            }

            @Override
            public int range() { return 0; }
        };
    }
}
