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

import hu.nemes.projecteuler.common.Primes;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.stream.IntStream;

public final class Problem110 implements Callable<BigInteger> {

	/**
	 * Diophantine reciprocals II
	 * Problem 110
	 *
	 * It can be verified that when n = 1260 there are 113 distinct solutions and this is the least value of n for which the total number of distinct solutions exceeds one hundred.
	 *
	 * What is the least value of n for which the number of distinct solutions exceeds four million?
	 *
	 * NOTE: This problem is a much more difficult version of Problem 108 and as it is well beyond the limitations of a brute force approach it requires a clever implementation.
	 */
	@Override
	public BigInteger call() throws IOException, URISyntaxException {

		// 1/x + 1/y = 1/n
		// yn + xn = xy
		// ...
		// n2 = sr
		//
		// 8_000_000 < D(n2) = (2a+1)(2b+1)...(2z+1)
		// a,b...z being divisor exponents for n
		// primes to have at max: 3^k > 8_000_000 (that is the worst case of a,b...z = 1)
		final int maxSize = (int) (Math.log(8_000_000) / Math.log(3)); // 14
		final int[] exponents = new int[maxSize];
		final BigInteger[] primes = Primes
				.makePrimeStream()
				.limit(maxSize)
				.mapToObj(BigInteger::valueOf)
				.toArray(BigInteger[]::new);

		// worst case from that (when every exponent is 1):
		BigInteger solution = Arrays
				.stream(primes)
				.reduce(BigInteger.ONE, (a, b) -> a.multiply(b));

		final double limit = (2 * 4000000) - 1;
		int counter = 0;

		do {
		    exponents[counter]++;
		    // reset the lower exponents to the increased minimum
		    Arrays.fill(exponents, 0, counter, exponents[counter]);

		    // reset the exponent of 2
			exponents[0] = 0;

			// calculate the number of divisors from the exponents
		    final double divisors = Arrays
		    		.stream(exponents)
		    		.map(e -> (2 * e) + 1)
		    		.reduce(1, (a, b) -> a * b);

		    // calculate back the exponent for 2, from the number of divisors
		    exponents[0] = ((int) (((limit / divisors) - 1) / 2)) + 1;

		    // 2^a * 3^b > 2^b * 3^a, if a<b so switch them
		    // to get a lower result after recalculation
		    if (exponents[0] < exponents[1]) {
		        counter++; // add a new exponent to the calculation
		    }
		    else {
		        // calculate and assign the lower one as the solution
		    	solution = IntStream
		        		.range(0, maxSize)
		        		.mapToObj(i -> primes[i].pow(exponents[i]))
						.reduce(BigInteger.ONE, (a, b) -> a.multiply(b))
						// apply minimum
						.min(solution);

		        counter = 1;
		    }
		} while (counter < maxSize);

		return solution;
    }
}
