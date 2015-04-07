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

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.concurrent.Callable;
import java.util.stream.LongStream;

public final class Problem037 implements Callable<Long> {

	/**
	 * Truncatable primes
	 * Problem 37
	 *
	 * The number 3797 has an interesting property. Being prime itself, it is possible to continuously remove digits from left to right, and remain prime at each stage: 3797, 797, 97, and 7. Similarly we can work from right to left: 3797, 379, 37, and 3.
	 *
	 * Find the sum of the only eleven primes that are both truncatable from left to right and right to left.
	 *
	 * NOTE: 2, 3, 5, and 7 are not considered to be truncatable primes.
	 */
	@Override
	public Long call() {

		final long[] postfixes = LongStream.of(1, 3, 7, 9).toArray();
		final Deque<long[]> rightTruncPrimes = new ArrayDeque<>();
		rightTruncPrimes.add(Primes.makePrimeStreamUntil(10).toArray());

		long[] lastRightTruncPrimes;
		while ((lastRightTruncPrimes = rightTruncPrimes.peekLast()).length > 0) {
			rightTruncPrimes.add(Arrays
				.stream(lastRightTruncPrimes)
				.flatMap(n -> Arrays
						.stream(postfixes)
						.map(f -> (n * 10) + f)
						.filter(Primes::isPrime))
				.toArray());
		};

		return rightTruncPrimes
				.stream()
				.parallel()
				.flatMapToLong(Arrays::stream)
				.filter(n -> n > 9)
				.filter(n -> {
					int i = 10;
					while (i < n) {
						i *= 10;
					}

					while ((i /= 10) > 1) {
						if (!Primes.isPrime(n %= i)) {
							return false;
						}
					};
					return true;
				})
				.sum();
	}
}
