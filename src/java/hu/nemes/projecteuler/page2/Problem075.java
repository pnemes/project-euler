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

import hu.nemes.projecteuler.common.Arithmetic;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public final class Problem075 implements Callable<Long> {

	/**
	 * Singular integer right triangles
	 * Problem 75
	 *
	 * It turns out that 12 cm is the smallest length of wire that can be bent to form an integer sided right angle triangle in exactly one way, but there are many more examples.
	 *
	 * 12 cm: (3,4,5)
	 * 24 cm: (6,8,10)
	 * 30 cm: (5,12,13)
	 * 36 cm: (9,12,15)
	 * 40 cm: (8,15,17)
	 * 48 cm: (12,16,20)
	 *
	 * In contrast, some lengths of wire, like 20 cm, cannot be bent to form an integer sided right angle triangle, and other lengths allow more than one solution to be found; for example, using 120 cm it is possible to form exactly three different integer sided right angle triangles.
	 *
	 * 120 cm: (30,40,50), (20,48,52), (24,45,51)
	 *
	 * Given that L is the length of the wire, for how many values of L ≤ 1,500,000 can exactly one integer sided right angle triangle be formed?
	 */
	@Override
	public Long call() {
		final int limit = 1_500_000;

		final AtomicIntegerArray triangles = new AtomicIntegerArray(limit + 1);
		final int mlimit = (int) Math.sqrt(limit / 2);

		LongStream
			.range(2, mlimit)
			.parallel()
			.forEach(m -> {
			    for (long n = 1; n < m; n++) {
			        if ((((n + m) % 2) == 1) && (Arithmetic.gcd(n, m) == 1)) {
			        	final long a = (m * m) + (n * n);
			        	final long b = (m * m) - (n * n);
			        	final long c = 2 * m * n;
			        	int p = (int) (a + b + c);
			            while (p <= limit) {
			            	triangles.incrementAndGet(p);
			                p += a + b + c;
			            }
			        }
			    }
		});

		return IntStream
				.range(0, triangles.length())
				.parallel()
				.map(triangles::get)
				.filter(n -> n == 1)
				.count();
	}
}
