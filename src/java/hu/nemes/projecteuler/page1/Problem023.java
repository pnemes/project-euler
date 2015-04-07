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

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.stream.IntStream;

public final class Problem023 implements Callable<Long> {

	/**
	 * Non-abundant sums
	 * Problem 23
	 *
	 * A perfect number is a number for which the sum of its proper divisors is exactly equal to the number. For example, the sum of the proper divisors of 28 would be 1 + 2 + 4 + 7 + 14 = 28, which means that 28 is a perfect number.
	 *
	 * A number n is called deficient if the sum of its proper divisors is less than n and it is called abundant if this sum exceeds n.
	 *
	 * As 12 is the smallest abundant number, 1 + 2 + 3 + 4 + 6 = 16, the smallest number that can be written as the sum of two abundant numbers is 24. By mathematical analysis, it can be shown that all integers greater than 28123 can be written as the sum of two abundant numbers. However, this upper limit cannot be reduced any further by analysis even though it is known that the greatest number that cannot be expressed as the sum of two abundant numbers is less than this limit.
	 *
	 * Find the sum of all the positive integers which cannot be written as the sum of two abundant numbers.
	 */
	private final static int max = 28_123;

	@Override
	public Long call() throws Exception {

		final long[] sumOfProperDivisors = new long[max];
		IntStream
			.rangeClosed(1, max/2)
			.forEach(n -> {
				for (int i=n*2; i < max; i+=n) {
					sumOfProperDivisors[i] += n;
				}
			});


        final int[] abundants = IntStream
        			.range(1, max)
                    .filter(n -> sumOfProperDivisors[n] > n)
                    .sorted()
                    .toArray();

        return (long) IntStream
                .rangeClosed(1, max)
                .parallel()
                .filter(f -> {
                        for (int j = 0; abundants[j] <= (f / 2); j++) {
                            if (Arrays.binarySearch(abundants, f - abundants[j]) >=0) {
                                return false;
                            }
                        }
                        return true;
                    })
		        .sum();
	}
}
