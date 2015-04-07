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

import hu.nemes.projecteuler.common.Primes;

import java.util.concurrent.Callable;
import java.util.function.LongSupplier;

public final class Problem077 implements Callable<Long> {

	/**
	 * Prime summations
	 * Problem 77
	 *
	 * It is possible to write ten as the sum of primes in exactly five different ways:
	 *
	 * 7 + 3
	 * 5 + 5
	 * 5 + 3 + 2
	 * 3 + 3 + 2 + 2
	 * 2 + 2 + 2 + 2 + 2
	 *
	 * What is the first value which can be written as the sum of primes in over five thousand different ways?
	 */
	@Override
	public Long call() {
		int n = 1;
		int[] ways;

		do {
			ways = new int[++n + 1];
		    ways[0] = 1;

		    int prime;
			final LongSupplier primes = Primes.makePrimeSupplier();

			while ((prime = (int) primes.getAsLong()) < n) {
		        for (int j = prime; j <= n; j++) {
		            ways[j] += ways[j - prime];
		        }
			}

		} while (ways[n] <= 5000);

		return (long) n;
	}
}
