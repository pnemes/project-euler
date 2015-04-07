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

import hu.nemes.projecteuler.common.Arithmetic;

import java.util.concurrent.Callable;

public final class Problem033 implements Callable<Long> {

	/**
	 * Digit canceling fractions
	 * Problem 33
	 *
	 * The fraction 49/98 is a curious fraction, as an inexperienced mathematician in attempting to simplify it may incorrectly believe that 49/98 = 4/8, which is correct, is obtained by cancelling the 9s.
	 *
	 * We shall consider fractions like, 30/50 = 3/5, to be trivial examples.
	 *
	 * There are exactly four non-trivial examples of this type of fraction, less than one in value, and containing two digits in the numerator and denominator.
	 *
	 * If the product of these four fractions is given in its lowest common terms, find the value of the denominator.
	 */
	@Override
	public Long call() {
		int dp = 1;
		int np = 1;

		for (int i = 1; i < 10; i++) {
		    for (int d = 1; d < i; d++) {
		        for (int n = 1; n < d; n++) {
		            if ((((n * 10) + i) * d) == (n * ((i * 10) + d))) {
		                dp *= d;
		                np *= n;
		            }
		        }
		    }
		}

		return (long) (dp /= Arithmetic.gcd(np, dp));
	}
}
