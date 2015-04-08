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

public final class Problem082 implements Callable<Long> {

	/**
	 * Path sum: three ways
	 * Problem 82
	 *
	 * NOTE: This problem is a more challenging version of Problem 81.
	 *
	 * The minimal path sum in the 5 by 5 matrix below, by starting in any cell in the left column and finishing in any cell in the right column, and only moving up, down, and right, is indicated in red and bold; the sum is equal to 994.
	 *
	 * Find the minimal path sum, in matrix.txt (right click and "Save Link/Target As..."), a 31K text file containing a 80 by 80 matrix, from the left column to the right column.
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

		final int length = data.length;
		final long[] solution = Arrays
				.stream(data)
				.mapToLong(a -> a[length-1])
				.toArray();

		for (int i = length - 2; i >= 0; i--) {

			solution[0] += data[0][i];
		    for (int j = 1; j < length; j++) {
		    	solution[j] = Math.min(
		    			solution[j - 1],
		    			solution[j]);
		    	solution[j] += data[j][i];
		    }

		    for (int j = length - 2; j >= 0; j--) {
		    	solution[j] = Math.min(
		    			solution[j],
		    			solution[j+1] + data[j][i]);
		    }
		}

		return Arrays
				.stream(solution)
				.min()
				.getAsLong();
	}
}
