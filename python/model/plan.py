import heapq
import sys
import itertools

'''
Implemented with guidance from https://docs.python.org/2/library/heapq.html#module-heapq
'''
class PriorityQueue(object):

    REMOVED = '<removed>'

    def __init__(self):
        self.pq = []
        self.entry_finder = {}
        self.length = 0
        self.counter = itertools.count()

    def add(self, vertex, weight):
        if vertex in self.entry_finder:
            self.remove(vertex)
        count = next(self.counter)
        entry = [weight, count, vertex]

        self.entry_finder[vertex] = entry
        heapq.heappush(self.pq, entry)
        self.length += 1

    def remove(self, vertex):
        entry = self.entry_finder.pop(vertex)
        entry[-1] = PriorityQueue.REMOVED
        self.length -= 1

    def pop(self):
        while self.pq:
            priority, count, vertex = heapq.heappop(self.pq)
            if vertex is not PriorityQueue.REMOVED:
                self.length -= 1
                del self.entry_finder[vertex]
                return vertex
        raise KeyError('pop from an empty priority queue')

    def __len__(self):
        return self.length


class FlightPlan(object):

    def __init__(self, allSegments):
        self.allSegments = allSegments
        self.jetStreamSegments = []
        self.totalEnergy = 0

        for segment in allSegments:
            if not segment.manual:
                self.jetStreamSegments.append(segment)
            self.totalEnergy += segment.energyRequired

class FlightPlanCalculator(object):

    def __init__(self):
        self.distances = {}
        self.edges = {}
        self.queue = PriorityQueue()
        self.adjacencies = {}

    def calculate(self, possibleSegments, startingPosition, endingPosition):
        self.edges = {}

        self.adjacencies = {}
        self.populateAdjacencyMatrix(possibleSegments)

        self.distances = {startingPosition: 0}

        self.queue = PriorityQueue()
        self.queue.add(startingPosition, 0)

        self.buildEdgeGraph()

        return FlightPlan(self.pathTo(endingPosition))

    def buildEdgeGraph(self):
        while self.queue:
            vertex = self.queue.pop()

            for segment in self.getAdjacencies(vertex):
                self.relax(segment)

    def pathTo(self, vertex):
        path = []

        edge = self.edges[vertex]
        while (edge):
            path.insert(0, edge)
            edge = self.edges.get(edge.start)

        return path

    def relax(self, segment):
        distancetoEnd = self.distance(segment.end)
        distanceToStart = self.distance(segment.start)

        calculatedDistance = distanceToStart + segment.energyRequired

        if distancetoEnd > calculatedDistance:
            self.distances[segment.end] = calculatedDistance
            self.edges[segment.end] = segment

            self.queue.add(segment.end, distancetoEnd)

    def populateAdjacencyMatrix(self, segments):
        for segment in segments:
            self.getAdjacencies(segment.start).append(segment)

    def getAdjacencies(self, vertex):
        if vertex not in self.adjacencies:
            self.adjacencies[vertex] = []

        return self.adjacencies[vertex]

    def distance(self, vertex):
        if vertex in self.distances:
            return self.distances[vertex]

        return sys.maxint