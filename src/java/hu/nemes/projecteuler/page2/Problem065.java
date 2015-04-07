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
import java.util.function.Supplier;

public final class Problem065 implements Callable<Long> {

	/**
	 * Convergents of e
	 * Problem 65
	 *
	 * The square root of 2 can be written as an infinite continued fraction.
	 *
	 * Hence the sequence of the first ten convergents for √2 are:
	 * 1, 3/2, 7/5, 17/12, 41/29, 99/70, 239/169, 577/408, 1393/985, 3363/2378, ...
	 *
	 * What is most surprising is that the important mathematical constant,
	 * e = [2; 1,2,1, 1,4,1, 1,6,1 , ... , 1,2k,1, ...].
	 *
	 * The first ten terms in the sequence of convergents for e are:
	 * 2, 3, 8/3, 11/4, 19/7, 87/32, 106/39, 193/71, 1264/465, 1457/536, ...
	 *
	 * The sum of digits in the numerator of the 10th convergent is 1+4+5+7=17.
	 *
	 * Find the sum of digits in the numerator of the 100th convergent of the continued fraction for e.
	 */
	@Override
	public Long call() {
		final BigInteger TWO = BigInteger.valueOf(2);
		return BigFraction
				.continuedFractionStream(
						TWO,
						new Supplier<BigInteger>() {

							private long n = 1;

							@Override
							public BigInteger get() {
								return ((++n % 3) == 0) ? BigInteger.valueOf((n/3)*2) : BigInteger.ONE;
							}

						})
				.skip(99)
				.mapToLong(bf -> bf.getNumerator().toString().chars().map(d -> d - '0').sum())
				.findFirst()
				.getAsLong();
	}
}
