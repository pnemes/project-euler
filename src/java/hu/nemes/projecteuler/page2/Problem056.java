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

import java.math.BigInteger;
import java.util.concurrent.Callable;
import java.util.stream.IntStream;

public final class Problem056 implements Callable<Long> {

	/**
	 * Powerful digit sum
	 * Problem 56
	 *
	 * A googol (10^100) is a massive number: one followed by one-hundred zeros; 100100 is almost unimaginably large: one followed by two-hundred zeros. Despite their size, the sum of the digits in each number is only 1.
	 *
	 * Considering natural numbers of the form, ab, where a, b < 100, what is the maximum digital sum?
	 */
	@Override
	public Long call() {
		return IntStream
				.range(1, 100)
				.parallel()
				.mapToObj(BigInteger::valueOf)
				.flatMap(a -> IntStream
						.range(1, 100)
						.parallel()
						.mapToObj(b -> a.pow(b)))
				.mapToLong(n -> n.toString()
									.chars()
									.map(c -> c - '0')
									.sum())
				.max()
				.getAsLong();
	}
}
