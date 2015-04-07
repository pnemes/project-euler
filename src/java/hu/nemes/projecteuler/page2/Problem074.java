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

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.stream.IntStream;

public final class Problem074 implements Callable<Long> {

	/**
	 * Digit factorial chains
	 * Problem 74
	 *
	 * The number 145 is well known for the property that the sum of the factorial of its digits is equal to 145:
	 *
	 * 1! + 4! + 5! = 1 + 24 + 120 = 145
	 *
	 * Perhaps less well known is 169, in that it produces the longest chain of numbers that link back to 169; it turns out that there are only three such loops that exist:
	 *
	 * 169 → 363601 → 1454 → 169
	 * 871 → 45361 → 871
	 * 872 → 45362 → 872
	 *
	 * It is not difficult to prove that EVERY starting number will eventually get stuck in a loop. For example,
	 *
	 * 69 → 363600 → 1454 → 169 → 363601 (→ 1454)
	 * 78 → 45360 → 871 → 45361 (→ 871)
	 * 540 → 145 (→ 145)
	 *
	 * Starting with 69 produces a chain of five non-repeating terms, but the longest non-repeating chain with a starting number below one million is sixty terms.
	 *
	 * How many chains, with a starting number below one million, contain exactly sixty non-repeating terms?
	 */
	@Override
	public Long call() {
		final int limit = 1_000_000;

		final long[] f = new long[10];
		f[0] = 1;
        for (int i = 1; i < f.length; ) {
            f[i] = f[i-1] * i++;
        }

		final int[] seqlengths = new int[limit + 1];
		seqlengths[169] = 3;
		seqlengths[363601] = 3;
		seqlengths[1454] = 3;
		seqlengths[871] = 2;
		seqlengths[45361] = 2;
		seqlengths[872] = 2;
		seqlengths[45362] = 2;

		return IntStream
				.rangeClosed(1, limit)
				.parallel()
				.map(n -> {

					final Collection<Integer> seq = new LinkedList<>();
					int n1;
					int n2 = n;
				    int count = 0;

				    do {
				    	seq.add(n1 = n2);
				        n2 = (int) Arithmetic.forEachDigitsOf(n1, 0, (a, b) -> a + f[(int) b]);
				        count++;

				        if ((n2 <= limit) && (seqlengths[n2] > 0)) {
				            count += seqlengths[n2];
				            break;
				        }
				    } while (n1 != n2);

				    int c = count;
				    for (final Integer a : seq) {
				        if (a <= limit) {
				        	seqlengths[a] = c;
				        }
				        c--;
				    }

				    return count;
				})
				.filter(n -> n == 60)
				.count();
	}
}
