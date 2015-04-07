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

import hu.nemes.projecteuler.common.Arithmetic;

import java.util.concurrent.Callable;

public final class Problem091 implements Callable<Long> {

	/**
	 * Right triangles with integer coordinates
	 * Problem 91
	 *
	 * The points P (x1, y1) and Q (x2, y2) are plotted at integer co-ordinates and are joined to the origin, O(0,0), to form ΔOPQ.
	 *
	 * There are exactly fourteen triangles containing a right angle that can be formed when each co-ordinate lies between 0 and 2 inclusive; that is,
	 * 0 ≤ x1, y1, x2, y2 ≤ 2.
	 *
	 * Given that 0 ≤ x1, y1, x2, y2 ≤ 50, how many right triangles can be formed?
	 */
	@Override
	public Long call() {
		final int size = 50;

		long result = size * size * 3;

		for (long x = 1; x <= size; x++) {
		    for (long y = 1; y <= x; y++) {
		    	final long fact = Arithmetic.gcd(x, y);
		    	result += Math.min((y*fact)/x, ((size - x)*fact)/y) * 2;
		        if (x != y) {
		        	result += Math.min((x*fact)/y, ((size - y)*fact)/x) * 2;
		        }
		    }
		}
		return result;
	}
}
