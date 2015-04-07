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
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

public final class Problem092 implements Callable<Long> {

	/**
	 * Square digit chains
	 * Problem 92
	 *
	 * A number chain is created by continuously adding the square of the digits in a number to form a new number until it has been seen before.
	 *
	 * For example,
	 *
	 * 44 → 32 → 13 → 10 → 1 → 1
	 * 85 → 89 → 145 → 42 → 20 → 4 → 16 → 37 → 58 → 89
	 *
	 * Therefore any chain that arrives at 1 or 89 will become stuck in an endless loop. What is most amazing is that EVERY starting number will eventually arrive at 1 or 89.
	 *
	 * How many starting numbers below ten million will arrive at 89?
	 */
	@Override
	public Long call() {
		final int limit = 10_000_000;

		final int cacheSize = (int) Math.ceil(81 * Math.log10(limit)) + 1;
		final boolean[] cache = new boolean[cacheSize];

		final IntUnaryOperator f = n1 -> {
			int d;
			int n2 = 0;
			while (n1 > 0) {
				d = n1 % 10;
				n2 += d * d;
				n1 /= 10;
			}
			return n2;
		};

		long result = IntStream
			.range(2, cacheSize)
			.filter(n -> {

				int n1 = n;
				while ((n1 != 89) && (n1 >= n)) {
					n1 = f.applyAsInt(n1);
				}

				return cache[n] = ((n1 == 89) || cache[n1]);
			})
			.count();

		result += IntStream
				.range(cacheSize, limit)
				.parallel()
				.map(f)
				.filter(n -> cache[n])
				.count();

		return result;
	}
}
