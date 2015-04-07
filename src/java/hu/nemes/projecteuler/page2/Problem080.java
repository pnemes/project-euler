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

import hu.nemes.projecteuler.common.StreamUtils;

import java.math.BigInteger;
import java.util.concurrent.Callable;
import java.util.function.LongSupplier;

public final class Problem080 implements Callable<Long> {

	/**
	 * Square root digital expansion
	 * Problem 80
	 *
	 * It is well known that if the square root of a natural number is not an integer, then it is irrational. The decimal expansion of such square roots is infinite without any repeating pattern at all.
	 *
	 * The square root of two is 1.41421356237309504880..., and the digital sum of the first one hundred decimal digits is 475.
	 *
	 * For the first one hundred natural numbers, find the total of the digital sums of the first one hundred decimal digits for all the irrational square roots.
	 */
	@Override
	public Long call() {
		final int limit = 100;
		final BigInteger bLimit = BigInteger.TEN.pow(limit + 1);
		final BigInteger FIVE = BigInteger.valueOf(5);
		final BigInteger HUNDRED = BigInteger.TEN.pow(2);

		return StreamUtils
				.generate(new LongSupplier() {

					private long n = 0;
					private long s = 1;

					@Override
					public long getAsLong() {
						if (++n == (s * s)) {
							s++;
							n++;
						}
						return n;
					}

				}, n -> n <= limit)
				.mapToObj(n -> {
					BigInteger a = BigInteger.valueOf(5 * n);
					BigInteger b = FIVE;

					while (b.compareTo(bLimit) == -1) {
						if (a.compareTo(b) > -1) {
							a = a.subtract(b);
							b = b.add(BigInteger.TEN);
						}
						else {
							a = a.multiply(HUNDRED);
							b = b.divide(BigInteger.TEN).multiply(HUNDRED).add(FIVE);
						}
					}

					return b.divide(HUNDRED);
				})
				.mapToLong(b -> {
					long s = 0;
					final char ca[] = b.toString().toCharArray();
					for (final char c : ca) {
						s += c - '0';
					}
					return s;
				})
				.sum();
	}
}
