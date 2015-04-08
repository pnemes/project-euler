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

import hu.nemes.projecteuler.common.StreamUtils;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public final class Problem059 implements Callable<Long> {

	/**
	 * XOR decryption
	 * Problem 59
	 *
	 * Each character on a computer is assigned a unique code and the preferred standard is ASCII (American Standard Code for Information Interchange). For example, uppercase A = 65, asterisk (*) = 42, and lowercase k = 107.
	 *
	 * A modern encryption method is to take a text file, convert the bytes to ASCII, then XOR each byte with a given value, taken from a secret key. The advantage with the XOR function is that using the same encryption key on the cipher text, restores the plain text; for example, 65 XOR 42 = 107, then 107 XOR 42 = 65.
	 *
	 * For unbreakable encryption, the key is the same length as the plain text message, and the key is made up of random bytes. The user would keep the encrypted message and the encryption key in different locations, and without both "halves", it is impossible to decrypt the message.
	 *
	 * Unfortunately, this method is impractical for most users, so the modified method is to use a password as a key. If the password is shorter than the message, which is likely, the key is repeated cyclically throughout the message. The balance for this method is using a sufficiently long password key for security, but short enough to be memorable.
	 *
	 * Your task has been made easy, as the encryption key consists of three lower case characters. Using cipher1.txt (right click and 'Save Link/Target As...'), a file containing the encrypted ASCII codes, and the knowledge that the plain text must contain common English words, decrypt the message and find the sum of the ASCII values in the original text.
	 */
	@Override
	public Long call() {

		final int[] data = StreamUtils
				.generateTokenizerStream(Problem058.class.getResourceAsStream("cipher1.txt"))
				.mapToInt(Integer::parseInt)
    			.toArray();

		final int[] valid = IntStream.concat(
								IntStream.concat(
									" '().,;!".chars(),
									IntStream.rangeClosed('0', '9')),
								IntStream.concat(
									IntStream.rangeClosed('a', 'z'),
									IntStream.rangeClosed('A', 'Z'))).toArray();
		Arrays.sort(valid);

		final long diff = ('z' - 'a') + 1;
		final int[] key = LongStream
				.range(0, diff * diff * diff)
				.parallel()
				.mapToObj(n -> {
					final int[] a = new int[3];
					for (int i = 0; i < 3; i++) {
						a[i] = ((int) (n % diff)) + 'a';
						n /= diff;
					}
					return a;
				})
				.filter(k -> {
					int i = 0;
					for (final int d : data) {
						if (Arrays.binarySearch(valid, d ^ k[i++ % k.length]) < 0) {
							return false;
						}
					}
					return true;
				})
				.findAny()
				.get();

		Arrays.parallelSetAll(data, i -> data[i] ^ key[i % key.length]);
		Arrays.parallelPrefix(data, (a, b) -> a + b);

		return (long) data[data.length - 1];
	}
}
