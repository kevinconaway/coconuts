package com.endgame.model;

import com.endgame.coconuts.model.FlightPlan;
import com.endgame.coconuts.model.FlightPlanCalculator;
import com.endgame.coconuts.model.Segment;
import com.endgame.coconuts.model.SegmentSource;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStreamReader;
import java.util.Iterator;

public class FlightPlanCalculatorTestCase {

    private FlightPlanCalculator calculator = new FlightPlanCalculator();

    @Test
    public void testSamplePaths() throws Exception {
        SegmentSource segmentSource = new SegmentSource(new InputStreamReader(getClass().getResourceAsStream("/sample_paths.txt")));

        FlightPlan plan = calculator.calculate(segmentSource.getSegments(), 0, segmentSource.getLastMile());

        Iterator<Segment> segments = plan.getJetStreamSegments().iterator();

        Assert.assertEquals(352, plan.getTotalEnergy());

        Assert.assertEquals(new Segment(0, 5, 10), segments.next());
        Assert.assertEquals(new Segment(6, 11, 20), segments.next());
        Assert.assertEquals(new Segment(14, 17, 8), segments.next());
        Assert.assertEquals(new Segment(19, 24, 14), segments.next());
        Assert.assertFalse(segments.hasNext());
    }

    @Test
    public void testThatExpensiveJetStreamIsSkipped() throws Exception {
        SegmentSource segmentSource = new SegmentSource(new InputStreamReader(getClass().getResourceAsStream("/test_path.txt")));

        FlightPlan plan = calculator.calculate(segmentSource.getSegments(), 0, segmentSource.getLastMile());

        Iterator<Segment> segments = plan.getJetStreamSegments().iterator();

        Assert.assertEquals(15, plan.getTotalEnergy());

        Assert.assertEquals(new Segment(0, 5, 5), segments.next());
        Assert.assertEquals(new Segment(10, 15, 5), segments.next());
        Assert.assertFalse(segments.hasNext());
    }

}
