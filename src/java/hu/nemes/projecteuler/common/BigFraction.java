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
package hu.nemes.projecteuler.common;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class BigFraction {

	private final BigInteger numerator;
	private final BigInteger denominator;

	public BigFraction(BigInteger value) {
		this(value, BigInteger.ONE);
	}

	public BigFraction(BigInteger numerator, BigInteger denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
	}

	public BigInteger getNumerator() {
		return numerator;
	}

	public BigInteger getDenominator() {
		return denominator;
	}

	public BigDecimal toBigDecimal(int scale, RoundingMode roundingMode) {
		return new BigDecimal(numerator).divide(new BigDecimal(denominator), scale, roundingMode);
	}

	public BigFraction simplificate() {
		final BigInteger gcd = denominator.gcd(numerator);
		if (gcd.compareTo(BigInteger.ONE) > 0) {
			return new BigFraction(numerator.divide(gcd), denominator.divide(gcd));
		}
		else {
			return this;
		}
	}

	public BigFraction inverse() {
		return new BigFraction(denominator, numerator);
	}

	@Override
	public String toString() {
		return numerator + " / " + denominator;
	}

	public static Stream<BigFraction> continuedFractionStream(Supplier<BigInteger> b) {
		return continuedFractionStream(() -> BigInteger.ONE, b);
	}

	public static Stream<BigFraction> continuedFractionStream(BigInteger b0, Supplier<BigInteger> b) {
		return continuedFractionStream(() -> BigInteger.ONE, b0, b);
	}

	public static Stream<BigFraction> continuedFractionStream(Supplier<BigInteger> a, Supplier<BigInteger> b) {
		return continuedFractionStream(a, b.get(), b);
	}

	public static Stream<BigFraction> continuedFractionStream(Supplier<BigInteger> a, BigInteger b0, Supplier<BigInteger> b) {

		final BigInteger a1 = a.get();
		final BigInteger b1 = b.get();

		final BigFraction n0 = new BigFraction(b0);
		final BigFraction n1 = new BigFraction(b1.multiply(b0).add(a1), b1);

		return StreamUtils.generate(new Supplier<BigFraction>() {

			private BigFraction n_2 = n0;
			private BigFraction n_1 = n1;

			@Override
			public BigFraction get() {
				final BigInteger an = a.get();
				final BigInteger bn = b.get();

				final BigFraction r = n_2;
				n_2 = n_1;
				n_1 = new BigFraction(
						bn.multiply(n_2.numerator).add(an.multiply(r.numerator)),
						bn.multiply(n_2.denominator).add(an.multiply(r.denominator)));

				return r;
			}
		});
	}
}
