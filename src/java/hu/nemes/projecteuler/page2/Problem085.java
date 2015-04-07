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
import java.util.function.LongBinaryOperator;

public final class Problem085 implements Callable<Long> {

	/**
	 * Counting rectangles
	 * Problem 85
	 *
	 * By counting carefully it can be seen that a rectangular grid measuring 3 by 2 contains eighteen rectangles:
	 *
	 * Although there exists no rectangular grid that contains exactly two million rectangles, find the area of the grid with the nearest solution.
	 */
	@Override
	public Long call() {
		final long target = 2_000_000;

		// (a) -> (a*(a+1))^2 / 4
		final LongBinaryOperator f = (a, b) -> (a*(a+1) * b*(b+1)) / 4;

		// inverse if a=b:
		// (n) -> (sqrt(8*sqrt(a)+1)-1) / 2

		// lower approx for square
		final int n = (int) (Math.sqrt((8 * Math.sqrt(target)) + 1) - 1) / 2;

		long oDiff;
		long diff = Long.MAX_VALUE;
		int a = 1 + n;
		int b = 2 + n;
		do {
			oDiff = diff;
			diff = Math.abs(f.applyAsLong(++a, --b) - target);
		} while ((b>0) && (diff < oDiff));
		return (long) (--a * ++b);
	}
}
