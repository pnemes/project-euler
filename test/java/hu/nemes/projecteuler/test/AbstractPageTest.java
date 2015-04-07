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
package hu.nemes.projecteuler.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

abstract class AbstractPageTest {

	private final static String[] solutions;

	static {
		try {
			solutions = Files
			.lines(Paths.get(AbstractPageTest.class.getResource("solutions.txt").toURI()))
			.toArray(String[]::new);
		}
		catch (IOException | URISyntaxException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	protected final void testProblem(int problemNumber) throws Exception {
		try {
			final int pageNumber = ((problemNumber - 1) / 50) + 1;
			final String clazzName = String.format(
					"hu.nemes.projecteuler.page%d.Problem%03d",
					pageNumber,
					problemNumber);

			@SuppressWarnings("unchecked")
			final Class<Callable<?>> clazz = (Class<Callable<?>>) Class.forName(clazzName);
			final Object actualAnswer = clazz.newInstance().call();
			assertEquals(solutions[problemNumber - 1], String.valueOf(actualAnswer));
		}
		catch (final ClassNotFoundException e) {
			fail(String.format(
					"No solution found for problem %d, skipping.",
					problemNumber));
		}
	}
}