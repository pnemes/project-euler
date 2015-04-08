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

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.stream.LongStream;

public final class Problem081 implements Callable<Long> {

	/**
	 * Path sum: two ways
	 * Problem 81
	 *
	 * In the 5 by 5 matrix below, the minimal path sum from the top left to the bottom right, by only moving to the right and down, is indicated in bold red and is equal to 2427.
	 *
	 * Find the minimal path sum, in matrix.txt (right click and "Save Link/Target As..."), a 31K text file containing a 80 by 80 matrix, from the top left to the bottom right by only moving right and down.
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	@Override
	public Long call() throws NumberFormatException, IOException, URISyntaxException {

		final long[][] data =
			Files
				.lines(new File(Problem082.class.getResource("matrix.txt").toURI()).toPath())
				.map(s -> s.split(","))
				.map(Arrays::stream)
				.map(s -> s.mapToLong(Long::parseLong))
				.map(LongStream::toArray)
				.toArray(x -> new long[x][]);

		final int l = data.length-1;
		for (int i = 1; i <= l; i++) {
			data[i][0] += data[i-1][0];
			data[0][i] += data[0][i-1];
		}

		for (int a = 1; a <= l; a++) {
			for (int b = 1; b <= l; b++) {
				data[a][b] += Math.min(data[a-1][b], data[a][b-1]);
			}
		}

		return data[l][l];
	}
}
