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

public final class Problem094 implements Callable<Long> {

	/**
	 * Almost equilateral triangles
	 * Problem 94
	 *
	 * It is easily proved that no equilateral triangle exists with integral length sides and integral area. However, the almost equilateral triangle 5-5-6 has an area of 12 square units.
	 *
	 * We shall define an almost equilateral triangle to be a triangle for which two sides are equal and the third differs by no more than one unit.
	 *
	 * Find the sum of the perimeters of all almost equilateral triangles with integral side lengths and area and whose perimeters do not exceed one billion (1,000,000,000).
	 */
	@Override
	public Long call() {
		long x = 2;
		long y = 1;
		final long limit = 1_000_000_000;
		long result = 0;
		double a;

		while (true) {

		    // b = a-1
		    long aTimes3 = (2 * x) + 1;
		    long areaTimes3 = y * (x + 2);
		    if (aTimes3 > limit) {
				break;
			}

		    if ((areaTimes3 > 0) &&
		    	((a = aTimes3 / 3d) == (long) a) &&
		        ((areaTimes3 % 3) == 0)) {
		        result += (3 * a) + 1;
		    }

		    //b = a+1
		    aTimes3 -= 2;
		    areaTimes3 -= 4 * y;

		    if ((areaTimes3 > 0) &&
		    	((a = aTimes3 / 3d) == (long) a) &&
		        ((areaTimes3 % 3) == 0)) {
		        result += (3 * a) - 1;
		    }

		    final long nextx = (2 * x) + (y * 3);
		    final long nexty = (y * 2) + x;

		    x = nextx;
		    y = nexty;
		}

		return result;
	}
}
