package com.endgame.coconuts.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class JetStream {

    private final Set<Segment> segments;

    public JetStream() {
        segments = new TreeSet<>(new Comparator<Segment>() {

            @Override
            public int compare(Segment first, Segment second) {
                return first.getStart() - second.getStart();
            }
        });
    }

    public void addSegment(int energyRequired, int start, int end) {
        segments.add(new Segment(energyRequired, start, end));
    }

    public Set<Segment> getSegments() {
        return Collections.unmodifiableSet(segments);
    }
}
