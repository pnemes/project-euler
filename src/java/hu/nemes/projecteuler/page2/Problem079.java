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

import hu.nemes.projecteuler.common.StreamUtils;

import java.util.Arrays;
import java.util.concurrent.Callable;

public final class Problem079 implements Callable<Long> {

	/**
	 * Passcode derivation
	 * Problem 79
	 *
	 * A common security method used for online banking is to ask the user for three random characters from a passcode. For example, if the passcode was 531278, they may ask for the 2nd, 3rd, and 5th characters; the expected reply would be: 317.
	 *
	 * The text file, keylog.txt, contains fifty successful login attempts.
	 *
	 * Given that the three characters are always asked for in order, analyse the file so as to determine the shortest possible secret passcode of unknown length.
	 */
	@Override
	public Long call() {

		final long[] after = new long[10];
		final long[] before = new long[10];

		StreamUtils
			.generateTokenizerStream(Problem079.class.getResourceAsStream("keylog.txt"))
			.map(Integer::parseInt)
			.forEach(i -> {
				final int c = i % 10;
				final int b = (i /= 10) % 10;
				final int a = (i /= 10) % 10;

				before[b] |= 1 << a;
				before[c] |= 1 << a;
				before[c] |= 1 << b;

				after[a] |= 1 << b;
				after[a] |= 1 << c;
				after[b] |= 1 << c;
			});

		final long[] afterCardinality = new long[10];
		final long mask = ((1 << 10) - 1);
		long seen = 0x0b;
		for (int i = 0; i < 10; i++) {
			seen |= before[i];
			seen |= after[i];
			afterCardinality[i] = (Long.bitCount(after[i]) << 10) | i;
		}

		Arrays.sort(afterCardinality);

		long x = 0;
		int i = afterCardinality.length - 1;
		do {
			final long n = afterCardinality[i] & mask;
			if ((seen & (1 << n)) != 0) {
				x *= 10;
				x += n;
			}
		} while (i-- > 0);

		return x;
	}
}
