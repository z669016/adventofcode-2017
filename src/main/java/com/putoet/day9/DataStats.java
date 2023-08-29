package com.putoet.day9;

record DataStats(int groupScore, int garbageLength, int excludedCounter) {
    public static DataStats parse(String input) {
        assert input != null;

        final var data = new DataTokenizer(input);

        StringBuilder garbage = null;
        int garbageLevel = 0;
        boolean excludeNext = false;
        int excludedCounter = 0;
        int garbageLength = 0;
        int groupLevel = 0;
        int groupScore = 0;

        while (data.hasNext()) {
            final var token = data.next();
            if (garbageLevel > 0) {
                if (garbage == null)
                    throw new IllegalStateException("Garbage cannot be null!");

                garbage.append(token.data());
            }

            if (excludeNext) {
                excludedCounter++;
                excludeNext = false;
            } else {
                switch (token.type()) {
                    case EXCLUDE_NEXT -> excludeNext = true;
                    case OPEN_GARBAGE -> {
                        if (garbageLevel == 0) {
                            garbage = new StringBuilder();
                            garbage.append(token.data());
                            garbageLevel = 1;
                        }
                    }
                    case CLOSE_GARBAGE -> {
                        if (garbageLevel > 0) {
                            garbageLength += garbage.length() - 2;
                            garbage = null;
                            garbageLevel = 0;
                        }
                    }
                    case OPEN_GROUP -> {
                        if (garbageLevel == 0)
                            groupLevel++;
                    }
                    case CLOSE_GROUP -> {
                        if (garbageLevel == 0) {
                            groupScore += groupLevel;
                            groupLevel--;
                        }
                    }
                }
            }
        }

        return new DataStats(groupScore, garbageLength, excludedCounter);
    }
}
