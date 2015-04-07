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
package hu.nemes.projecteuler.page3;

import hu.nemes.projecteuler.common.Arithmetic;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.Callable;

public final class Problem110 implements Callable<Long> {

	/**
	 * Diophantine reciprocals II
	 * Problem 110
	 *
	 * It can be verified that when n = 1260 there are 113 distinct solutions and this is the least value of n for which the total number of distinct solutions exceeds one hundred.
	 *
	 * What is the least value of n for which the number of distinct solutions exceeds four million?
	 *
	 * NOTE: This problem is a much more difficult version of Problem 108 and as it is well beyond the limitations of a brute force approach it requires a clever implementation.
	 */
	@Override
	public Long call() throws IOException, URISyntaxException {

		// 1/x + 1/y = 1/n
		// yn + xn = xy
		// ...
		// n2 = sr

		long n = 1;
		for (long s = 0; s < 8_000_000; n++) {
			s = Arithmetic.numberOfDivisors(n*n);
		}
		return --n;
    }
}
