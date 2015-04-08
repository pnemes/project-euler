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

public final class Problem108 implements Callable<Long> {

	/**
	 * Diophantine reciprocals I
	 * Problem 108
	 *
	 * What is the least value of n for which the number of distinct solutions exceeds one-thousand?
	 *
	 * NOTE: This problem is an easier version of Problem 110; it is strongly advised that you solve this one first.
	 */
	@Override
	public Long call() throws IOException, URISyntaxException {

		// 1/x + 1/y = 1/n
		// yn + xn = xy
		// ...
		// n2 = sr

		long n = 1;
		for (long s = 0; s < 2000; n++) {
			s = Arithmetic.numberOfDivisorsPow(n, 2);
		}
		return --n;
    }
}
