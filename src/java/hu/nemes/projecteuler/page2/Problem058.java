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

import hu.nemes.projecteuler.common.Primes;

import java.util.concurrent.Callable;

public final class Problem058 implements Callable<Long> {

	/**
	 * Spiral primes
	 * Problem 58
	 *
	 * Starting with 1 and spiralling anticlockwise in the following way, a square spiral with side length 7 is formed.
	 *
	 * 37 36 35 34 33 32 31
	 * 38 17 16 15 14 13 30
	 * 39 18  5  4  3 12 29
	 * 40 19  6  1  2 11 28
	 * 41 20  7  8  9 10 27
	 * 42 21 22 23 24 25 26
	 * 43 44 45 46 47 48 49
	 *
	 * It is interesting to note that the odd squares lie along the bottom right diagonal, but what is more interesting is that 8 out of the 13 numbers lying along both diagonals are prime; that is, a ratio of 8/13 ≈ 62%.
	 *
	 * If one complete new layer is wrapped around the spiral above, a square spiral with side length 9 will be formed. If this process is continued, what is the side length of the square spiral for which the ratio of primes along both diagonals first falls below 10%?
	 */
	@Override
	public Long call() {

		long i = 1;
		long r = 1;

		long a = 0;
		long b = 1;
		do {
			for (int n = 0; n < 4; n++) {
				if (Primes.isPrime(i += 2 * r)) {
					a++;
				}
				b++;
			}
			r++;
		} while (((double) a / (double) b) > 0.1);

		return (r * 2) - 1;
	}
}
