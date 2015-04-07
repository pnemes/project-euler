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

import hu.nemes.projecteuler.common.StreamUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.IntStream;

public final class Problem068 implements Callable<String> {

	/**
	 * Magic 5-gon ring
	 * Problem 68
	 *
	 * Consider the following "magic" 3-gon ring, filled with the numbers 1 to 6, and each line adding to nine.
	 *
	 * Working clockwise, and starting from the group of three with the numerically lowest external node (4,3,2 in this example), each solution can be described uniquely. For example, the above solution can be described by the set: 4,3,2; 6,2,1; 5,1,3.
	 *
	 * It is possible to complete the ring with four different totals: 9, 10, 11, and 12. There are eight solutions in total.
	 * Total	Solution Set
	 * 9	4,2,3; 5,3,1; 6,1,2
	 * 9	4,3,2; 6,2,1; 5,1,3
	 * 10	2,3,5; 4,5,1; 6,1,3
	 * 10	2,5,3; 6,3,1; 4,1,5
	 * 11	1,4,6; 3,6,2; 5,2,4
	 * 11	1,6,4; 5,4,2; 3,2,6
	 * 12	1,5,6; 2,6,4; 3,4,5
	 * 12	1,6,5; 3,5,4; 2,4,6
	 *
	 * By concatenating each group it is possible to form 9-digit strings; the maximum string for a 3-gon ring is 432621513.
	 *
	 * Using the numbers 1 to 10, and depending on arrangements, it is possible to form 16- and 17-digit strings. What is the maximum 16-digit string for a "magic" 5-gon ring?
	 */
	@Override
	public String call() throws NumberFormatException, IOException, URISyntaxException {
		return StreamUtils
				.generate(StreamUtils.makePermutationIterator(IntStream.rangeClosed(6, 10).toArray()))
				.<int[]>flatMap(a1 -> {
					return StreamUtils
							.generate(StreamUtils.makePermutationIterator(IntStream.rangeClosed(1, 5).toArray()))
							.map(a2 -> {
								final int[] a3 = Arrays.copyOf(a1, 10);
								System.arraycopy(a2, 0, a3, 5, 5);
								return a3;
							});
				})
				.filter(a -> {
					final int s = a[0] + a[5] + a[6];
					for (int i = 1; i < 5; i++) {
						if (s != (a[i] +
								 a[((i + 5) % 5) + 5] +
								 a[((i + 6) % 5) + 5])) {
							return false;
						}
					}
					return true;
				})
				.map(a -> {
					int l = 0;
					for (int i = 0; i < 5; i++) {
						if (a[i] < a[l]) {
							l = i;
						}
					}
					final StringBuilder sb = new StringBuilder();
					for (int i = 0; i < 5; i++) {
						final int i2 = ((l+i) % 5);

						sb.append(a[i2]);
						sb.append(a[((i2 + 5) % 5) + 5]);
						sb.append(a[((i2 + 6) % 5) + 5]);
					}

					return new BigInteger(sb.toString());
				})
				.max(Comparator.comparing(Function.identity()))
				.get()
				.toString();

	}
}
