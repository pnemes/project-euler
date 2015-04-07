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

import java.util.concurrent.Callable;

public final class Problem031 implements Callable<Long> {

	/**
	 * Coin sums
	 * Problem 31
	 *
	 * In England the currency is made up of pound (P) and pence (p), and there are eight coins in general circulation:
	 *
	 *     1p, 2p, 5p, 10p, 20p, 50p, 1P (100p) and 2P (200p).
	 *
	 * It is possible to make 2P in the following way:
	 *
	 *     1x1P + 1x50p + 2x20p + 1x5p + 1x2p + 3x1p
	 *
	 * How many different ways can 2P be made using any number of coins?
	 */
	private final static int[] coinSizes = new int[]{ 1, 2, 5, 10, 20, 50, 100, 200 };
	private final static int target = 200;

	@Override
	public Long call() {
		final int[] ways = new int[target + 1];
		ways[0] = 1;

		for (final int coinSize : coinSizes) {
		    for (int j = coinSize; j <= target; j++) {
		        ways[j] += ways[j - coinSize];
		    }
		}

		return (long) ways[target];
	}
}
