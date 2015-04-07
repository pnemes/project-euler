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

public final class Problem049 implements Callable<String> {

	/**
	 * Prime permutations
	 * Problem 49
	 *
	 * The arithmetic sequence, 1487, 4817, 8147, in which each of the terms increases by 3330, is unusual in two ways: (i) each of the three terms are prime, and, (ii) each of the 4-digit numbers are permutations of one another.
	 *
	 * There are no arithmetic sequences made up of three 1-, 2-, or 3-digit primes, exhibiting this property, but there is one other 4-digit increasing sequence.
	 *
	 * What 12-digit number do you form by concatenating the three terms in this sequence?
	 */
	@Override
	public String call() {

		return Primes
				.makePrimeStreamUntil(10000)
				.filter(p -> p >= 1000)
				.parallel()
				.mapToObj(p -> StreamUtils
							.makePermutationStream(
									(int) p / 1000,
									((int) p / 100) % 10,
									((int) p / 10) % 10,
									(int) p % 10)
							.filter(Primes::isPrime)
							.toArray())
				.filter(a -> ((a.length >= 3) && ((a[1] - a[0]) == (a[2] - a[1]))))
				.map(a -> Long.toString(a[0]) + Long.toString(a[1]) + Long.toString(a[2]))
				.findFirst()
				.get();
	}
}
