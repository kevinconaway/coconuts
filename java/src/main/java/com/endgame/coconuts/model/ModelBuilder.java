package com.endgame.coconuts.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class ModelBuilder {

    private Queue<Segment> segments;
    private int energyConstant;

    public ModelBuilder(Reader input) throws IOException {
        parse(input);
    }

    public Queue<Segment> getSegments() {
        return segments;
    }

    public int getEnergyConstant() {
        return energyConstant;
    }

    protected void parse(Reader input) throws IOException {
        BufferedReader reader = new BufferedReader(input);

        String line = reader.readLine();

        energyConstant = Integer.parseInt(line.trim());

        segments = new LinkedList<>();

        while ((line = reader.readLine()) != null) {
            String [] parts = line.trim().split(" ");

            int start = Integer.parseInt(parts[0].trim());
            int end = Integer.parseInt(parts[1].trim());
            int energyRequired = Integer.parseInt(parts[2].trim());

            segments.add(new Segment(start, end, energyRequired));
        }

        Collections.sort((LinkedList) segments, new Comparator<Segment>() {
            @Override
            public int compare(Segment o1, Segment o2) {
                return o1.getStart() - o2.getStart();
            }
        });
    }


}
