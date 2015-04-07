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

import hu.nemes.projecteuler.common.Arithmetic;
import hu.nemes.projecteuler.common.Primes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;

public final class Problem060 implements Callable<Long> {

	/**
	 * Prime pair sets
	 * Problem 60
	 *
	 * The primes 3, 7, 109, and 673, are quite remarkable. By taking any two primes and concatenating them in any order the result will always be prime. For example, taking 7 and 109, both 7109 and 1097 are prime. The sum of these four primes, 792, represents the lowest sum for a set of four primes with this property.
	 *
	 * Find the lowest sum for a set of five primes for which any two primes concatenate to produce another prime.
	 */
	@Override
	public Long call() {

		long[] primes = new long[1500];
		int i = -1;
		long p = 2;

		final Map<Long, Set<Long>> masterMap = new LinkedHashMap<>();
		final List<Long> backwardSet = new ArrayList<>();

		int size;
		long sum = 0;
		do {
			do {
				if (++i == primes.length) {
					primes = Arrays.copyOf(primes, i * 2);
				}

				primes[i] = (p = Primes.nextPrime(++p));

				backwardSet.clear();
				for (int a = 0; a < i; a++) {
					if (Primes.isPrime(Arithmetic.concatDigits(primes[a], primes[i])) &&
						Primes.isPrime(Arithmetic.concatDigits(primes[i], primes[a]))) {

						masterMap.computeIfAbsent(primes[a], k -> new TreeSet<Long>()).add(primes[i]);
						backwardSet.add(primes[a]);
					}
				}

			} while (i < 4);

			if ((size = backwardSet.size()) == 0) {
				continue;
			}

			Set<Long> entry;
			final int e = i;
			for (int a=0; (a<size) && (sum == 0); a++) {
				for (int b=a+1; (b<size) && (sum == 0); b++) {
					for (int c=b+1; (c<size) && (sum == 0); c++) {
						for (int d=c+1; (d<size) && (sum == 0); d++) {

							final long[] x = new long[]{
									backwardSet.get(a),
									backwardSet.get(b),
									backwardSet.get(c),
									backwardSet.get(d),
									primes[e]};

							boolean rule = true;
							for (int i1=0; (i1<4) && rule; i1++) {
								for (int i2=i1+1; (i2<4) && rule; i2++) {
									rule &= (((entry = masterMap.get(x[i1])) != null) && entry.contains(x[i2]));
								}
							}

							if (rule) {
								for (final long o : x) {
									sum += o;
								}
							}
						}
					}
				}
			}
		} while (sum == 0);

		return sum;
	}
}
