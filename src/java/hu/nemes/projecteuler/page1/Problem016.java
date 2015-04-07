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

import java.math.BigInteger;
import java.util.concurrent.Callable;

public final class Problem016 implements Callable<Long> {

	/**
	 * Power digit sum
	 * Problem 16
	 *
	 * 2^15 = 32768 and the sum of its digits is 3 + 2 + 7 + 6 + 8 = 26.
	 *
	 * What is the sum of the digits of the number 2^1000?
	 */
	@Override
	public Long call() {
		return BigInteger
				.valueOf(2).pow(1000) // or alternatively: .ZERO.setBit(1000)
				.toString()
				.chars()
				.parallel()
				.mapToLong(f -> f - '0')
				.sum();
	}
}
