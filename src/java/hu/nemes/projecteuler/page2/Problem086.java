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

import java.util.concurrent.Callable;
import java.util.stream.IntStream;

public final class Problem086 implements Callable<Long> {

	/**
	 * Cuboid route
	 * Problem 86
	 *
	 * A spider, S, sits in one corner of a cuboid room, measuring 6 by 5 by 3, and a fly, F, sits in the opposite corner. By travelling on the surfaces of the room the shortest "straight line" distance from S to F is 10 and the path is shown on the diagram.
	 *
	 * However, there are up to three "shortest" path candidates for any given cuboid and the shortest route doesn't always have integer length.
	 *
	 * It can be shown that there are exactly 2060 distinct cuboids, ignoring rotations, with integer dimensions, up to a maximum size of M by M by M, for which the shortest route has integer length when M = 100. This is the least value of M for which the number of solutions first exceeds two thousand; the number of solutions when M = 99 is 1975.
	 *
	 * Find the least value of M such that the number of solutions first exceeds one million.
	 */
	@Override
	public Long call() {
		final long target = 1_000_000;

		int i = 2;
		long n = 0;
		while (n < target) {
		    final int c = ++i;

		    n += IntStream
		    	.rangeClosed(3, 2*c)
		    	.map(ab -> {
		    		final double sqrt = Math.sqrt((ab * ab) + (c * c));
			        if (sqrt == (int)(sqrt)) {
			            return (ab <= c) ? ab / 2 : 1 + (c - ((ab+1)/2));
			        }
			        return 0;
		    	})
		    	.sum();
		}

		return (long) i;
	}
}
