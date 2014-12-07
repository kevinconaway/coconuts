import unittest
from model import plan
from model import segment

class FlightPlanCalculatorTestCase(unittest.TestCase):

    def testSamplePaths(self):
        source = segment.SegmentSource(input='test/resources/sample_paths.txt')

        calculator = plan.FlightPlanCalculator()

        flightPlan = calculator.calculate(source.segments, 0, source.lastMile)

        self.assertEqual(352, flightPlan.totalEnergy)

        self.assertEquals(4, len(flightPlan.jetStreamSegments))

        self.assertTrue(segment.Segment(0, 5, 10) in source.segments)
        self.assertTrue(segment.Segment(6, 11, 20) in source.segments)
        self.assertTrue(segment.Segment(14, 17, 8) in source.segments)
        self.assertTrue(segment.Segment(19, 24, 14) in source.segments)

    def testThatExpensiveJetStreamIsSkipped(self):
        source = segment.SegmentSource(input='test/resources/test_path.txt')

        calculator = plan.FlightPlanCalculator()

        flightPlan = calculator.calculate(source.segments, 0, source.lastMile)

        self.assertEqual(15, flightPlan.totalEnergy)

        self.assertEquals(2, len(flightPlan.jetStreamSegments))

        self.assertTrue(segment.Segment(0, 5, 5) in source.segments)
        self.assertTrue(segment.Segment(10, 15, 5) in source.segments)