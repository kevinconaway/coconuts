package com.endgame.coconuts.model;

public class Segment {

    private final int start;
    private final int end;
    private final int energyRequired;
    private final boolean manual;

    public Segment(int start, int end, int energyRequired, boolean manual) {
        this.start = start;
        this.end = end;
        this.energyRequired = energyRequired;
        this.manual = manual;
    }

    public Segment(int start, int end, int energyRequired) {
        this(start, end, energyRequired, false);
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

    public boolean isManual() {
        return manual;
    }

    @Override
    public String toString() {
        return manual ? "ManualSegment["+start+"-"+end+". energyRequired: "+energyRequired+"]" : "JetStreamSegment["+start+"-"+end+". energyRequired: "+energyRequired+"]";
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
