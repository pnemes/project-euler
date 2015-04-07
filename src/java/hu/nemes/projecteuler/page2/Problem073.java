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

public final class Problem073 implements Callable<Long> {

	/**
	 * Counting fractions in a range
	 * Problem 73
	 *
	 * Consider the fraction, n/d, where n and d are positive integers. If n<d and HCF(n,d)=1, it is called a reduced proper fraction.
	 *
	 * If we list the set of reduced proper fractions for d ≤ 8 in ascending order of size, we get:
	 *
	 * 1/8, 1/7, 1/6, 1/5, 1/4, 2/7, 1/3, 3/8, 2/5, 3/7, 1/2, 4/7, 3/5, 5/8, 2/3, 5/7, 3/4, 4/5, 5/6, 6/7, 7/8
	 *
	 * It can be seen that there are 3 fractions between 1/3 and 1/2.
	 *
	 * How many fractions lie between 1/3 and 1/2 in the sorted set of reduced proper fractions for d ≤ 12,000?
	 */
	@Override
	public Long call() {
		final int limit = 12_000;

		// Farey Sequences
		int a = 1;
		int b = 3;
		int c = 4000;
		int d = 11999;

		long result = 0;

		while (!((c == 1) && (d == 2))) {
		    result++;
		    final int k = (limit + b) / d;
		    final int e = (k * c) - a;
		    final int f = (k * d) - b;
		    a = c;
		    b = d;
		    c = e;
		    d = f;
		    /* or:
		    c = - (a - k * (a = c));
		    d = - (b - k * (b = d));
		    */
		}

		return result;
	}
}
