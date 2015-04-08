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

public final class Problem025 implements Callable<Long> {

	/**
	 * 1000-digit Fibonacci number
	 * Problem 25
	 *
	 * The Fibonacci sequence is defined by the recurrence relation:
	 *
	 * Fn = Fn-1 + Fn-2, where F1 = 1 and F2 = 1.
	 * Hence the first 12 terms will be:
	 *
	 *     F1 = 1
	 *     F2 = 1
	 *     F3 = 2
	 *     F4 = 3
	 *     F5 = 5
	 *     F6 = 8
	 *     F7 = 13
	 *     F8 = 21
	 *     F9 = 34
	 *     F10 = 55
	 *     F11 = 89
	 *     F12 = 144
	 *
	 * The 12th term, F12, is the first term to contain three digits.
	 *
	 * What is the first term in the Fibonacci sequence to contain 1000 digits?
	 */
	@Override
	public Long call() {

		final BigInteger m = BigInteger.TEN.pow(999);

		BigInteger f1 = BigInteger.ONE;
		BigInteger f2 = BigInteger.ZERO;
		long i = 0;

		do {
			f2 = f2.add(f1);
			f1 = f2.subtract(f1);
			i++;
		}
		while (f2.compareTo(m) == -1);

		return i;
	}
}
