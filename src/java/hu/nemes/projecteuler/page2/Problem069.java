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

public final class Problem069 implements Callable<Long> {

	/**
	 * Totient maximum
	 * Problem 69
	 *
	 * Euler's Totient function, φ(n) [sometimes called the phi function], is used to determine the number of numbers less than n which are relatively prime to n. For example, as 1, 2, 4, 5, 7, and 8, are all less than nine and relatively prime to nine, φ(9)=6.
	 * n 	Relatively Prime 	φ(n) 	n/φ(n)
	 * 2 	1 					1 		2
	 * 3 	1,2 				2 		1.5
	 * 4 	1,3 				2 		2
	 * 5 	1,2,3,4 			4 		1.25
	 * 6 	1,5 				2 		3
	 * 7 	1,2,3,4,5,6 		6 		1.1666...
	 * 8 	1,3,5,7 			4 		2
	 * 9 	1,2,4,5,7,8 		6 		1.5
	 * 10 	1,3,7,9 			4 		2.5
	 *
	 * It can be seen that n=6 produces a maximum n/φ(n) for n ≤ 10.
	 *
	 * Find the value of n ≤ 1,000,000 for which n/φ(n) is a maximum.
	 */
	private final static long limit = 1_000_000;

	@Override
	public Long call() {
		long result = 1;
		long p = 0;

		while ((result *= (p = Primes.nextPrime(p))) <= limit) {
			p++;
		}
		result /= p;

		return result;
	}
}
