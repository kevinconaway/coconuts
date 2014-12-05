package com.endgame.coconuts.model;

public class Segment {

    private final int start;
    private final int end;
    private final int energyRequired;

    public Segment(int start, int end, int energyRequired) {
        this.start = start;
        this.end = end;
        this.energyRequired = energyRequired;
    }

    public int getEnergyRequired() {
        return energyRequired;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "Segment["+start+"-"+end+". energyRequired: "+energyRequired+"]";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Segment)) {
            return false;
        }

        Segment other = (Segment) o;

        return start == other.start && end == other.end && energyRequired == other.energyRequired;
    }

    @Override
    public int hashCode() {
        return (energyRequired + start + end);
    }
}
