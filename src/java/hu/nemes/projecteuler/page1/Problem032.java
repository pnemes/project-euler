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

public final class Problem032 implements Callable<Long> {

	/**
	 * Pandigital products
	 * Problem 32
	 *
	 * We shall say that an n-digit number is pandigital if it makes use of all the digits 1 to n exactly once; for example, the 5-digit number, 15234, is 1 through 5 pandigital.
	 *
	 * The product 7254 is unusual, as the identity, 39 x 186 = 7254, containing multiplicand, multiplier, and product is 1 through 9 pandigital.
	 *
	 * Find the sum of all products whose multiplicand/multiplier/product identity can be written as a 1 through 9 pandigital.
	 * HINT: Some products can be obtained in more than one way so be sure to only include it once in your sum.
	 */
	@Override
	public Long call() {
		return LongStream
				.range(1, 100)
				.parallel()
				.flatMap(a ->
					LongStream
					.rangeClosed(
							(a > 9) ? 123 : 1234,
							10000 / a)
					.parallel()
					.filter(b -> a != b)
					.filter(b -> Arithmetic.isPandigitalCombined(new long[] {a, b, a * b}))
					.map(b -> a * b))
				.distinct()
				.sum();
	}
}
