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

public final class Problem078 implements Callable<Long> {

	/**
	 * Coin partitions
	 * Problem 78
	 *
	 * Let p(n) represent the number of different ways in which n coins can be separated into piles. For example, five coins can separated into piles in exactly seven different ways, so p(5)=7.
	 * OOOOO
	 * OOOO   O
	 * OOO   OO
	 * OOO   O   O
	 * OO   OO   O
	 * OO   O   O   O
	 * O   O   O   O   O
	 *
	 * Find the least value of n for which p(n) is divisible by one million.
	 */
	@Override
	public Long call() {
		int[] p = new int[1024];
		p[0] = 1;

		int n = 0;
		do {
			++n;
		    int i = 0;
		    int penta = 1;

		    if (n == p.length) {
		    	p = Arrays.copyOf(p, p.length * 2);
		    }

		    while (penta <= n) {
		        final int sign = ((i % 4) > 1) ? -1 : 1;
		        p[n] += sign * p[n - penta];
		        p[n] %= 1000000;
		        i++;

		        final int j = ((i % 2) == 0) ? (i / 2) + 1 : -((i / 2) + 1);
		        penta = (j * ((3 * j) - 1)) / 2;
		    }
		} while (p[n] != 0);

		return (long) n;
	}
}
