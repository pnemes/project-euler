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

import hu.nemes.projecteuler.common.Arithmetic;

import java.util.concurrent.Callable;
import java.util.stream.LongStream;

public final class Problem036 implements Callable<Long> {

	/**
	 * Double-base palindromes
	 * Problem 36
	 *
	 * The decimal number, 585 = 10010010012 (binary), is palindromic in both bases.
	 *
	 * Find the sum of all numbers, less than one million, which are palindromic in base 10 and base 2.
	 *
	 * (Please note that the palindromic number, in either base, may not include leading zeros.)
	 */
	@Override
	public Long call() {
		return LongStream
				.range(1, 1_000_000)
				.parallel()
				.filter(n -> Arithmetic.isPalindrome(n, 2) && Arithmetic.isPalindrome(n, 10))
				.sum();
	}
}
