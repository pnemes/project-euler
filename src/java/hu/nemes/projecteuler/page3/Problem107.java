/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Peter Nemes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package hu.nemes.projecteuler.page3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public final class Problem107 implements Callable<Long> {

	/**
	 * Minimal network
	 * Problem 107
	 *
	 * The following undirected network consists of seven vertices and twelve edges with a total weight of 243.
	 *
	 * The same network can be represented by the matrix below.
	 *     	A	B	C	D	E	F	G
	 * A	-	16	12	21	-	-	-
	 * B	16	-	-	17	20	-	-
	 * C	12	-	-	28	-	31	-
	 * D	21	17	28	-	18	19	23
	 * E	-	20	-	18	-	-	11
	 * F	-	-	31	19	-	-	27
	 * G	-	-	-	23	11	27	-
	 *
	 * However, it is possible to optimize the network by removing some edges and still ensure that all points on the network remain connected. The network which achieves the maximum saving is shown below. It has a weight of 93, representing a saving of 243 − 93 = 150 from the original network.
	 *
	 * Using network.txt (right click and 'Save Link/Target As...'), a 6K text file containing a network with forty vertices, and given in matrix form, find the maximum saving which can be achieved by removing redundant edges whilst ensuring that the network remains connected.
	 */
	@Override
	public Long call() throws NumberFormatException, IOException {

		final NavigableSet<int[]> edges = new TreeSet<>(Comparator
				.<int[]>comparingInt(e -> e[2])	// weight
				.thenComparingInt(e -> e[1])	// j
				.thenComparingInt(e -> e[0]));	// i
		final BufferedReader reader = new BufferedReader(new InputStreamReader(Problem107.class.getResourceAsStream("network.txt")));

		int weight;
		long initialWeight = 0;
		int i = 0;
		String line;
		String data[];
		while ((line = reader.readLine()) != null) {
			data = line.split(",");
	    	for (int j = 0; j<i; j++) {
	    		if (!data[j].equals("-")) {
	    			initialWeight += (weight = Integer.parseInt(data[j]));
	    			edges.add(new int[]{i, j, weight});
	    		}
	    	}
			i++;
		}

		int[] edge;
		long spanningWeight = 0;
		final BitSet totalEdges = new BitSet(i);
		final Collection<BitSet> spanningEdges = new ArrayList<>();
		while (((spanningEdges.size() != 1) || ((totalEdges.cardinality()) < i)) && ((edge = edges.pollFirst()) != null)) {

			final BitSet newSet = new BitSet(i);
			newSet.set(edge[0]);
			newSet.set(edge[1]);

			final List<BitSet> sets = spanningEdges
					.stream()
					.filter(spanningEdge -> spanningEdge.intersects(newSet))
					.collect(Collectors.toList());

			switch (sets.size()) {
				case 2: // connect two distinct spanning trees
					spanningWeight += edge[2];
					for (final BitSet oldSet : sets) {
						newSet.or(oldSet);
						spanningEdges.remove(oldSet);
					}
					spanningEdges.add(newSet);
					break;

				case 1: // add to a spanning tree (if any of the two trees is not yet contained)
					final BitSet oldSet = sets.get(0);
					newSet.andNot(oldSet);
					if (!newSet.isEmpty()) {
						spanningWeight += edge[2];
						oldSet.or(newSet);
						totalEdges.or(newSet);
					}
					break;

				case 0: // add a new spanning tree
					spanningWeight += edge[2];
					totalEdges.or(newSet);
					spanningEdges.add(newSet);
					break;

				default:

			}
		}

		return initialWeight - spanningWeight;
    }
}
