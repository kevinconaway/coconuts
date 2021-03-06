from model import segment
from model import plan
import time
import sys

if len(sys.argv) != 2:
    print 'Please provide an input file name'
else:
    print "Reading in segments...",
    sys.stdout.flush()

    source = segment.SegmentSource(input=sys.argv[1])
    print 'done!'

    print 'Calculating flight plan, this may take a minute or so...',
    sys.stdout.flush()

    start= time.time()
    flightPlanCalculator = plan.FlightPlanCalculator()
    flightPlan = flightPlanCalculator.calculate(source.segments, 0, source.lastMile)
    elapsed = int(time.time()) - int(start)

    print 'done in {0}s\n'.format(elapsed)

    print "JetStream Segments: ", [(s.start, s.end) for s in flightPlan.jetStreamSegments]
    print "Total flight cost: ", flightPlan.totalEnergy
