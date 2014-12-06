package com.endgame.coconuts.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class SegmentSource {

    private Collection<Segment> segments;
    private int energyConstant;
    private int firstMile = Integer.MAX_VALUE;
    private int lastMile = Integer.MIN_VALUE;

    public SegmentSource(Reader input) throws IOException {
        segments = new ArrayList<>();

        parse(input);
        addMissingSegments();
    }

    public Collection<Segment> getSegments() {
        return segments;
    }

    public int getEnergyConstant() {
        return energyConstant;
    }

    public int getLastMile() {
        return lastMile;
    }

    protected void parse(Reader input) throws IOException {
        try (BufferedReader reader = new BufferedReader(input)) {

            String line = reader.readLine();

            energyConstant = Integer.parseInt(line.trim());

            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split(" ");

                int start = Integer.parseInt(parts[0].trim());
                int end = Integer.parseInt(parts[1].trim());
                int energyRequired = Integer.parseInt(parts[2].trim());

                lastMile = Math.max(end, lastMile);
                firstMile = Math.min(start, firstMile);

                segments.add(new Segment(start, end, energyRequired));
            }
        }
    }

    protected void addMissingSegments() {
        LinkedList<Segment> processQueue = new LinkedList(segments);

        Collections.sort(processQueue, new Comparator<Segment>() {
            @Override
            public int compare(Segment o1, Segment o2) {
                return o1.getStart() - o2.getStart();
            }
        });

        List<Segment> missingSegments = new ArrayList();

        if (firstMile != 0) {
            int cost = calculateFlightCost(0, firstMile);
            missingSegments.add(new Segment(0, firstMile, cost, true));
        }

        while (!processQueue.isEmpty()) {
            Segment segment = processQueue.remove();

            for (Segment next : processQueue) {
                if (next.getStart() > segment.getEnd()) {
                    int cost = calculateFlightCost(segment.getEnd(), next.getStart());
                    missingSegments.add(new Segment(segment.getEnd(), next.getStart(), cost, true));
                }
            }
        }

        segments.addAll(missingSegments);
    }

    protected int calculateFlightCost(int start, int end) {
        int milesFlown = end - start;

        return energyConstant * milesFlown;
    }

}
