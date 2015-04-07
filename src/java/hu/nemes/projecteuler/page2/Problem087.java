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

import java.util.Arrays;
import java.util.BitSet;
import java.util.concurrent.Callable;

public final class Problem087 implements Callable<Long> {

	/**
	 * Prime power triples
	 * Problem 87
	 *
	 * The smallest number expressible as the sum of a prime square, prime cube, and prime fourth power is 28. In fact, there are exactly four numbers below fifty that can be expressed in such a way:
	 *
	 * 28 = 2^2 + 2^3 + 2^4
	 * 33 = 3^2 + 2^3 + 2^4
	 * 49 = 5^2 + 2^3 + 2^4
	 * 47 = 2^2 + 3^3 + 2^4
	 *
	 * How many numbers below fifty million can be expressed as the sum of a prime square, prime cube, and prime fourth power?
	 */
	@Override
	public Long call() {
		final int target = 50_000_000;

		final int limit2 = (int) Math.sqrt(target - (3*3*3) - (2*2*2*2));
		final int limit3 = (int) Math.pow(target - (3*3) - (2*2*2*2), 1d/3);
		final int limit4 = (int) Math.pow(target - (3*3) - (2*2*2), 1d/4);

		final long[] primes = Primes.makePrimeStreamUntil(limit2).toArray();

		final int iLimit2 = primes.length;
		final int iLimit3 = -Arrays.binarySearch(primes, limit3)-1;
		final int iLimit4 = -Arrays.binarySearch(primes, limit4)-1;

		final long[] primes2 = Arrays.stream(primes).map(p -> p*p).toArray();
		final long[] primes3 = Arrays.stream(primes).limit(iLimit3).map(p -> p*p*p).toArray();
		final long[] primes4 = Arrays.stream(primes).limit(iLimit4).map(p -> p*p*p*p).toArray();

		final BitSet bitSet = new BitSet(target-1);

		int sum = 0;
		for (int i2=0; i2<iLimit2; i2++) {
			for (int i3=0; i3<iLimit3; i3++) {
				for (int i4=0; (i4<iLimit4) && ((sum = (int) (primes2[i2] + primes3[i3] + primes4[i4])) < target); i4++) {
					bitSet.set(sum);
				}
			}
		}

		return (long) bitSet.cardinality();
	}
}
