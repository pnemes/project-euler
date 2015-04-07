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

import hu.nemes.projecteuler.common.BigFraction;

import java.math.BigInteger;
import java.util.concurrent.Callable;

public final class Problem057 implements Callable<Long> {

	/**
	 * Square root convergents
	 * Problem 57
	 *
	 * It is possible to show that the square root of two can be expressed as an infinite continued fraction.
	 *
	 * sqrt(2) = 1 + 1/(2 + 1/(2 + 1/(2 + ... ))) = 1.414213...
	 *
	 * By expanding this for the first four iterations, we get:
	 *
	 * 1 + 1/2 = 3/2 = 1.5
	 * 1 + 1/(2 + 1/2) = 7/5 = 1.4
	 * 1 + 1/(2 + 1/(2 + 1/2)) = 17/12 = 1.41666...
	 * 1 + 1/(2 + 1/(2 + 1/(2 + 1/2))) = 41/29 = 1.41379...
	 *
	 * The next three expansions are 99/70, 239/169, and 577/408, but the eighth expansion, 1393/985, is the first example where the number of digits in the numerator exceeds the number of digits in the denominator.
	 *
	 * In the first one-thousand expansions, how many fractions contain a numerator with more digits than denominator?
	 */
	@Override
	public Long call() {
		final BigInteger TWO = BigInteger.valueOf(2);
		return BigFraction
				.continuedFractionStream(BigInteger.ONE, () -> TWO)
				.limit(1000)
				.filter(bf -> bf.getNumerator().toString().length() > bf.getDenominator().toString().length())
				.count();
	}
}
