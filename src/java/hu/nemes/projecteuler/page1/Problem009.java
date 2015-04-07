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

import java.math.BigInteger;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.stream.LongStream;

public final class Problem009 implements Callable<Long> {

	/**
	 * Special Pythagorean triplet
	 * Problem 9
	 *
	 * A Pythagorean triplet is a set of three natural numbers, a < b < c, for which,
	 * a2 + b2 = c2
	 *
	 * For example, 32 + 42 = 9 + 16 = 25 = 52.
	 *
	 * There exists exactly one Pythagorean triplet for which a + b + c = 1000.
	 * Find the product abc.
	 */
	private final static int sum = 1000;

	private final static int min = (int) Math.ceil(sum / (2 + Math.sqrt(2)));
	private final static int max = sum / 2;

	@Override
	public Long call() {

		return LongStream
				.range(min, max)
				.parallel()
				.mapToObj(b -> {
					final double a = ((double) (((sum * sum) / 2) - (sum * b))) / (sum - b);
					if ((a % 1) != 0) {
						return null;
					}
					return BigInteger.valueOf(((long) a) * b).multiply(BigInteger.valueOf(sum - ((long) a) - b));
				})
				.filter(Predicate.isEqual(null).negate())
				.reduce((p1, p2) -> {
					throw new AssertionError(String.format("Multiple solutions exists (p0 = %d, p2 = %d)!", p1, p2));
				})
				.get()
				.longValueExact();
	}
}
