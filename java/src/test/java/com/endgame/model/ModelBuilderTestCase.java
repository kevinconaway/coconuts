package com.endgame.model;

import com.endgame.coconuts.model.ModelBuilder;
import com.endgame.coconuts.model.Segment;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;

public class ModelBuilderTestCase {

    @Test
    public void testParsSamplePaths() throws IOException {
        Reader reader = new InputStreamReader(getClass().getResourceAsStream("/sample_paths.txt"));

        ModelBuilder builder = new ModelBuilder(reader);

        Assert.assertEquals(50, builder.getEnergyConstant());

        Iterator<Segment> segments = builder.getSegments().iterator();

        Assert.assertEquals(new Segment(0, 5, 10), segments.next());
        Assert.assertEquals(new Segment(1, 3, 5), segments.next());
        Assert.assertEquals(new Segment(3, 7, 12), segments.next());
        Assert.assertEquals(new Segment(6, 11, 20), segments.next());
        Assert.assertEquals(new Segment(14, 17, 8), segments.next());
        Assert.assertEquals(new Segment(19, 24, 14), segments.next());
        Assert.assertEquals(new Segment(21, 22, 2), segments.next());
        Assert.assertFalse(segments.hasNext());
    }
}
