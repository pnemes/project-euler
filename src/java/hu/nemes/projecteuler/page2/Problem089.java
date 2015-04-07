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

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.concurrent.Callable;

public final class Problem089 implements Callable<Long> {

	/**
	 * Roman numerals
	 * Problem 89
	 *
	 * For a number written in Roman numerals to be considered valid there are basic rules which must be followed. Even though the rules allow some numbers to be expressed in more than one way there is always a "best" way of writing a particular number.
	 *
	 * For example, it would appear that there are at least six ways of writing the number sixteen:
	 *
	 * IIIIIIIIIIIIIIII
	 * VIIIIIIIIIII
	 * VVIIIIII
	 * XIIIIII
	 * VVVI
	 * XVI
	 *
	 * However, according to the rules only XIIIIII and XVI are valid, and the last example is considered to be the most efficient, as it uses the least number of numerals.
	 *
	 * The 11K text file, roman.txt (right click and 'Save Link/Target As...'), contains one thousand numbers written in valid, but not necessarily minimal, Roman numerals; see About... Roman Numerals for the definitive rules for this problem.
	 *
	 * Find the number of characters saved by writing each of these in their minimal form.
	 *
	 * Note: You can assume that all the Roman numerals in the file contain no more than four consecutive identical units.
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws NumberFormatException
	 */

	/*
			I = 1
			V = 5
			X = 10
			L = 50
			C = 100
			D = 500
			M = 1000
	 */
	@Override
	public Long call() throws NumberFormatException, IOException, URISyntaxException {
		/* 				best	diff	mod
		 * I			I		0		-
		 * II			II		0		-
		 * III			III		0		-
		 * IIII		IV		2		-
		 * IIIII		V		4		-
		 * IIIIII		VI		4		-
		 * IIIIIII	VII	4		-
		 * IIIIIIII	VIII	4		-
		 * IIIIIIIII	IX		7		-
		 * VIIII		IX		3		+
		 */
		return Files
				.lines(new File(Problem089.class.getResource("roman.txt").toURI()).toPath())
				.map(String::toCharArray)
				.mapToLong(chars -> {
					long s = 0;
					int i = 0;
					int n = -1;
					boolean mod = false;
					char lc;
					char c = chars[0];
					do {
						n++;
						lc = c;
						if ((i == chars.length) || ((c = chars[i]) != lc)) {
							switch (lc) {
								case 'I':
								case 'X':
								case 'C':
									switch (n) {
										case 9:
											s += 3;
										case 8:
										case 7:
										case 6:
										case 5:
											s += 2;
										case 4:
											s += mod ? 3 : 2;
									}
									mod = false;
									break;

								case 'V':
									mod = (c == 'I');
									break;

								case 'L':
									mod = (c == 'X');
									break;

								case 'D':
									mod = (c == 'C');
									break;

								case 'M':
							}

							n = 0;
						}
					} while (i++ < chars.length);
					return s;
				})
				.sum();
	}
}
