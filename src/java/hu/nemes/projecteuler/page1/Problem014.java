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

import hu.nemes.projecteuler.common.StreamUtils;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.concurrent.Callable;
import java.util.stream.LongStream;

public final class Problem014 implements Callable<Long> {

	/**
	 * Longest Collatz sequence
	 * Problem 14
	 *
	 * The following iterative sequence is defined for the set of positive integers:
	 *
	 * n -> n/2 (n is even)
	 * n -> 3n + 1 (n is odd)
	 *
	 * Using the rule above and starting with 13, we generate the following sequence:
	 * 13 -> 40 -> 20 -> 10 -> 5 -> 16 -> 8 -> 4 -> 2 -> 1
	 *
	 * It can be seen that this sequence (starting at 13 and finishing at 1) contains 10 terms. Although it has not been proved yet (Collatz Problem), it is thought that all starting numbers finish at 1.
	 *
	 * Which starting number, under one million, produces the longest chain?
	 *
	 * NOTE: Once the chain starts the terms are allowed to go above one million.
	 */
	@Override
	public Long call() {
		return LongStream
				.range(1, 1_000_000)
				.parallel()
				.mapToObj(i ->
					new AbstractMap.SimpleImmutableEntry<>(
							i,
							StreamUtils.makeCollatzStream(i).count()))
				.max(Comparator.comparingLong(AbstractMap.SimpleImmutableEntry::getValue))
				.get()
				.getKey();
	}
}
