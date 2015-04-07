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

public final class Problem006 implements Callable<Long> {

	/**
	 * Sum square difference
	 * Problem 6
	 *
	 * The sum of the squares of the first ten natural numbers is,
	 * 12 + 22 + ... + 102 = 385
	 *
	 * The square of the sum of the first ten natural numbers is,
	 * (1 + 2 + ... + 10)2 = 552 = 3025
	 *
	 * Hence the difference between the sum of the squares of the first ten natural numbers and the square of the sum is 3025 - 385 = 2640.
	 *
	 * Find the difference between the sum of the squares of the first one hundred natural numbers and the square of the sum.
	 */
	@Override
	public Long call() {

		long d = 0;
		long t = 0;
		for (int i = 1; i <= 100; i++) {
			d += i * i;
			t += i;
		}

		return (t * t) - d;
	}
}
