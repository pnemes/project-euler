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

import java.util.concurrent.Callable;

public final class Problem111 implements Callable<Long> {

	/**
	 * Primes with runs
	 * Problem 111
	 *
	 * Considering 4-digit primes containing repeated digits it is clear that they cannot all be the same: 1111 is divisible by 11, 2222 is divisible by 22, and so on. But there are nine 4-digit primes containing three ones:
	 *
	 * 1117, 1151, 1171, 1181, 1511, 1811, 2111, 4111, 8111
	 *
	 * We shall say that M(n, d) represents the maximum number of repeated digits for an n-digit prime where d is the repeated digit, N(n, d) represents the number of such primes, and S(n, d) represents the sum of these primes.
	 *
	 * So M(4, 1) = 3 is the maximum number of repeated digits for a 4-digit prime where one is the repeated digit, there are N(4, 1) = 9 such primes, and the sum of these primes is S(4, 1) = 22275. It turns out that for d = 0, it is only possible to have M(4, 0) = 2 repeated digits, but there are N(4, 0) = 13 such cases.
	 *
	 * In the same way we obtain the following results for 4-digit primes.
	 * Digit, d 	M(4, d) 	N(4, d) 	S(4, d)
	 * 0 	2 	13 	67061
	 * 1 	3 	9 	22275
	 * 2 	3 	1 	2221
	 * 3 	3 	12 	46214
	 * 4 	3 	2 	8888
	 * 5 	3 	1 	5557
	 * 6 	3 	1 	6661
	 * 7 	3 	9 	57863
	 * 8 	3 	1 	8887
	 * 9 	3 	7 	48073
	 *
	 * For d = 0 to 9, the sum of all S(4, d) is 273700.
	 *
	 * Find the sum of all S(10, d).
	 */
	@Override
	public Long call() {

		//							0123456789
		// 1/x + 1/y = 1/n
		// yn + xn = xy
		// ...
		// n2 = sr

		long n = 1;
		for (long s = 0; s < 8_000_000; n++) {
			s = Arithmetic.numberOfDivisors(n*n);
		}
		return --n;
    }
}
