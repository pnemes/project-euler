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

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.stream.LongStream;

public final class Problem017 implements Callable<Long> {

	/**
	 * Number letter counts
	 * Problem 17
	 *
	 * If the numbers 1 to 5 are written out in words: one, two, three, four, five, then there are 3 + 3 + 5 + 4 + 4 = 19 letters used in total.
	 *
	 * If all the numbers from 1 to 1000 (one thousand) inclusive were written out in words, how many letters would be used?
	 *
	 * NOTE: Do not count spaces or hyphens. For example, 342 (three hundred and forty-two) contains 23 letters and 115 (one hundred and fifteen) contains 20 letters. The use of "and" when writing out numbers is in compliance with British usage.
	 */
	private final static String andWord = "and";
	private final static String[] words20 = new String[] {
		"zero",	"one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
		"ten", 	"eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"
	};
	private final static String[] words100 = new String[] {
		"twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"
	};
	private final static String[] wordsExp = new String[] {
		"hundred", "thousand", "million", "billion"
	};

	private final static long andWordLen = andWord.length();
	private final static long[] words20Len = Arrays.stream(words20).mapToLong(String::length).toArray();
	private final static long[] words100Len = Arrays.stream(words100).mapToLong(String::length).toArray();
	private final static long[] wordsExpLen = Arrays.stream(wordsExp).mapToLong(String::length).toArray();

	private final static long[] expLen = new long[] {2, 3, 6, 9};

	private final static long decompose(long f) {

		boolean a = false;
		long s = 0;
		while (f >= 100) {
			final int e = (int) Math.log10(f);

			int i = expLen.length - 1;
			while ((i >= 0) && (expLen[i] > e)) {
				i--;
			}
			final int r = (int) Math.pow(10, expLen[i]);

			s += decompose(f / r);
			s += wordsExpLen[i];

			f %= r;
			if ((!a) && (f > 0)) {
				a = true;
				s += andWordLen;
			}
		}

		if (f >= 20) {
			final int i = ((int) (f / 10)) - 2;

			s += words100Len[i];
			f %= 10;
		}

		if ((f > 0) || (s == 0)) {
			final int i = (int) f;

			s += words20Len[i];
		}

		return s;
	};

	@Override
	public Long call() {
		return LongStream
				.range(1, 1001)
				.parallel()
				.map(Problem017::decompose)
				.sum();
	}
}
