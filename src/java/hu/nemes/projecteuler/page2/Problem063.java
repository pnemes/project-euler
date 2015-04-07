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

public final class Problem063 implements Callable<Integer> {

	/**
	 * Powerful digit counts
	 * Problem 63
	 *
	 * The 5-digit number, 16807=7^5, is also a fifth power. Similarly, the 9-digit number, 134217728=8^9, is a ninth power.
	 *
	 * How many n-digit positive integers exist which are also an nth power?
	 */
	@Override
	public Integer call() {
		final BigInteger TEN = BigInteger.valueOf(10);
		BigInteger t = BigInteger.ONE;
		BigInteger a;
		boolean found;

		int i = 1;
		int n = 0;
		do {
			a = BigInteger.ONE;
			found = false;
			while (a.pow(i).compareTo(t) < 0) {
				a = a.add(BigInteger.ONE);
			}

			t = t.multiply(TEN);
			while (a.pow(i).compareTo(t) < 0) {
				a = a.add(BigInteger.ONE);
				n++;
				found = true;
			}
			i++;

		} while (found);

		return n;
	}
}
