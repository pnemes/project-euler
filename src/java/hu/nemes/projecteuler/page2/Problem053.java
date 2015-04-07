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

import java.util.concurrent.Callable;
import java.util.stream.LongStream;

public final class Problem053 implements Callable<Long> {

	/**
	 * Combinatoric selections
	 * Problem 53
	 *
	 * There are exactly ten ways of selecting three from five, 12345:
	 *
	 * 123, 124, 125, 134, 135, 145, 234, 235, 245, and 345
	 *
	 * In combinatorics, we use the notation, 5C3 = 10.
	 *
	 * In general,
	 * nCr =
	 * n! / r!(n−r)!
	 * ,where r <= n, n! = n×(n−1)×...×3×2×1, and 0! = 1.
	 *
	 * It is not until n = 23, that a value exceeds one-million: 23C10 = 1144066.
	 *
	 * How many, not necessarily distinct, values of  nCr, for 1 <= n <= 100, are greater than one-million?
	 */
	@Override
	public Long call() {

		return LongStream
				.rangeClosed(1, 100)
				.parallel()
				.flatMap(n -> LongStream
						.rangeClosed(1, n)
						.parallel()
						.map(r -> {
							long x = 1;
							final long k = Math.min(r, n - r);

							long i = (n - k) + 1;
				            for (long j = 1; (j <= k) && (x < 1_000_000); j++) {
				                x = (x * i++) / j;
				            }
							return x;
						})
						.filter(x -> x > 1_000_000))
				.count();
	}
}
