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

import java.util.concurrent.Callable;
import java.util.stream.IntStream;

public final class Problem021 implements Callable<Long> {

	/**
	 * Amicable numbers
	 * Problem 21
	 *
	 * Let d(n) be defined as the sum of proper divisors of n (numbers less than n which divide evenly into n).
	 * If d(a) = b and d(b) = a, where a =/= b, then a and b are an amicable pair and each of a and b are called amicable numbers.
	 *
	 * For example, the proper divisors of 220 are 1, 2, 4, 5, 10, 11, 20, 22, 44, 55 and 110; therefore d(220) = 284. The proper divisors of 284 are 1, 2, 4, 71 and 142; so d(284) = 220.
	 *
	 * Evaluate the sum of all the amicable numbers under 10000.
	 */
	@Override
	public Long call() {
		final int limit = 10_000;
		final long[] sumOfProperDivisors = new long[limit];
		IntStream
			.rangeClosed(1, limit)
			.forEach(n -> {
				for (int i=n*2; i < limit; i+=n) {
					sumOfProperDivisors[i] += n;
				}
			});

		return (long) IntStream
				.range(1, limit)
				.parallel()
				.filter(i -> i != sumOfProperDivisors[i])
				.filter(i -> sumOfProperDivisors[i] < limit)
				.filter(i -> i == sumOfProperDivisors[(int) sumOfProperDivisors[i]])
				.sum();
	}
}
