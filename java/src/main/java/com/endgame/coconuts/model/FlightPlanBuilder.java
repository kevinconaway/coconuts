package com.endgame.coconuts.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;

public class FlightPlanBuilder {

    private final int energyConstant;

    public FlightPlanBuilder(int energyConstant) {
        this.energyConstant = energyConstant;
    }

    public FlightPlan build(int startPosition, Queue<Segment> segments) {
        FlightPlan flight = new FlightPlan();

        Segment currentSegment = new Segment(0, 0, -1);

        while (!segments.isEmpty()) {
            Segment nextSegment = findBestSegment(segments);

            if (currentSegment.getEnd() < nextSegment.getStart()) {
                int energyRequired = getFlightEnergyBetweenSegments(currentSegment, nextSegment);

                flight.addEnergy(energyRequired);
                flight.setCurrentSegment(nextSegment);

                currentSegment = nextSegment;
            } else if (currentSegment.getStart() == nextSegment.getStart() && nextSegment.getEnd() > currentSegment.getEnd()) {
                currentSegment = nextSegment;
                flight.replaceCurrentSegment(nextSegment);
            }

        }

        flight.complete();

        return flight;
    }

    protected int getFlightEnergyBetweenSegments(Segment start, Segment end) {
        int milesToFly = end.getStart() - start.getEnd();
        int energyRequired = energyConstant * milesToFly;

        return energyRequired;
    }

    protected Segment findBestSegment(Queue<Segment> segments) {
        Segment segment = segments.remove();

        List<Segment> candidates = new ArrayList<Segment>();
        candidates.add(segment);

        Segment nextSegment = null;
        boolean done = false;

        while (!done) {
            nextSegment = segments.peek();

            if (nextSegment != null && nextSegment.getStart() == segment.getStart()) {
                candidates.add(segments.remove());
            } else {
                done = true;
            }
        }

        if (nextSegment == null) {
            return findLowestCostCandidate(candidates);
        } else {
            return findBestCandidateToNextSegment(candidates, nextSegment);
        }

    }

    protected Segment findBestCandidateToNextSegment(Collection<Segment> candidates, Segment nextSegment) {
        Segment result = null;
        int bestCost = Integer.MAX_VALUE;

        for (Segment candidate : candidates) {
            int cost = Math.max(getFlightEnergyBetweenSegments(candidate, nextSegment), candidate.getEnergyRequired());

            if (cost < bestCost) {
                result = candidate;
                bestCost = cost;
            }

        }

        return result;
    }

    protected Segment findLowestCostCandidate(Collection<Segment> candidates) {
        Segment result = null;
        int bestCost = Integer.MAX_VALUE;

        for (Segment candidate : candidates) {
            if (candidate.getEnergyRequired() < bestCost) {
                result = candidate;
                bestCost = candidate.getEnergyRequired();
            }
        }

        return result;
    }

}
