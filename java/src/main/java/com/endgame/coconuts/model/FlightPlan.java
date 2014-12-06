package com.endgame.coconuts.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FlightPlan {

    private final List<Segment> allSegments;
    private final List<Segment> jetStreamSegments;
    private int totalEnergy = 0;

    public FlightPlan(Collection<Segment> allSegments) {
        this.allSegments = new ArrayList<>(allSegments);
        this.jetStreamSegments = new ArrayList<>();

        for (Segment segment : allSegments) {
            if (!segment.isManual()) {
                jetStreamSegments.add(segment);
            }

            totalEnergy += segment.getEnergyRequired();
        }
    }

    public List<Segment> getAllSegments() {
        return allSegments;
    }

    public List<Segment> getJetStreamSegments() {
        return jetStreamSegments;
    }

    public int getTotalEnergy() {
        return totalEnergy;
    }
}

