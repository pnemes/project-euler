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

import java.util.concurrent.Callable;
import java.util.stream.LongStream;

public final class Problem040 implements Callable<Long> {

	/**
	 * Champernowne's constant
	 * Problem 40
	 *
	 * An irrational decimal fraction is created by concatenating the positive integers:
	 *
	 * 0.123456789101112131415161718192021...
	 *
	 * It can be seen that the 12th digit of the fractional part is 1.
	 *
	 * If dn represents the nth digit of the fractional part, find the value of the following expression.
	 *
	 * d1 × d10 × d100 × d1000 × d10000 × d100000 × d1000000
	 */
	@Override
	public Long call() {
		return LongStream
				.rangeClosed(0, 6)
				.map(n -> (long) Math.pow(10, n))
				.map(n -> {
					long i = 1;
					long m = 9;
					while (n > (m * i)) {
						n -= m * i++;
						m *= 10;
					}

					m /= 9;
					n--;
					long result = m + (n / i);
					long digit = n % i;

					long t = m * 10;
					while (result > 9) {
						while (--digit > 0) {
							t /= 10;
						}
						result %= t;
						result /= 10;
					}

					return result;
				})
				.reduce(1, (a, b) -> a * b);
	}
}
