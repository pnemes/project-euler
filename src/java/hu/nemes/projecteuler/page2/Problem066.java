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

import java.math.BigInteger;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.IntStream;

public final class Problem066 implements Callable<Integer> {

	/**
	 * Diophantine equation
	 * Problem 66
	 *
	 * Consider quadratic Diophantine equations of the form:
	 *
	 * x2 – Dy2 = 1
	 *
	 * For example, when D=13, the minimal solution in x is 6492 – 13×1802 = 1.
	 *
	 * It can be assumed that there are no solutions in positive integers when D is square.
	 *
	 * By finding minimal solutions in x for D = {2, 3, 5, 6, 7}, we obtain the following:
	 *
	 * 32 – 2×22 = 1
	 * 22 – 3×12 = 1
	 * 92 – 5×42 = 1
	 * 52 – 6×22 = 1
	 * 82 – 7×32 = 1
	 *
	 * Hence, by considering minimal solutions in x for D ≤ 7, the largest x is obtained when D=5.
	 *
	 * Find the value of D ≤ 1000 in minimal solutions of x for which the largest value of x is obtained.
	 */
	@Override
	public Integer call() {
		return IntStream
				.rangeClosed(1, 1000)
				.filter(n -> (Math.sqrt(n) % 1) != 0)
				.mapToObj(n -> {
					final int a0 = (int) Math.sqrt(n);

					int m = 0;
					int d = 1;
					int a = a0;

					final BigInteger N = BigInteger.valueOf(n);
					BigInteger A;

					BigInteger numm1 = BigInteger.ONE;
					BigInteger num = BigInteger.valueOf(a);
					BigInteger denm1 = BigInteger.ZERO;
					BigInteger den = BigInteger.ONE;

					while (!num.pow(2).subtract(den.pow(2).multiply(N)).equals(BigInteger.ONE)) {
						m = (d * a) - m;
						d = (n - (m * m)) / d;
						a = (a0 + m) / d;

						A = BigInteger.valueOf(a);
				        num = numm1.add(A.multiply(numm1 = num));
				        den = denm1.add(A.multiply(denm1 = den));
					};

					return new AbstractMap.SimpleImmutableEntry<Integer, BigInteger>(n, num);
				})
				.max(Comparator.comparing(Map.Entry::getValue))
				.get()
				.getKey();
	}
}
