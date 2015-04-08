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
package hu.nemes.projecteuler.page2;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.stream.LongStream;

public final class Problem083 implements Callable<Long> {

	/**
	 * Path sum: four ways
	 * Problem 83
	 *
	 * NOTE: This problem is a significantly more challenging version of Problem 81.
	 *
	 * In the 5 by 5 matrix below, the minimal path sum from the top left to the bottom right, by moving left, right, up, and down, is indicated in bold red and is equal to 2297.
	 *
	 * Find the minimal path sum, in matrix.txt (right click and "Save Link/Target As..."), a 31K text file containing a 80 by 80 matrix, from the top left to the bottom right by moving left, right, up, and down.
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	private final static class Node implements Comparable<Node> {

		public final int x;
		public final int y;

//		final Node parent;
		final long cost;
		final long hCost;
		final long tCost;

		public Node(long[][] costs, long[][] hCosts, int x, int y, Node parent) {
			this.x = x;
			this.y = y;
//			this.parent = parent;
			cost = ((parent != null) ? parent.cost : 0) + costs[x][y];
			hCost = hCosts[x][y];
			tCost = cost + hCost;
		}

		@Override
		public int compareTo(Node o) {
			return (int) (o.tCost - tCost);
		}

		@Override
		public String toString() {
			return "["+ x + ", " + y + "]" + "=" + cost + "(" + hCost + ")" /*+ ((parent != null) ? (" <-" + parent.toString()) : "")*/;
		}
	}

	@Override
	public Long call() throws NumberFormatException, IOException, URISyntaxException {

		final long[][] data =
			Files
				.lines(new File(Problem083.class.getResource("matrix.txt").toURI()).toPath())
				.map(s -> s.split(","))
				.map(Arrays::stream)
				.map(s -> s.mapToLong(Long::parseLong))
				.map(LongStream::toArray)
				.toArray(x -> new long[x][]);

		final int length = data.length - 1;
		final long minH = Arrays
				.stream(data)
				.flatMapToLong(Arrays::stream)
				.min()
				.getAsLong();

		final long[][] hCost = new long[length+1][length+1];
		final Node[][] closed = new Node[length+1][length+1];
		int sortedOpenSize = 0;
		Node[] sortedOpen = new Node[64];

		for (int x=0; x<=length; x++) {
			final int len = hCost[x].length;
			for (int y=0; y<len; y++) {
				hCost[x][y] = ((((2 * length) + 1) - x - y) * minH);
			}
		}

		Node n = new Node(data, hCost, 0, 0, null);
		sortedOpen[sortedOpenSize++] = n;
		do {
			Arrays.sort(sortedOpen, 0, sortedOpenSize);
			do {
				n = sortedOpen[--sortedOpenSize];
			} while (closed[n.x][n.y] != null);

			closed[n.x][n.y] = n;
			if ((sortedOpenSize+4) >= sortedOpen.length) {

				sortedOpenSize = compact(closed, sortedOpen, sortedOpenSize);

				if (((sortedOpenSize * 4)/3) >= sortedOpen.length) {
					sortedOpen = Arrays.copyOf(sortedOpen, sortedOpen.length * 2);
				}
			}

			if ((n.x > 0) && (closed[n.x-1][n.y] == null)) {
				sortedOpen[sortedOpenSize++] = new Node(data, hCost, n.x-1, n.y, n);
			}
			if ((n.x < length) && (closed[n.x+1][n.y] == null)) {
				sortedOpen[sortedOpenSize++] = new Node(data, hCost, n.x+1, n.y, n);
			}
			if ((n.y > 0) && (closed[n.x][n.y-1] == null)) {
				sortedOpen[sortedOpenSize++] = new Node(data, hCost, n.x, n.y-1, n);
			}
			if ((n.y < length) && (closed[n.x][n.y+1] == null)) {
				sortedOpen[sortedOpenSize++] = new Node(data, hCost, n.x, n.y+1, n);
			}

		} while ((closed[length][length] == null) && (sortedOpenSize > 0));

		return closed[length][length].cost;
	}

	private int compact(Node[][] closed, Node[] sortedOpen, int sortedOpenSize) {
		int i = 0;
		int gap = -1;
		int start = -1;
		int end = -1;
		int gain = 0;

		do {
			final boolean isT = closed[sortedOpen[i].x][sortedOpen[i].y] != null;
			if (isT) {
				if (gap == -1) {
					gap = i;
				}
				else if (start != -1) {
					System.arraycopy(sortedOpen, start, sortedOpen, gap, (end = i) - start);
					gain = start - gap;
					gap += end - start;
					start = -1;
					end = -1;
				}
			}
			else if ((gap != -1) && (start == -1)) {
				start = i;
			}
		} while (++i < sortedOpenSize);

		if ((gap != -1) && (start != -1)) {
			System.arraycopy(sortedOpen, start, sortedOpen, gap, (end = i) - start);
			gain = start - gap;
		}

		sortedOpenSize -= gain;
		return sortedOpenSize;
	}
}
