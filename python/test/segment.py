import unittest
from model import segment

class SegmentSourceTestCase(unittest.TestCase):

    def testParse(self):
        source = segment.SegmentSource(input='test/resources/test_path.txt')

        self.assertEqual(1, source.energyConstant)
        self.assertEqual(15, source.lastMile)

        self.assertEquals(6, len(source.segments))
        
        self.assertTrue(segment.Segment(0, 5, 5) in source.segments)
        self.assertTrue(segment.Segment(5, 6, 1) in source.segments)
        self.assertTrue(segment.Segment(5, 10, 5) in source.segments)
        self.assertTrue(segment.Segment(6, 9, 300) in source.segments)
        self.assertTrue(segment.Segment(9, 10, 1) in source.segments)
        self.assertTrue(segment.Segment(10, 15, 5) in source.segments)

    def testMissingFirstSegment(self):
        source = segment.SegmentSource(input='test/resources/missing_first_segment_path.txt')

        self.assertEqual(1, source.energyConstant)
        self.assertEqual(15, source.lastMile)

        self.assertEquals(4, len(source.segments))

        self.assertTrue(segment.Segment(0, 6, 6) in source.segments)
        self.assertTrue(segment.Segment(6, 9, 300) in source.segments)
        self.assertTrue(segment.Segment(9, 10, 1) in source.segments)
        self.assertTrue(segment.Segment(10, 15, 5) in source.segments)