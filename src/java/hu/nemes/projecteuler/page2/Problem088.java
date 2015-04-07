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

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.stream.IntStream;

public final class Problem088 implements Callable<Long> {

	/**
	 * Product-sum numbers
	 * Problem 88
	 *
	 * A natural number, N, that can be written as the sum and product of a given set of at least two natural numbers, {a1, a2, ... , ak} is called a product-sum number: N = a1 + a2 + ... + ak = a1 × a2 × ... × ak.
	 *
	 * For example, 6 = 1 + 2 + 3 = 1 × 2 × 3.
	 *
	 * For a given set of size, k, we shall call the smallest N with this property a minimal product-sum number. The minimal product-sum numbers for sets of size, k = 2, 3, 4, 5, and 6 are as follows.
	 *
	 * k=2: 4 = 2 × 2 = 2 + 2
	 * k=3: 6 = 1 × 2 × 3 = 1 + 2 + 3
	 * k=4: 8 = 1 × 1 × 2 × 4 = 1 + 1 + 2 + 4
	 * k=5: 8 = 1 × 1 × 2 × 2 × 2 = 1 + 1 + 2 + 2 + 2
	 * k=6: 12 = 1 × 1 × 1 × 1 × 2 × 6 = 1 + 1 + 1 + 1 + 2 + 6
	 *
	 * Hence for 2≤k≤6, the sum of all the minimal product-sum numbers is 4+6+8+12 = 30; note that 8 is only counted once in the sum.
	 *
	 * In fact, as the complete set of minimal product-sum numbers for 2≤k≤12 is {4, 6, 8, 12, 15, 16}, the sum is 61.
	 *
	 * What is the sum of all the minimal product-sum numbers for 2≤k≤12000?
	 */
	@Override
	public Long call() {
		final int limit = 12_000;
		final int max = 2 * limit; // 1*1*1*1*1*[...]*2*limit = 1+1+1+1+[...]+1+2+limit

		final int numFactors = (int) (Math.log(max) / Math.log(2)); // worst case 2^x = 2*limit
		final int[] factors = new int[numFactors];

		// for storing minimum solutions for given k
		final int[] k = IntStream.rangeClosed(0, limit).map(i -> i*2).toArray();
		k[1] = 0;

		factors[0] = 1;
		int curMaxFactor = 1;
		int j = 0;

		while (true) {
		    if (j == 0) {
		        // at first factor
		        if (curMaxFactor == numFactors) {
		            // Used all the factors we can
		            break;
		        }

		        if (factors[0]++ >= factors[1]) {
		            // add another factor
		            factors[curMaxFactor++] = Integer.MAX_VALUE;
		            factors[0] = 2;
		        }

		        j++;
		        factors[1] = factors[0]-1;
		    }
		    else if (j == (curMaxFactor-1)) {
		        // At the max factor
		        factors[j]++;
		        int sum = 0;
		        int prod = 1;
		        for (int i = 0; i < curMaxFactor; i++) {
		            prod *= factors[i];
		            sum += factors[i];
		        }

		        if (prod > max) {
		            // Exceeded the limit so go back
		            j--;
		        }
		        else {
		            // Check the result
		            final int pk = (prod - sum) + curMaxFactor;

		            if ((pk <= limit) && (prod < k[pk])) {
		                k[pk] = prod;
		            }
		        }
		    }
		    else if (factors[j] < factors[j-- + 1]) {
		        // increment and reset the next factor and go up
		        factors[++j+1] = (++factors[j++]) - 1;
		    }
		}

		return (long) Arrays.stream(k).distinct().sum();
	}
}
