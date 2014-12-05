package com.endgame.model;

import com.endgame.coconuts.model.FlightPlan;
import com.endgame.coconuts.model.FlightPlanBuilder;
import com.endgame.coconuts.model.ModelBuilder;
import com.endgame.coconuts.model.Segment;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;

public class FlightPlanBuilderTestCase {

    @Test
    public void testFlightPlanForSamplePaths() throws Exception {
        Reader reader = new InputStreamReader(getClass().getResourceAsStream("/sample_paths.txt"));

        ModelBuilder modelBuilder = new ModelBuilder(reader);

        FlightPlanBuilder builder = new FlightPlanBuilder(modelBuilder.getEnergyConstant());
        FlightPlan plan = builder.build(0, modelBuilder.getSegments());

        Assert.assertEquals(352, plan.getTotalEnergy());

        Iterator<Segment> segments = plan.getSegments().iterator();

        Assert.assertEquals(new Segment(0, 5, 10), segments.next());
        Assert.assertEquals(new Segment(6, 11, 20), segments.next());
        Assert.assertEquals(new Segment(14, 17, 8), segments.next());
        Assert.assertEquals(new Segment(19, 24, 14), segments.next());
        Assert.assertFalse(segments.hasNext());
    }

    @Test
    public void testFlightPlanForMultipleStartPoints() throws Exception {
        Reader reader = new InputStreamReader(getClass().getResourceAsStream("/multiple_start_points.txt"));

        ModelBuilder modelBuilder = new ModelBuilder(reader);

        FlightPlanBuilder builder = new FlightPlanBuilder(modelBuilder.getEnergyConstant());
        FlightPlan plan = builder.build(0, modelBuilder.getSegments());

        System.out.println("\n\n");
        System.out.println(plan.getTotalEnergy());
        System.out.println(plan.getSegments());

        Assert.assertEquals(352, plan.getTotalEnergy());

        Iterator<Segment> segments = plan.getSegments().iterator();

        Assert.assertEquals(new Segment(0, 5, 10), segments.next());
        Assert.assertEquals(new Segment(6, 11, 20), segments.next());
        Assert.assertEquals(new Segment(14, 17, 8), segments.next());
        Assert.assertEquals(new Segment(19, 24, 14), segments.next());
        Assert.assertFalse(segments.hasNext());
    }

}
