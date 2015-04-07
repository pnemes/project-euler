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

import hu.nemes.projecteuler.common.Primes;
import hu.nemes.projecteuler.common.StreamUtils;

import java.util.concurrent.Callable;

public final class Problem043 implements Callable<Long> {

	/**
	 * Sub-string divisibility
	 * Problem 43
	 *
	 * The number, 1406357289, is a 0 to 9 pandigital number because it is made up of each of the digits 0 to 9 in some order, but it also has a rather interesting sub-string divisibility property.
	 *
	 * Let d1 be the 1st digit, d2 be the 2nd digit, and so on. In this way, we note the following:
	 *
	 *     d2d3d4=406 is divisible by 2
	 *     d3d4d5=063 is divisible by 3
	 *     d4d5d6=635 is divisible by 5
	 *     d5d6d7=357 is divisible by 7
	 *     d6d7d8=572 is divisible by 11
	 *     d7d8d9=728 is divisible by 13
	 *     d8d9d10=289 is divisible by 17
	 *
	 * Find the sum of all 0 to 9 pandigital numbers with this property.
	 */
	@Override
	public Long call() {

		final long[] divisors = Primes.makePrimeStream().limit(7).toArray();

		return StreamUtils
			.makePermutationStream(1, 0, 2, 3, 4, 5, 6, 7, 8, 9)
			.parallel()
			.filter(n -> {

				for (int i = divisors.length - 1; i >= 0; i--) {
					if (!(((n % 1000) % divisors[i]) == 0)) {
						return false;
					}
					n /= 10;
				}
				return true;
			})
			.sum();
	}
}
