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
import java.util.stream.Stream;

public final class Problem067 implements Callable<Long> {

	/**
	 * Maximum path sum II
	 * Problem 67
	 *
	 * By starting at the top of the triangle below and moving to adjacent numbers on the row below, the maximum total from top to bottom is 23.
	 *
	 * 3
	 * 7 4
	 * 2 4 6
	 * 8 5 9 3
	 *
	 * That is, 3 + 7 + 4 + 9 = 23.
	 *
	 * Find the maximum total from top to bottom in triangle.txt (right click and 'Save Link/Target As...'), a 15K text file containing a triangle with one-hundred rows.
	 *
	 * NOTE: This is a much more difficult version of Problem 18. It is not possible to try every route to solve this problem, as there are 299 altogether! If you could check one trillion (1012) routes every second it would take over twenty billion years to check them all. There is an efficient algorithm to solve it. ;o)
	 */
	@Override
	public Long call() throws NumberFormatException, IOException, URISyntaxException {
		final Stream<long[]> data = Files
				.lines(new File(Problem067.class.getResource("triangle.txt").toURI()).toPath())
				.map(l -> l.split(" "))
				.map(a -> Arrays.stream(a).mapToLong(e -> Long.parseLong(e)).toArray());

		return Arrays.stream(
				data.sequential()
					.reduce((a, b) -> {
						b[0] += a[0];
						for (int i = 1; i < (b.length - 1); i++) {
							b[i] += Math.max(a[i - 1], a[i]);
						}
						b[b.length - 1] += a[a.length - 1];

						return b;
					})
					.get())
			.parallel()
			.max()
			.getAsLong();
	}
}
