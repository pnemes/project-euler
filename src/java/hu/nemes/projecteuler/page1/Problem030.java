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

import java.util.concurrent.Callable;
import java.util.stream.LongStream;

public final class Problem030 implements Callable<Long> {

	/**
	 * Digit fifth powers
	 * Problem 30
	 *
	 * Surprisingly there are only three numbers that can be written as the sum of fourth powers of their digits:
	 *
	 *     1634 = 1^4 + 6^4 + 3^4 + 4^4
	 *     8208 = 8^4 + 2^4 + 0^4 + 8^4
	 *     9474 = 9^4 + 4^4 + 7^4 + 4^4
	 *
	 * As 1 = 1^4 is not a sum it is not included.
	 *
	 * The sum of these numbers is 1634 + 8208 + 9474 = 19316.
	 *
	 * Find the sum of all the numbers that can be written as the sum of fifth powers of their digits.
	 */
	private final static int p = 5;

	@Override
	public Long call() {

		// approximate the upper bound: n * 9^p < 10^n-1
		final long m = (long) Math.pow(9, p);
		long l = 9;

		long max = m;
		do {
			max += m;
		} while ((l = (++l * 10) - 1) <= max);

		return LongStream
				.rangeClosed(2, max)
				.parallel()
				.filter(n -> n == Arithmetic.forEachDigitsOf(n, 0, (a, b) -> a + (long) Math.pow(b, p)))
				.sum();
	}
}
