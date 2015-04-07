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

public final class Problem112 implements Callable<Long> {

	/**
	 * Bouncy numbers
	 * Problem 112
	 *
	 * Working from left-to-right if no digit is exceeded by the digit to its left it is called an increasing number; for example, 134468.
	 *
	 * Similarly if no digit is exceeded by the digit to its right it is called a decreasing number; for example, 66420.
	 *
	 * We shall call a positive integer that is neither increasing nor decreasing a "bouncy" number; for example, 155349.
	 *
	 * Clearly there cannot be any bouncy numbers below one-hundred, but just over half of the numbers below one-thousand (525) are bouncy. In fact, the least number for which the proportion of bouncy numbers first reaches 50% is 538.
	 *
	 * Surprisingly, bouncy numbers become more and more common and by the time we reach 21780 the proportion of bouncy numbers is equal to 90%.
	 *
	 * Find the least number for which the proportion of bouncy numbers is exactly 99%.
	 */
	@Override
	public Long call() throws IOException, URISyntaxException {

		long t = 99;
		long b = 0;

		do  {
		    if (Arithmetic.isBouncy(++t)) {
		        b++;
		    }
		} while (b < (0.99d * t));
		return t;
    }
}
