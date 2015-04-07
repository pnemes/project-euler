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

import hu.nemes.projecteuler.common.Arithmetic;
import hu.nemes.projecteuler.common.Primes;

import java.util.Arrays;
import java.util.concurrent.Callable;

public final class Problem035 implements Callable<Long> {

	/**
	 * Circular primes
	 * Problem 35
	 *
	 * The number, 197, is called a circular prime because all rotations of the digits: 197, 971, and 719, are themselves prime.
	 *
	 * There are thirteen such primes below 100: 2, 3, 5, 7, 11, 13, 17, 31, 37, 71, 73, 79, and 97.
	 *
	 * How many circular primes are there below one million?
	 */
	@Override
	public Long call() {
		final long[] primes = Primes
				.makePrimeStreamUntil(1_000_000)
				.parallel()
				// if any digits of the prime is even, it won't be a circular prime (except 2), so filter those out
				.filter(p -> ((p == 2) || !Arithmetic.isAnyDigits(p, d -> (d % 2) == 0)))
				.toArray();

		return Arrays
				.stream(primes)
				.parallel()
				.filter(p -> {
					final long log10p = (long) Math.log10(p);
					final long r10p = (long) Math.pow(10, log10p);

					for (int i = 0; i < log10p; i++) {
						p = ((p % 10) * r10p) + (p / 10);
						if (Arrays.binarySearch(primes, p) < 0) {
							return false;
						}
					}
					return true;
				})
				.count();
	}
}
