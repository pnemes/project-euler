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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.LongStream;

public final class Problem062 implements Callable<String> {

	/**
	 * Cubic permutations
	 * Problem 62
	 *
	 * The cube, 41063625 (345^3), can be permuted to produce two other cubes: 56623104 (384^3) and 66430125 (405^3). In fact, 41063625 is the smallest cube which has exactly three permutations of its digits which are also cube.
	 *
	 * Find the smallest cube for which exactly five permutations of its digits are cube.
	 */
	@Override
	public String call() {
		final Map<String, Collection<String>> map = new HashMap<>();

		return LongStream
				.iterate(1, n -> n + 1)
				.mapToObj(n -> BigInteger.valueOf(n).pow(3))
				.map(n -> {
					final String value = n.toString();
					final int[] digits = value.chars().toArray();
					Arrays.sort(digits);

					final String key = new String(digits, 0, digits.length);
					final Collection<String> values = map.computeIfAbsent(key, k -> new ArrayList<>());
					values.add(value);

					return values;
				})
				.filter(values -> values.size() == 5)
				.map(values -> values.iterator().next())
				.findFirst()
				.get();
	}
}
