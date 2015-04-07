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

public final class Problem071 implements Callable<Long> {

	/**
	 * Ordered fractions
	 * Problem 71
	 *
	 * Consider the fraction, n/d, where n and d are positive integers. If n<d and HCF(n,d)=1, it is called a reduced proper fraction.
	 *
	 * If we list the set of reduced proper fractions for d ≤ 8 in ascending order of size, we get:
	 *
	 * 1/8, 1/7, 1/6, 1/5, 1/4, 2/7, 1/3, 3/8, 2/5, 3/7, 1/2, 4/7, 3/5, 5/8, 2/3, 5/7, 3/4, 4/5, 5/6, 6/7, 7/8
	 *
	 * It can be seen that 2/5 is the fraction immediately to the left of 3/7.
	 *
	 * By listing the set of reduced proper fractions for d ≤ 1,000,000 in ascending order of size, find the numerator of the fraction immediately to the left of 3/7.
	 */
	@Override
	public Long call() {
		final int limit = 1_000_000;

		final long a = 3;
		final long b = 7;

		long r = 0;
		long s = 1;

		long lowerbound = 2;
		int q = limit;

	    long p;
		while (q >= lowerbound) {
		    if (((p = ((a * q) - 1) / b) * s) > (r * q--)) {
		        lowerbound = (s = q + 1) / ((a * s) - (b * (r = p)));
		    }
		}

		return r /= Arithmetic.gcd(r, s);
	}
}
