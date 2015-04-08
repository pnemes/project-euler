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

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.LongStream;

public final class Problem039 implements Callable<Long> {

	/**
	 * Integer right triangles
	 * Problem 39
	 *
	 * If p is the perimeter of a right angle triangle with integral length sides, {a,b,c}, there are exactly three solutions for p = 120.
	 *
	 * {20,48,52}, {24,45,51}, {30,40,50}
	 *
	 * For which value of p <= 1000, is the number of solutions maximized?
	 */
	private final static int maxP = 1000;

	@Override
	public Long call() {
		return LongStream
				.rangeClosed(1, maxP / 2)
				.parallel()
				.map(p -> 2 * p)
				.<Map.Entry<Long, Long>>mapToObj(p ->
						new AbstractMap.SimpleImmutableEntry<>(p ,
						LongStream
							.rangeClosed(2, (int) (p / (2 + Math.sqrt(2))))
							.parallel()
							.filter(a -> ((p*(p - (2*a))) % (2*(p-a))) == 0)
							.count()))
				.max(Comparator.comparingLong(Map.Entry::getValue))
				.get()
				.getKey();
	}
}
