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

import java.util.concurrent.Callable;
import java.util.stream.LongStream;

public final class Problem046 implements Callable<Long> {

	/**
	 * Goldbach's other conjecture
	 * Problem 46
	 *
	 * It was proposed by Christian Goldbach that every odd composite number can be written as the sum of a prime and twice a square.
	 *
	 * 9 = 7 + 2×12
	 * 15 = 7 + 2×22
	 * 21 = 3 + 2×32
	 * 25 = 7 + 2×32
	 * 27 = 19 + 2×22
	 * 33 = 31 + 2×12
	 *
	 * It turns out that the conjecture was false.
	 *
	 * What is the smallest odd composite that cannot be written as the sum of a prime and twice a square?
	 */
	@Override
	public Long call() {
		return LongStream
			.iterate(3, n -> n + 2)
			.filter(n -> !Primes.isPrime(n))
			.filter(n -> !Primes
					.makePrimeStreamUntil(n)
					.anyMatch(p -> (Math.sqrt((n - p) / 2) % 1) == 0))
			.findFirst()
			.getAsLong();
	}
}
