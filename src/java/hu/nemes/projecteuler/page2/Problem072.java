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

public final class Problem072 implements Callable<Long> {

	/**
	 * Counting fractions
	 * Problem 72
	 *
	 * Consider the fraction, n/d, where n and d are positive integers. If n<d and HCF(n,d)=1, it is called a reduced proper fraction.
	 *
	 * If we list the set of reduced proper fractions for d ≤ 8 in ascending order of size, we get:
	 *
	 * 1/8, 1/7, 1/6, 1/5, 1/4, 2/7, 1/3, 3/8, 2/5, 3/7, 1/2, 4/7, 3/5, 5/8, 2/3, 5/7, 3/4, 4/5, 5/6, 6/7, 7/8
	 *
	 * It can be seen that there are 21 elements in this set.
	 *
	 * How many elements would be contained in the set of reduced proper fractions for d ≤ 1,000,000?
	 */
	@Override
	public Long call() {
		final int limit = 1_000_000;
		final int[] phi = IntStream.rangeClosed(0, limit).toArray();
		long result = 0;
		for (int i = 2; i <= limit; i++) {
		    if (phi[i] == i) {
		        for (int j = i; j <= limit; j += i) {
		            phi[j] = (phi[j] / i) * (i - 1);
		        }
		    }
		    result += phi[i];
		}

		return result;
	}
}
