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
package hu.nemes.projecteuler.page1;

import hu.nemes.projecteuler.common.Primes;

import java.util.concurrent.Callable;
import java.util.function.LongSupplier;

public final class Problem047 implements Callable<Long> {

	/**
	 * Distinct primes factors
	 * Problem 47
	 *
	 * The first two consecutive numbers to have two distinct prime factors are:
	 *
	 * 14 = 2 × 7
	 * 15 = 3 × 5
	 *
	 * The first three consecutive numbers to have three distinct prime factors are:
	 *
	 * 644 = 2² × 7 × 23
	 * 645 = 3 × 5 × 43
	 * 646 = 2 × 17 × 19.
	 *
	 * Find the first four consecutive integers to have four distinct prime factors. What is the first of these numbers?
	 */
	private final static long n = 4;

	@Override
	public Long call() {

		final long min = Primes
				.makePrimeStream()
				.limit(n)
				.reduce(1, (a, b) -> a * b);

		long i = min;
		long d;
		long j;
		do {
			j = 0;
			do {
				d = 0;
				long m = i - j;
				long p = 0;
				final LongSupplier primes = Primes.makePrimeSupplier();
				do {
					p = primes.getAsLong();
			        if ((m % p) == 0) {
				        do {
				        	m /= p;
				        } while ((m % p) == 0);
				        d++;
				        continue;
			        }
				} while (m > 1);
			} while ((d >= n) && (++j < n));
			i += n - j;
		} while ((j != n) || (d < n));

		return (i - j) + 1;
	}
}
