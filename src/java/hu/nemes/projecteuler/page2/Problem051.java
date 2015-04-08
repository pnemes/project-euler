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
import hu.nemes.projecteuler.common.StreamUtils;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public final class Problem051 implements Callable<Long> {

	/**
	 * Prime digit replacements
	 * Problem 51
	 *
	 * By replacing the 1st digit of the 2-digit number *3, it turns out that six of the nine possible values: 13, 23, 43, 53, 73, and 83, are all prime.
	 *
	 * By replacing the 3rd and 4th digits of 56**3 with the same digit, this 5-digit number is the first example having seven primes among the ten generated numbers, yielding the family: 56003, 56113, 56333, 56443, 56663, 56773, and 56993. Consequently 56003, being the first member of this family, is the smallest prime with this property.
	 *
	 * Find the smallest prime which, by replacing part of the number (not necessarily adjacent digits) with the same digit, is part of an eight prime value family.
	 */
	@Override
	public Long call() {
		final int[][][] permutations = Stream
				.of(
					new int[] {0, 0, 0, 1, 1},
					new int[] {0, 0, 0, 1, 1, 1})
				.<int[][]>map(t -> StreamUtils
						.generate(StreamUtils.makePermutationIterator(t))
						.filter(a -> a[a.length - 1] == 1)
						.map(a -> Arrays.copyOf(a, a.length))
						.toArray(int[][]::new))
				.toArray(int[][][]::new);

		return LongStream
				.range(0, 500 - 5)
				.parallel()
				.map(i -> (2 * i) + 11)
				.filter(i -> (i % 5) != 0)
				.mapToObj(i -> new AbstractMap.SimpleImmutableEntry<>(i, permutations[(i < 100) ? 0 : 1]))
				.<Map.Entry<Long, long[]>>flatMap(e ->
						Arrays.stream(e.getValue())
							.map(p -> IntStream
									.rangeClosed(p[0] == 0 ? 1 : 0, 9)
									.mapToLong(gd -> {
										long i = Arithmetic.reverseDigits(e.getKey());
										long x = 0;
										for (final int pd : p) {
											if (pd == 0) {
												x = (x * 10) + gd;
											}
											else {
												x = (x * 10) + (i % 10);
												i /= 10;
											}
										}
										return x;
									})
									.toArray()
							)
							.map(a -> new AbstractMap.SimpleImmutableEntry<>(e.getKey(), a))
				)
				.sequential()
				.filter(e -> {
					int num = 0;
					for (final long x : e.getValue()) {
						if (Primes.isPrime(x)) {
							if (++num >= 8) {
								return true;
							}
						}
					}
					return false;
				})
				.min(Comparator.comparingLong(Map.Entry::getKey))
				.get()
				.getValue()[0];
	}
}
