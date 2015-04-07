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

import java.util.AbstractMap;
import java.util.BitSet;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.stream.IntStream;

public final class Problem095 implements Callable<Long> {

	/**
	 * Problem 95
	 *
	 * The proper divisors of a number are all the divisors excluding the number itself. For example, the proper divisors of 28 are 1, 2, 4, 7, and 14. As the sum of these divisors is equal to 28, we call it a perfect number.
	 *
	 * Interestingly the sum of the proper divisors of 220 is 284 and the sum of the proper divisors of 284 is 220, forming a chain of two numbers. For this reason, 220 and 284 are called an amicable pair.
	 *
	 * Perhaps less well known are longer chains. For example, starting with 12496, we form a chain of five numbers:
	 *
	 * 12496 → 14288 → 15472 → 14536 → 14264 (→ 12496 → ...)
	 *
	 * Since this chain returns to its starting point, it is called an amicable chain.
	 *
	 * Find the smallest member of the longest amicable chain with no element exceeding one million.
	 */
	@Override
	public Long call() {
		final int limit = 1_000_000;

		final long[] sumOfProperDivisors = new long[limit];
		IntStream
			.rangeClosed(1, limit/2)
			.forEach(n -> {
				for (int i=n*2; i < limit; i+=n) {
					sumOfProperDivisors[i] += n;
				}
			});


		final BitSet bitSet = new BitSet(limit);

		return (long) IntStream
			.rangeClosed(1, limit)
			.parallel()
			.mapToObj(n -> {

				final Set<Integer> visited = new LinkedHashSet<>();
				int c = n;
				if (!bitSet.get(c)) {
					while ((c < limit)) {
						visited.add(c);
						bitSet.set(c);

						if (bitSet.get(c = (int) sumOfProperDivisors[c])) {
							int i = 0;
							int min = c;
							boolean found = false;
							for (final int e : visited) {
								if (!found) {
									found |= (e == c);
									i++;
								}
								else {
									min = Math.min(min, e);
								}
							}
							return new AbstractMap.SimpleImmutableEntry<>(visited.size() - i, min);
						}
					};
				}
				return null;
			})
			.filter(e -> e != null)
			.max(Comparator.comparingInt(Map.Entry::getKey))
			.get()
			.getValue();
	}
}
