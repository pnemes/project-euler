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

import java.util.function.LongBinaryOperator;
import java.util.function.LongPredicate;

public final class Arithmetic {

	// Greatest common divisor
    public static long gcd(long p, long q) {

        long u = p;
        long v = q;

        if ((u == 0) || (v == 0)) {
        	assert ((u != Long.MIN_VALUE) && (v != Long.MIN_VALUE));
            return Math.abs(u) + Math.abs(v);
        }

        if (u > 0) {
            u = -u;
        }

        if (v > 0) {
            v = -v;
        }

        int k = 0;
        while (((u & 1) == 0) && ((v & 1) == 0) && (k < 63)) {
            u /= 2;
            v /= 2;
            k++;
        }

        assert (k != 63);

        long t = ((u & 1) == 1) ? v : -(u / 2);

        do {
            while ((t & 1) == 0) {
                t /= 2;
            }

            if (t > 0) {
                u = -t;
            } else {
                v = t;
            }

            t = (v - u) / 2;
        } while (t != 0);

        return -u * (1L << k);

    }

    // lowest common multiple
    public static long lcm(long a, long b) {

        if ((a == 0) || (b == 0)) {
            return 0;
        }

        final long lcm = Math.abs((a / gcd(a, b)) * b);
        assert (lcm != Long.MIN_VALUE);
        return lcm;
    }

    // k^e
	public static long pow(long k, long e) {
		assert (e >= 0);

		long result = 1l;
		long k2p = k;
		while (e != 0) {
			if ((e & 0x1) != 0) {
				result *= k2p;
			}
			k2p *= k2p;
			e = e >> 1;
		}
		return result;
	}

	// product of all digits
	public static long productOfDigits(long n) {
		return forEachDigitsOf(n, 1, (a, b) -> a * b);
	}

	public static long productOfDigits(long n, int base) {
		return forEachDigitsOf(n, base, 1, (a, b) -> a * b);
	}

	// sum of all digits
	public static long sumOfDigits(long n) {
		return forEachDigitsOf(n, 0, (a, b) -> a + b);
	}

	// sum of all digits
	public static long sumOfDigits(long n, int base) {
		return forEachDigitsOf(n, base, 0, (a, b) -> a + b);
	}

	// f(x) = n(n+1)/2
	public static boolean isTriangle(long n) {
		return ((((Math.sqrt(1 + (8 * n)) - 1) / 2) % 1) == 0);
	}

	// f(x) = n(3n−1)/2
	public static boolean isPentagonal(long n) {
		return ((((Math.sqrt(1 + (24 * n)) + 1) / 6) % 1) == 0);
	}

	// f(x) = n(2n−1)
	public static boolean isHexagonal(long n) {
		return ((((Math.sqrt(1 + (8 * n)) + 1) / 4) % 1) == 0);
	}

	public static boolean isPalindrome(long n) {
		return isPalindrome(n, 10);
	}

	public static boolean isPalindrome(long n, int base) {
		return ((n % base) != 0) && (n == reverseDigits(n, base));
	}

	public static boolean isPandigital(long n) {
		return isPandigital(n, 10);
	}

	public static boolean isPandigital(long n, int base) {
		assert (base <= Long.SIZE);

		long x = 0;
		do {
			if (((n % base) == 0) || (x == (x |= (1 << (n % base))))) {
				return false;
			}
		} while ((n /= base) > 0);
		return isPowerOfTwo((x >>> 1) + 1);
	}

	public static boolean isPandigitalCombined(long[] n) {
		return isPandigitalCombined(n, 10);
	}

	public static boolean isPandigitalCombined(long[] n, int base) {
		assert (base <= Long.SIZE);

		long x = 0;
		for (int i = 0; i < n.length; i++) {
			do {
				if (((n[i] % base) == 0) || (x == (x |= (1 << (n[i] % base))))) {
					return false;
				}
			} while ((n[i] /= base) > 0);
		}
		return isPowerOfTwo((x >>> 1) + 1);
	}

	public static long reverseDigits(long n) {
		return reverseDigits(n, 10);
	}

	public static long reverseDigits(long n, int base) {
		return forEachDigitsOf(n, base, 0, (a, b) -> (a * base) + b);
	}

	public static long concatDigits(long n1, long n2) {
		return concatDigits(n1, n2, 10);
	}

