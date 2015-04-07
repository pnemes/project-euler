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

public final class Problem034 implements Callable<Long> {

	/**
	 * Digit factorials
	 * Problem 34
	 *
	 * 145 is a curious number, as 1! + 4! + 5! = 1 + 24 + 120 = 145.
	 *
	 * Find the sum of all numbers which are equal to the sum of the factorial of their digits.
	 *
	 * Note: as 1! = 1 and 2! = 2 are not sums they are not included.
	 */
	@Override
	public Long call() {
		final long[] f = new long[10];
		f[0] = 1;
        for (int i = 1; i < f.length; ) {
            f[i] = f[i-1] * i++;
        }

		// approximate the upper bound: n * 9! < 10^n-1
		final long m = f[9];
		long l = 9;

		long max = m;
		do {
			max += m;
		} while ((l = (++l * 10) - 1) <= max);

		return LongStream
				.rangeClosed(3, max)
				.parallel()
				.filter(n -> n == Arithmetic.forEachDigitsOf(n, 0, (a, b) -> a + f[(int) b]))
				.sum();
	}
}
