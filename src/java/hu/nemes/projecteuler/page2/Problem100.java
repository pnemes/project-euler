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

public final class Problem100 implements Callable<BigInteger> {

	/**
	 * Problem 100
	 *
	 * If a box contains twenty-one coloured discs, composed of fifteen blue discs and six red discs, and two discs were taken at random, it can be seen that the probability of taking two blue discs, P(BB) = (15/21)×(14/20) = 1/2.
	 *
	 * The next such arrangement, for which there is exactly 50% chance of taking two blue discs at random, is a box containing eighty-five blue discs and thirty-five red discs.
	 *
	 * By finding the first arrangement to contain over 10^12 = 1,000,000,000,000 discs in total, determine the number of blue discs that the box would contain.
	 */
	@Override
	public BigInteger call() {

		// n total
		// b - blue

		//
		// 0.5 = (b/n) * ((b-1)/(n-1)) = (b * (b-1)) / (n * (n-1))
		// 2 * b * (b-1) = n * (n-1)
		// 2b^2 - 2b = n^2 - n
		// 2b^2 - 2b - n^2 + n = 0

		// x = b + n - 1
	    // b2 = 3 * b + 2 * n - 2 = b + 2*(b + n - 1) = b + x + x
	    // n2 = 4 * b + 3 * n - 3 = b + 3*(b + n - 1) = b + x + x + x

		final BigInteger TARGET = BigInteger.TEN.pow(12);

		BigInteger b = BigInteger.valueOf(15);
		BigInteger n = BigInteger.valueOf(21);

		BigInteger x;
		while (n.compareTo(TARGET) < 0) {

			x = b.add(n).subtract(BigInteger.ONE);
			n = b.add(x.add(x).add(x));
			b = b.add(x.add(x));
		}

		return b;
    }
}
