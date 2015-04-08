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

import hu.nemes.projecteuler.common.StreamUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.concurrent.Callable;

public final class Problem102 implements Callable<Long> {

	/**
	 * Triangle containment
	 * Problem 102
	 *
	 * Three distinct points are plotted at random on a Cartesian plane, for which -1000 ≤ x, y ≤ 1000, such that a triangle is formed.
	 *
	 * Consider the following two triangles:
	 *
	 * A(-340,495), B(-153,-910), C(835,-947)
	 *
	 * X(-175,41), Y(-421,-714), Z(574,-645)
	 *
	 * It can be verified that triangle ABC contains the origin, whereas triangle XYZ does not.
	 *
	 * Using triangles.txt (right click and 'Save Link/Target As...'), a 27K text file containing the co-ordinates of one thousand "random" triangles, find the number of triangles for which the interior contains the origin.
	 *
	 * NOTE: The first two examples in the file represent the triangles in the example given above.
	 */
	@Override
	public Long call() throws IOException {
		final LineNumberReader reader = new LineNumberReader(new InputStreamReader(Problem102.class.getResourceAsStream("triangles.txt")));

		return (long) StreamUtils
			.<int[]>generate(
				f -> {
					try {
						final int[] p = new int[6];
						final String line = reader.readLine();
						final boolean hasNext = (line != null);
		                if (hasNext) {
		                	int i = 0;
		                	int x = 0;
		                	boolean neg = false;

		                	for (final char c : line.toCharArray()) {
		                		switch (c) {
		                			case ',':
		                				p[i++] = neg ? -x : x;
		                				x = 0;
		                				neg = false;
		                				break;

		                			case '-':
		                				neg = true;
		                				break;

		                			default:
		                				x *= 10;
		                				x += c - '0';
		                		}
		                	}
		                	p[i] = neg ? -x : x;
		                	f.accept(p);
		                }
		                return hasNext;
					}
					catch (final Exception e) {
						throw new RuntimeException(e);
					}
				},
				reader)
			.filter(p ->
				area(p[0], p[1], p[2], p[3], p[4], p[5]) ==
				(area(p[0], p[1], p[2], p[3], 0,    0   ) +
				area(p[0], p[1], 0,    0,    p[4], p[5]) +
				area(0,    0,    p[2], p[3], p[4], p[5]))
			)
			.count();
    }

	private int area(int ax, int ay, int bx, int by, int cx, int cy) {
	    return Math.abs(((ax - cx) * (by - ay)) - ((ax - bx) * (cy - ay)));
	}
}
