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

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.stream.IntStream;

public final class Problem090 implements Callable<Long> {

	/**
	 * Cube digit pairs
	 * Problem 90
	 *
	 * Each of the six faces on a cube has a different digit (0 to 9) written on it; the same is done to a second cube. By placing the two cubes side-by-side in different positions we can form a variety of 2-digit numbers.
	 *
	 * For example, the square number 64 could be formed:
	 *
	 * In fact, by carefully choosing the digits on both cubes it is possible to display all of the square numbers below one-hundred: 01, 04, 09, 16, 25, 36, 49, 64, and 81.
	 *
	 * For example, one way this can be achieved is by placing {0, 5, 6, 7, 8, 9} on one cube and {1, 2, 3, 4, 8, 9} on the other cube.
	 *
	 * However, for this problem we shall allow the 6 or 9 to be turned upside-down so that an arrangement like {0, 5, 6, 7, 8, 9} and {1, 2, 3, 4, 6, 7} allows for all nine square numbers to be displayed; otherwise it would be impossible to obtain 09.
	 *
	 * In determining a distinct arrangement we are interested in the digits on each cube, not the order.
	 *
	 * {1, 2, 3, 4, 5, 6} is equivalent to {3, 6, 4, 1, 2, 5}
	 * {1, 2, 3, 4, 5, 6} is distinct from {1, 2, 3, 4, 5, 9}
	 *
	 * But because we are allowing 6 and 9 to be reversed, the two distinct sets in the last example both represent the extended set {1, 2, 3, 4, 5, 6, 9} for the purpose of forming 2-digit numbers.
	 *
	 * How many distinct arrangements of the two cubes allow for all of the square numbers to be displayed?
	 */
	@Override
	public Long call() {
		final int[] squares = IntStream
				.range(1, 10)
				.map(i -> i*i)
				.toArray();

		final int[][] digits = Arrays
				.stream(squares)
				.mapToObj(n -> new int[]{n/10, n%10})
				.toArray(a -> new int[a][]);

		final int[][] rules = IntStream
				.range(0, 10)
				.mapToObj(n -> Arrays
						.stream(digits)
						.filter(a -> (a[0] == n) || (a[1] == n))
						.mapToInt(a -> a[0] == n ? a[1] : a[0])
						.filter(a -> a != 0)
						.toArray()
				)
				.peek(a -> System.out.println(Arrays.toString(a)))
				.toArray(a -> new int[a][]);
		return (long) generate(0, rules, new int[6], new int[6], 0, 0);
	}

	private int generate(int s, int[][] rules, int[] cube1, int[] cube2, int i1, int i2) {
		for (int x=0; x<(10-i1); x++) {
			cube1[i1] = x;
			applyRule(rules, cube1, cube2, i1, i2);
		}
		System.out.println(Arrays.toString(cube1));
		System.out.println(Arrays.toString(cube2));
		return s;
	}

	private void applyRule(int[][] rules, int[] cube1, int[] cube2, int i1, int i2) {
		final int[] r = rules[cube1[i1]];
		for (final int element : r) {
			if (Arrays.binarySearch(cube2, 0, i2, element) >= 0) {
				continue;
			}
			cube2[i2] = element;
			applyRule(rules, cube2, cube1, i2, i1+1);
		}
	}
}
