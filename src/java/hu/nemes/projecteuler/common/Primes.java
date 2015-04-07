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
package hu.nemes.projecteuler.common;

import java.util.Arrays;
import java.util.function.LongSupplier;
import java.util.stream.LongStream;

public final class Primes {

	private static long[] primes;
	private static int lastPrimeIdx;

	static {
		primes = new long[]{2, 3, 5, 7, 11, 13, 17, 19};
		lastPrimeIdx = primes.length - 1;
	}

	public static LongSupplier makePrimeSupplier() {
		return new LongSupplier() {

			private int i = 0;

			@Override
			public long getAsLong() {
				while (i > lastPrimeIdx) {
					calculateNext();
				}

				return primes[i++];
			}
		};
	}

	public static LongStream makePrimeStream() {
		return StreamUtils.generate(makePrimeSupplier());
	}

	public static LongStream makePrimeStreamUntil(long n) {
		/*
		ensureCalculatedUntil(n);
		return Arrays.stream(primes, 0, indexOf(n) + 1);
		*/

		return StreamUtils.generate(makePrimeSupplier(), prime -> prime <= n);
	}

	public static long[] getPrimesUntil(long n) {
		ensureCalculatedUntil(n);
        return Arrays.copyOf(primes, indexOf(n));
	}

	private static int indexOf(long n) {
        final int i = Arrays.binarySearch(primes, 0, lastPrimeIdx + 1, n);
        return (i >= 0) ? i : -i - 1;
	}

	private static void ensureCalculatedUntil(long n) {
		while (n > primes[lastPrimeIdx]) {
			calculateNext();
		}
	}

	private static synchronized long calculateNext() {
    	if ((lastPrimeIdx+1) == primes.length) {
    		primes = Arrays.copyOf(primes, Math.min(primes.length * 2, Integer.MAX_VALUE));
    	}
		long x = primes[lastPrimeIdx];
		do {
			x += 2;
		} while (!isPrime(x));
		return primes[++lastPrimeIdx] = x;
	}

	public static long nextPrime(long n) {
		ensureCalculatedUntil(n);
        return primes[indexOf(n)];
	}

	public static boolean isPrime(long n) {
		if ((n <= 1) || ((n % 2) == 0)) {
            return false;
        }

        if (n == 2) {
            return true;
        }

        final long lastPrime = primes[lastPrimeIdx];
        if (n <= lastPrime) {
        	return Arrays.binarySearch(primes, 0, lastPrimeIdx + 1, n) >= 0;
        }

        final long sqrt = (long) Math.sqrt(n);

        if (sqrt > lastPrime) {
        	if ((sqrt == nextPrime(sqrt)) && (n == (sqrt * sqrt))) {
        		return false;
        	}
        }

        int i = 1;
    	long prime;
        do {
        	prime = primes[i++];
        	if ((n % prime) == 0) {
        		return false;
        	}
        } while (prime < sqrt);
        return true;
	}
}
