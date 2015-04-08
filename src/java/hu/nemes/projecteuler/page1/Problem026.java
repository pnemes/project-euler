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
import java.util.concurrent.Callable;
import java.util.stream.IntStream;

public final class Problem026 implements Callable<Long> {

	/**
	 * Reciprocal cycles
	 * Problem 26
	 *
	 * A unit fraction contains 1 in the numerator. The decimal representation of the unit fractions with denominators 2 to 10 are given:
	 *
	 *     1/2	= 	0.5
	 *     1/3	= 	0.(3)
	 *     1/4	= 	0.25
	 *     1/5	= 	0.2
	 *     1/6	= 	0.1(6)
	 *     1/7	= 	0.(142857)
	 *     1/8	= 	0.125
	 *     1/9	= 	0.(1)
	 *     1/10	= 	0.1
	 *
	 * Where 0.1(6) means 0.166666..., and has a 1-digit recurring cycle. It can be seen that 1/7 has a 6-digit recurring cycle.
	 *
	 * Find the value of d < 1000 for which 1/d contains the longest recurring cycle in its decimal fraction part.
	 */
	@Override
	public Long call() {
		return IntStream
			.range(2, 1000)
			.parallel()
			.mapToObj(seed -> {

			    final int[] foundRemainders = new int[seed];
			    int value = 1;
			    int position = 0;
			    final int length;

			    while ((foundRemainders[value] == 0) && (value != 0)) {
			        foundRemainders[value] = position;
			        value *= 10;
			        value %= seed;
			        position++;
			    }

			    if ((position - foundRemainders[value]) > 0) {
			    	length = position - foundRemainders[value];
			    }
			    else {
			    	length = 0;
			    }

				return new AbstractMap.SimpleImmutableEntry<>(
						seed,
						length);
			})
			.max(Comparator.comparingLong(AbstractMap.SimpleImmutableEntry::getValue))
			.get()
			.getKey()
			.longValue();
	}
}
