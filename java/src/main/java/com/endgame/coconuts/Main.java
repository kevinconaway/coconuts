package com.endgame.coconuts;

import com.endgame.coconuts.model.FlightPlan;
import com.endgame.coconuts.model.FlightPlanCalculator;
import com.endgame.coconuts.model.SegmentSource;

import java.io.File;
import java.io.FileReader;

public class Main {

    public static void main(String [] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Please provide an input file name");
            return;
        }

        System.out.print("Reading in segments...");
        SegmentSource segmentSource = new SegmentSource(new FileReader(new File(args[0])));
        System.out.println("done!");

        System.out.print("Calculating flight plan...");
        FlightPlanCalculator flightPlanCalculator = new FlightPlanCalculator();
        FlightPlan plan = flightPlanCalculator.calculate(segmentSource.getSegments(), 0, segmentSource.getLastMile());
        System.out.println("done!");

        System.out.println("Jetstream segments: "+plan.getJetStreamSegments());
        System.out.println("Total flight cost: "+plan.getTotalEnergy());
    }

}
