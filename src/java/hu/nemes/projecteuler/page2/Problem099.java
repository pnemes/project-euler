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

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URISyntaxException;
import java.util.concurrent.Callable;

public final class Problem099 implements Callable<Long> {

	/**
	 * Largest exponential
	 * Problem 99
	 *
	 * Comparing two numbers written in index form like 211 and 37 is not difficult, as any calculator would confirm that 2^11 = 2048 < 3^7 = 2187.
	 *
	 * However, confirming that 632382^518061 > 519432^525806 would be much more difficult, as both numbers contain over three million digits.
	 *
	 * Using base_exp.txt (right click and 'Save Link/Target As...'), a 22K text file containing one thousand lines with a base/exponent pair on each line, determine which line number has the greatest numerical value.
	 *
	 * NOTE: The first two lines in the file represent the numbers in the example given above.
	 */
	@Override
	public Long call() throws IOException, URISyntaxException {
		final LineNumberReader reader = new LineNumberReader(new InputStreamReader(Problem099.class.getResourceAsStream("base_exp.txt")));

		return (long) StreamUtils
			.<int[]>generate(
				f -> {
					try {
						final String line = reader.readLine();
						final boolean hasNext = (line != null);
		                if (hasNext) {
		                	final int i = line.indexOf(',');

		                	final int n = reader.getLineNumber();
		                	final int a = Integer.parseInt(line.substring(0, i));
		                	final int b = Integer.parseInt(line.substring(i+1, line.length()));

		                	f.accept(new int[] {n, (int) (b * Math.log(a))});
		                }
		                return hasNext;
					}
					catch (final Exception e) {
						throw new RuntimeException(e);
					}
				},
				reader)
			.max((a1, a2) -> a1[1] - a2[1])
			.get()[0];
    }
}
