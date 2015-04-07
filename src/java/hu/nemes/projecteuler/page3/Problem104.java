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
package hu.nemes.projecteuler.page3;

import hu.nemes.projecteuler.common.Arithmetic;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.Callable;

public final class Problem104 implements Callable<Long> {

	/**
	 * Pandigital Fibonacci ends
	 * Problem 104
	 *
	 * The Fibonacci sequence is defined by the recurrence relation:
	 *
	 * Fn = Fn−1 + Fn−2, where F1 = 1 and F2 = 1.
	 *
	 * It turns out that F541, which contains 113 digits, is the first Fibonacci number for which the last nine digits are 1-9 pandigital (contain all the digits 1 to 9, but not necessarily in order). And F2749, which contains 575 digits, is the first Fibonacci number for which the first nine digits are 1-9 pandigital.
	 *
	 * Given that Fk is the first Fibonacci number for which the first nine digits AND the last nine digits are 1-9 pandigital, find k.
	 */
	@Override
	public Long call() throws IOException, URISyntaxException {

		final double SQRT_5 = Math.sqrt(5);
		final double PHI = (1 + SQRT_5) / 2;
		final double LOG10_PHI = Math.log10(PHI);
		final double LOG10_SQRT_5 = Math.log10(SQRT_5);

		final long TEN_POW_TEN = 1_000_000_000;

		long fn2 = 1;
		long fn1 = 1;


		long n = 2;
		boolean found = false;

		do {
		    n++;
		    fn1 = (fn2 + (fn2 = fn1)) % TEN_POW_TEN;

		    if (Arithmetic.isPandigital(fn1)) {
		        final double t = ((n * LOG10_PHI) - LOG10_SQRT_5);
		        found = Arithmetic.isPandigital((long) Math.pow(10, (t - (long) t) + 8));
		    }
		} while (!found);

		return n;
    }
}
