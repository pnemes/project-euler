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
import java.util.stream.IntStream;

public final class Problem064 implements Callable<Long> {

	/**
	 * Odd period square roots
	 * Problem 64
	 *
	 * All square roots are periodic when written as continued fractions and can be written in the form:
	 *
	 * It can be seen that the sequence is repeating. For conciseness, we use the notation √23 = [4;(1,3,1,8)], to indicate that the block (1,3,1,8) repeats indefinitely.
	 *
	 * The first ten continued fraction representations of (irrational) square roots are:
	 *
	 * √2=[1;(2)], period=1
	 * √3=[1;(1,2)], period=2
	 * √5=[2;(4)], period=1
	 * √6=[2;(2,4)], period=2
	 * √7=[2;(1,1,1,4)], period=4
	 * √8=[2;(1,4)], period=2
	 * √10=[3;(6)], period=1
	 * √11=[3;(3,6)], period=2
	 * √12= [3;(2,6)], period=2
	 * √13=[3;(1,1,1,1,6)], period=5
	 *
	 * Exactly four continued fractions, for N ≤ 13, have an odd period.
	 *
	 * How many continued fractions for N ≤ 10000 have an odd period?
	 */
	@Override
	public Long call() {
		return IntStream
				.rangeClosed(1, 100)
				.parallel()
				.flatMap(n -> IntStream.range(((n - 1) * (n - 1)) + 1, n * n))
				.map(n -> {
					int l = 0;
					final int a0 = (int) Math.sqrt(n);

					int m = 0;
					int d = 1;
					int a = a0;

					do {
						m = (d * a) - m;
						d = (n - (m * m)) / d;
						a = (a0 + m) / d;
						l++;
					} while (a != (2 * a0));

					return l;
				})
				.filter(l -> (l % 2) == 1)
				.count();
	}
}
