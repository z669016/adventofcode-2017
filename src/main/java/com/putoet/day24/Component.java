package com.putoet.day24;

import java.util.Objects;

public class Component {
    public final int id;
    private int portOne, portTwo;

    public Component(int id, int portOne, int portTwo) {
        this.id = id;
        this.portOne = portOne;
        this.portTwo = portTwo;
    }

    public Component(Component component) {
        this.id = component.id;
        this.portOne = component.portOne;
        this.portTwo = component.portTwo;
    }

    public void reverse() {
        final int temp = portOne;
        portOne = portTwo;
        portTwo = temp;
    }

    public boolean hasPort(int port) {
        return portOne == port || portTwo == port;
    }

    public int one() { return portOne; }
    public int two() { return portTwo;
    }
    @Override
    public String toString() {
        return portOne + "/" + portTwo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Component component)) return false;
        return id == component.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
