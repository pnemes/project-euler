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

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Stream;

public final class Problem070 implements Callable<Long> {

	/**
	 * Totient permutation
	 * Problem 70
	 *
	 * Euler's Totient function, φ(n) [sometimes called the phi function], is used to determine the number of positive numbers less than or equal to n which are relatively prime to n. For example, as 1, 2, 4, 5, 7, and 8, are all less than nine and relatively prime to nine, φ(9)=6.
	 * The number 1 is considered to be relatively prime to every positive number, so φ(1)=1.
	 *
	 * Interestingly, φ(87109)=79180, and it can be seen that 87109 is a permutation of 79180.
	 *
	 * Find the value of n, 1 < n < 10^7, for which φ(n) is a permutation of n and the ratio n/φ(n) produces a minimum.
	 */
	private final static long limit = 10_000_000;

	@Override
	public Long call() {
		final long primeLimit = (long) (Math.sqrt(limit) * 2);
		final long[] primes = Primes
				.makePrimeStreamUntil(primeLimit)
				.toArray();

		return Arrays
				.stream(primes)
				.parallel()
				.<Stream<Map.Entry<Long, Double>>>mapToObj(
						a -> Arrays
						.stream(primes)
						.parallel()
						.filter(b -> {
							final long n;
							final long t;
							return
								((a != b) &&
								((n = a * b) <= limit) &&
								(((n - (t =  (a - 1) * (b - 1))) % 9) == 0) &&
								Arrays.equals(
										Long.toString(n).chars().sorted().toArray(),
										Long.toString(t).chars().sorted().toArray())
								);
						})
						.mapToObj(b -> new AbstractMap.SimpleImmutableEntry<>(a * b, ((double) (a * b)) / ((a - 1) * (b - 1))))
				)
				.flatMap(Function.identity())
				.min(Comparator.comparing(Map.Entry::getValue))
				.get()
				.getKey();
	}
}
