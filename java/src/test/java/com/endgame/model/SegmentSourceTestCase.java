package com.endgame.model;

import com.endgame.coconuts.model.Segment;
import com.endgame.coconuts.model.SegmentSource;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;

public class SegmentSourceTestCase {

    @Test
    public void testParse() throws IOException {
        Reader reader = new InputStreamReader(getClass().getResourceAsStream("/test_path.txt"));

        SegmentSource builder = new SegmentSource(reader);

        Assert.assertEquals(1, builder.getEnergyConstant());
        Assert.assertEquals(15, builder.getLastMile());

        Collection<Segment> segments = builder.getSegments();

        Assert.assertEquals(6, segments.size());
        Assert.assertTrue(segments.contains(new Segment(0, 5, 5)));
        Assert.assertTrue(segments.contains(new Segment(5, 6, 1)));
        Assert.assertTrue(segments.contains(new Segment(5, 10, 5)));
        Assert.assertTrue(segments.contains(new Segment(6, 9, 300)));
        Assert.assertTrue(segments.contains(new Segment(9, 10, 1)));
        Assert.assertTrue(segments.contains(new Segment(10, 15, 5)));
    }

    @Test
    public void testMissingFirstSegment() throws IOException {
        Reader reader = new InputStreamReader(getClass().getResourceAsStream("/missing_first_segment_path.txt"));

        SegmentSource builder = new SegmentSource(reader);

        Assert.assertEquals(1, builder.getEnergyConstant());
        Assert.assertEquals(15, builder.getLastMile());

        Collection<Segment> segments = builder.getSegments();

        Assert.assertEquals(4, segments.size());
        Assert.assertTrue(segments.contains(new Segment(0, 6, 6)));
        Assert.assertTrue(segments.contains(new Segment(6, 9, 300)));
        Assert.assertTrue(segments.contains(new Segment(9, 10, 1)));
        Assert.assertTrue(segments.contains(new Segment(10, 15, 5)));
    }
}
