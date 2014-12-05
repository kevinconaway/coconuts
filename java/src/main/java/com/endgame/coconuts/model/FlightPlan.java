package com.endgame.coconuts.model;

import java.util.ArrayList;
import java.util.List;

public class FlightPlan {

    private final List<Segment> segments;
    private int totalEnergy;
    private Segment currentSegment;

    public FlightPlan() {
        this.segments = new ArrayList<>();
    }

    public int getTotalEnergy() {
        int result = totalEnergy;

        for (Segment segment : segments) {
            result = result + segment.getEnergyRequired();
        }

        return result;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    void complete() {
        if (currentSegment != null) {
            segments.add(currentSegment);
        }
    }

    void addEnergy(int energy) {
        totalEnergy = totalEnergy + energy;
    }

    void setCurrentSegment(Segment newCurrentSegment) {
        if (currentSegment != null) {
            segments.add(currentSegment);
        }

        currentSegment = newCurrentSegment;
    }

    void replaceCurrentSegment(Segment newCurrentSegment) {
        currentSegment = newCurrentSegment;
    }
}
