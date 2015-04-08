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

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.LongSupplier;

public final class Problem050 implements Callable<Long> {

	/**
	 * Consecutive prime sum
	 * Problem 50
	 *
	 * The prime 41, can be written as the sum of six consecutive primes:
	 * 41 = 2 + 3 + 5 + 7 + 11 + 13
	 *
	 * This is the longest sum of consecutive primes that adds to a prime below one-hundred.
	 *
	 * The longest sum of consecutive primes below one-thousand that adds to a prime, contains 21 terms, and is equal to 953.
	 *
	 * Which prime, below one-million, can be written as the sum of the most consecutive primes?
	 */
	@Override
	public Long call() {

		return Primes
				.makePrimeStreamUntil(1_000_000)
				.filter(p -> p > 1000)
				.<Map.Entry<Long, Long>>mapToObj(p -> {
					final LongSupplier primes1 = Primes.makePrimeSupplier();
					final LongSupplier primes2 = Primes.makePrimeSupplier();

					long max = 0;
					long i = 0;
					long sum = 0;
					long last = 0;
					do {
						while (sum < p) {
							sum += (last = primes1.getAsLong());
							i++;
						}
						max = Math.max(max, i);

						do {
							sum -= primes2.getAsLong();
							i--;
						} while (sum > p);
					} while ((last * max) < p);

					return new AbstractMap.SimpleImmutableEntry<Long, Long>(p, (sum == p) ? i : 0);
				})
				.filter(e -> e.getValue() > 0)
				.max(Comparator.comparingLong(Map.Entry::getValue))
				.get()
				.getKey();
	}
}
