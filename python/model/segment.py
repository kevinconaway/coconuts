import sys
import fileinput

class Segment(object):

    def __init__(self, start, end, energyRequired, manual=False):
        self.start = start
        self.end = end
        self.energyRequired = energyRequired
        self.manual = manual

    def __hash__(self):
        return hash(self.start + self.end + self.energyRequired)

    def __eq__(self, other):
        return self.start == other.start and self.end == other.end and self.energyRequired == other.energyRequired

    def __repr__(self):
        if self.manual:
            return "ManualSegment["+str(self.start)+"-"+str(self.end)+". energyRequired: "+str(self.energyRequired)+"]"
        else:
            return "JetStreamSegment["+str(self.start)+"-"+str(self.end)+". energyRequired: "+str(self.energyRequired)+"]"


class SegmentSource(object):

    def __init__(self, input):
        self.segments = []
        self.firstMile = sys.maxint
        self.lastMile = -1
        self.energyConstant = 0

        self.parse(input)
        self.addMissingSegments()

    def parse(self, input):
        lines = fileinput.input(input)

        self.energyConstant = int(lines.next().strip())

        for line in lines:
            parts = line.strip().split(' ')
            start = int(parts[0])
            end = int(parts[1])
            energyRequired = int(parts[2])

            self.lastMile = max(end, self.lastMile)
            self.firstMile = min(start, self.firstMile)

            self.segments.append(Segment(start, end, energyRequired))

    def addMissingSegments(self):
        processQueue = self.segments[:]
        processQueue.sort(key=lambda x: x.start)

        missingSegments = []

        if (self.firstMile != 0):
            cost = self.calculateFlightCost(0, self.firstMile)
            missingSegments.append(Segment(0, self.firstMile, cost, True))

        while processQueue:
            segment = processQueue.pop(0)

            for nextSegment in processQueue:
                if nextSegment.start > segment.end:
                    cost = self.calculateFlightCost(segment.end, nextSegment.start)
                    missingSegments.append(Segment(segment.end, nextSegment.start, cost, True))

        self.segments.extend(missingSegments)

    def calculateFlightCost(self, start, end):
        milesFlown = end - start
        return self.energyConstant * milesFlown