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

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public final class Problem027 implements Callable<Long> {

	/**
	 * Quadratic primes
	 * Problem 27
	 *
	 * Euler discovered the remarkable quadratic formula:
	 *
	 * n^2 + n + 41
	 *
	 * It turns out that the formula will produce 40 primes for the consecutive values n = 0 to 39. However, when n = 40, 402 + 40 + 41 = 40(40 + 1) + 41 is divisible by 41, and certainly when n = 41, 41^2 + 41 + 41 is clearly divisible by 41.
	 *
	 * The incredible formula  n^2 - 79n + 1601 was discovered, which produces 80 primes for the consecutive values n = 0 to 79. The product of the coefficients, -79 and 1601, is -126479.
	 *
	 * Considering quadratics of the form:
	 *
	 * n^2 + an + b, where |a| < 1000 and |b| < 1000
	 *
	 * where |n| is the modulus/absolute value of n
	 *     e.g. |11| = 11 and |-4| = 4
	 *
	 * Find the product of the coefficients, a and b, for the quadratic expression that produces the maximum number of primes for consecutive values of n, starting with n = 0.
	 */
	private final static long maxQ = 999;
	private final static long maxN = maxQ - 1;
	private final static long maxP = maxN ^ (2 + (maxN * maxQ) + maxQ) ^ 2;

	@Override
	public Long call() throws Exception {

		final long[] primes = Primes
				.makePrimeStreamUntil(maxP)
				.toArray();

		return LongStream
				.rangeClosed(-maxQ, maxQ)
				.parallel()
				.boxed()
				.flatMap(
					(Function<Long, Stream<AbstractMap.SimpleImmutableEntry<Long, Long>>>) a ->
					LongStream
						.rangeClosed(-maxQ, maxQ)
						.parallel()
						.mapToObj(b -> new AbstractMap.SimpleImmutableEntry<>(a, b))
				)
				.map(e -> {
					final long a = e.getKey();
					final long b = e.getValue();
					long n = 0;
					long x;
					do {
						x = (n * n) + (a * n) + b;
						n++;
					} while (Arrays.binarySearch(primes, x) >= 0);
					return new AbstractMap.SimpleImmutableEntry<>(a * b, --n);
				})
				.max(Comparator.comparingLong(AbstractMap.SimpleImmutableEntry::getValue))
				.get()
				.getKey();
	}
}
