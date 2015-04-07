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

import hu.nemes.projecteuler.common.Arithmetic;

import java.util.concurrent.Callable;
import java.util.function.LongUnaryOperator;
import java.util.stream.LongStream;

public final class Problem052 implements Callable<Long> {

	/**
	 * Permuted multiples
	 * Problem 52
	 *
	 * It can be seen that the number, 125874, and its double, 251748, contain exactly the same digits, but in a different order.
	 *
	 * Find the smallest positive integer, x, such that 2x, 3x, 4x, 5x, and 6x, contain the same digits.
	 */
	@Override
	public Long call() {
		final LongUnaryOperator f = n -> Arithmetic.forEachDigitsOf(n, 0, (a, b) -> a | (1 << b));

		return LongStream
				.iterate(1, i -> i + 1)
				.filter(i -> {
					double d = i;
					while (d > 10) {
						d /= 10;
					}
					return (d * 6) < 10;
				})
				.filter(i -> {
					final long m = f.applyAsLong(i);

					for (int x = 2; x < 7; x++) {
						if (m != f.applyAsLong(x * i)) {
							return false;
						}
					}
					return true;
				})
				.findFirst()
				.getAsLong();
	}
}
