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

import java.util.concurrent.Callable;

public final class Problem076 implements Callable<Long> {

	/**
	 * Counting summations
	 * Problem 76
	 *
	 * It is possible to write five as a sum in exactly six different ways:
	 *
	 * 4 + 1
	 * 3 + 2
	 * 3 + 1 + 1
	 * 2 + 2 + 1
	 * 2 + 1 + 1 + 1
	 * 1 + 1 + 1 + 1 + 1
	 *
	 * How many different ways can one hundred be written as a sum of at least two positive integers?
	 */
	@Override
	public Long call() {
		final int n = 100;
		final int[] ways = new int[n + 1];

		ways[0] = 1;
		for (int i = 1; i < n; i++) {
		    for (int j = i; j <= n; j++) {
		    	ways[j] += ways[j - i];
		    }
		}

		return (long) ways[100];
	}
}