	public static long concatDigits(long n1, long n2, int base) {
		while ((n2 % base) == 0) {
			n1 *= base;
			n2 /= base;
		}
		n2 = reverseDigits(n2, base);
		return forEachDigitsOf(n2, base, n1, (a, b) -> (a * base) + b);
	}

	// digits mapper
	public static long forEachDigitsOf(long n, long identity, LongBinaryOperator f) {
		return forEachDigitsOf(n, 10, identity, f);
	}

	public static long forEachDigitsOf(long n, int base, long identity, LongBinaryOperator f) {
		long x = identity;
		do {
			x = f.applyAsLong(x, n % base);
		} while ((n /= base) > 0);
		return x;
	}

	public static boolean isAllDigits(long n, LongPredicate f) {
		return isAllDigits(n, 10, f);
	}

	public static boolean isAllDigits(long n, int base, LongPredicate f) {
		return !isAnyDigits(n, 10, f.negate());
	}

	public static boolean isAnyDigits(long n, LongPredicate f) {
		return isAnyDigits(n, 10, f);
	}

	public static boolean isAnyDigits(long n, int base, LongPredicate f) {
		do {
			if (f.test(n % base)) {
				return true;
			}
		} while ((n /= base) > 0);
		return false;
	}

	// product of all proper divisors
	public static long productOfProperDivisors(long n) {
		return forEachProperDivisorOf(n, 1, (a, b) -> a * b);
	}

	// sum of all proper divisors
	public static long sumOfProperDivisors(long n) {
		return forEachProperDivisorOf(n, 0, (a, b) -> a + b);
	}

	// proper divisor mapper
	public static long forEachProperDivisorOf(long n, long identity, LongBinaryOperator f) {
		long x = identity;
		for (int i = 1; i <= (n/2); i++) {
			if ((n % i) == 0) {
				x = f.applyAsLong(x, i);
			}
		}
		return x;
	}

	// divisor mapper
	public static long forEachDivisorOf(long n, long identity, LongBinaryOperator f) {
		return f.applyAsLong(
				forEachProperDivisorOf(n, identity, f),
				n);
	}

	// number of divisors
	public static long numberOfDivisors(long n) {
		if (n == 1) {
			return 1;
		}
		long divnum = 1;
		final long[] primes = Primes.getPrimesUntil((long) Math.sqrt(n));

	    int exponent;
	    long remain = n;
	    for (final long prime : primes) {

	        exponent = 1;
	        while ((remain % prime) == 0) {
	            exponent++;
	            remain = remain / prime;
	        }
	        divnum *= exponent;

	        if (remain == 1) {
	            return divnum;
	        }
	    }
	    return 2 * divnum;
	}

	// number of divisors of n^k
	public static long numberOfDivisorsPow(long n, int k) {
		if (n == 1) {
			return 1;
		}
		long divnum = 1;
		final long[] primes = Primes.getPrimesUntil((long) Math.sqrt(n));

	    int exponent;
	    long remain = n;
	    for (final long prime : primes) {

	        exponent = 0;
	        while ((remain % prime) == 0) {
	            exponent++;
	            remain /= prime;
	        }
	        divnum *= (k * exponent) + 1;

	        if (remain == 1) {
	            return divnum;
	        }
	    }
	    return 2 * divnum;
	}

	public static boolean isPowerOfTwo(long n) {
		return (n > 0) && ((n & (n - 1)) == 0);
	}

	public static boolean isIncreasing(long n) {
		return isIncreasing(n, 10);
	}

	public static boolean isIncreasing(long n, int base) {
		long x = base;
		do {
			if (x < (x = n % base)) {
				return false;
			}
		} while ((n /= base) > 0);
		return true;
	}

	public static boolean isDecreasing(long n) {
		return isDecreasing(n, 10);
	}

	public static boolean isDecreasing(long n, int base) {
		long x = 0;
		do {
			if (x > (x = n % base)) {
				return false;
			}
		} while ((n /= base) > 0);
		return true;
	}

	public static boolean isBouncy(long n) {
		return isBouncy(n, 10);
	}

	public static boolean isBouncy(long n, int base) {
		boolean inc = false;
		boolean dec = false;
		long x1;
		long x2 = n % base;
		do {
			x1 = n % base;
			inc |= (x2 > x1);
			dec |= (x2 < x1);
			x2 = x1;
		} while (((n /= base) > 0) && (!(dec && inc)));
		return dec && inc;
	}
}
