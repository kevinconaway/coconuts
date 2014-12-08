I don't work with graphs  so I'll admit that the solution to this wasn't immediately obvious to me.

My first inclination was to read in all of the jet stream segments and sort them in order of the starting mile.  I would then iterate over that list of segments and have the bird hop between them (making up the difference between segments as necessary). 

This worked fine for the sample data but I started wondering what would happen if there were multiple jet stream segments at the same starting mile.  I updated the above algorithm to pick the "best" segment amongst all possible segments at a given starting point.  The "best" segment being the shortest distance to the next jet stream segment.

That worked OK too but then I started wondering about jet streams that may not be optimal at all.  What if the possibly insane king has a weather machine that makes flying in the jet stream the worse option?

For example, given an energy constant of 1 and the following jet stream segments:

    0 5 5
    6 9 300
    10 15 5

Its clearly better for the bird to fly around the segment from 6-9.

At this point, the pieces started clicking for me and I realized that this was a graphing problem and I needed to find the shortest path from the starting mile to the ending mile.  The jet stream segments provided in the input file only make up
part of the graph, I still needed to calculate the distances from the end of one segment to the beginning of each of the others.  Maybe its cheaper to bypass the jet streams completely and just flap all the way through!

After reading in the provided jet stream segments, I sorted them by the start mile.  I then iterated through each one and calculated the cost to fly to each of the remaining jet stream segments.  After doing so, I had a complete "graph" of the all the possible flight paths.

I remembered that Dijkstra's algorithm is one of the popular ways to find the shortest path in a graph so I started with [Wikipedia](http://en.wikipedia.org/wiki/Dijkstra%27s_algorithm).  I coded up a naive implementation that worked with the sample data but ran for a long time on flight_paths.txt.
It was then that I realized the Big O of the original algorithm is `O(V^2)` which meant it would probably take decades to complete.

Oops.

After some more research, I read the Dijkstra's algorithm can be implemented a lot more efficiently using a heap/priority queue so thats what I ended up with.  The code at [http://algs4.cs.princeton.edu/44sp/](http://algs4.cs.princeton.edu/44sp/) was very helpful as a reference in figuring out how the algorithm worked.  I adapted that for the provided solutions.

The Java solution uses the native `PriorityQueue` but it doesn't support updating the priority for a given key.  Thus we remove and re-add the element.  This slows down the implementation considerably but given that it was just my reference I was OK with it.

The python solution uses the [heapq](https://docs.python.org/2/library/heapq.html#module-heapq) module as a priority queue.  I used the provided code to allow the items in the queue to be re-weighted but it still runs pretty slow, about 2 minutes to calculate the graph on my machine.  I'm not sure if this is a result of my implementation or the heapq module.  I'm not thrilled with the performance here but I didn't have the time to investigate why its running as slow as it is.



