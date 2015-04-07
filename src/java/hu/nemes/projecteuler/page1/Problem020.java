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
import java.util.stream.LongStream;

public final class Problem020 implements Callable<Long> {

	/**
	 * Factorial digit sum
	 * Problem 20
	 *
	 * n! means n x (n - 1) x ... x 3 x 2 x 1
	 *
	 * For example, 10! = 10 x 9 x ... x 3 x 2 x 1 = 3628800,
	 * and the sum of the digits in the number 10! is 3 + 6 + 2 + 8 + 8 + 0 + 0 = 27.
	 *
	 * Find the sum of the digits in the number 100!
	 */
	@Override
	public Long call() {
		return LongStream
				.rangeClosed(1, 100)
				.boxed()
				.reduce(
						BigInteger.ONE,
						(a, b) -> a.multiply(BigInteger.valueOf(b)),
						(a, b) -> a.multiply(b))
				.toString()
				.chars()
				.parallel()
				.mapToLong(f -> f - '0')
				.sum();
	}
}
