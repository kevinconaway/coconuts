package com.endgame.coconuts.model;

import java.util.*;

public class FlightPlanCalculator {

    private Map<Integer, Long> distances;
    private Map<Integer, Segment> edges;
    private PriorityQueue<PriorityKey> queue;
    private Map<Integer, List<Segment>> adjacencies;

    public FlightPlan calculate(Collection<Segment> possibleSegments, int startingPosition, int endingPosition) {
        edges = new HashMap<>();

        adjacencies = new HashMap<>();
        populateAdjacencyMatrix(possibleSegments);

        distances = new HashMap<>();
        distances.put(startingPosition, 0L);

        queue = new PriorityQueue<>();
        queue.add(new PriorityKey(startingPosition, distances.get(startingPosition)));

        buildEdgeGraph();

        return new FlightPlan(pathTo(endingPosition));
    }

    private void buildEdgeGraph() {
        while (!queue.isEmpty()) {
            PriorityKey key = queue.remove();
            for (Segment e : getAdjacencies(key.vertex)) {
                relax(e);
            }
        }
    }

    private Collection<Segment> pathTo(int vertex) {
        List<Segment> path = new ArrayList<Segment>();
        Segment edge = edges.get(vertex);

        while (edge != null) {
            path.add(0, edge);

            edge = edges.get(edge.getStart());
        }

        return path;
    }

    private long getDistance(int vertex) {
        Long distance = distances.get(vertex);

        if (distance == null) {
            distance = Long.MAX_VALUE;
        }

        return distance;
    }

    private void relax(Segment e) {
        long distanceToEnd = getDistance(e.getEnd());
        long distanceToStart = getDistance(e.getStart());

        long calculatedDistance = distanceToStart + e.getEnergyRequired();

        if (distanceToEnd > calculatedDistance) {
            distances.put(e.getEnd(), calculatedDistance);
            edges.put(e.getEnd(), e);

            PriorityKey key = new PriorityKey(e.getEnd(), distanceToEnd);
            if (queue.contains(key)) {
                queue.remove(key);
            }

            queue.add(key);
        }
    }

    private void populateAdjacencyMatrix(Collection<Segment> segments) {
        for (Segment segment : segments) {
            getAdjacencies(segment.getStart()).add(segment);
        }
    }

    private Collection<Segment> getAdjacencies(int vertex) {
        List<Segment> result = adjacencies.get(vertex);

        if (result == null) {
            result = new ArrayList<>();
            adjacencies.put(vertex, result);
        }

        return result;
    }

    private static class PriorityKey implements Comparable {

        public final int vertex;
        public long weight;

        public PriorityKey(int vertex, long weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        @Override
        public boolean equals(Object o) {
            PriorityKey other = (PriorityKey) o;

            return vertex == other.vertex;
        }

        @Override
        public int hashCode() {
            return new Long(vertex).hashCode();
        }

        @Override
        public int compareTo(Object o) {
            PriorityKey other = (PriorityKey) o;
            return new Long(weight).compareTo(new Long(((PriorityKey) o).weight));
        }
    }
}
