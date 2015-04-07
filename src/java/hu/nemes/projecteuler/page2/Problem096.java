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

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;

public final class Problem096 implements Callable<Long> {

	/**
	 * Su Doku
	 * Problem 96
	 *
	 * Su Doku (Japanese meaning number place) is the name given to a popular puzzle concept. Its origin is unclear, but credit must be attributed to Leonhard Euler who invented a similar, and much more difficult, puzzle idea called Latin Squares. The objective of Su Doku puzzles, however, is to replace the blanks (or zeros) in a 9 by 9 grid in such that each row, column, and 3 by 3 box contains each of the digits 1 to 9. Below is an example of a typical starting puzzle grid and its solution grid.
	 *
	 * A well constructed Su Doku puzzle has a unique solution and can be solved by logic, although it may be necessary to employ "guess and test" methods in order to eliminate options (there is much contested opinion over this). The complexity of the search determines the difficulty of the puzzle; the example above is considered easy because it can be solved by straight forward direct deduction.
	 *
	 * The 6K text file, sudoku.txt (right click and 'Save Link/Target As...'), contains fifty different Su Doku puzzles ranging in difficulty, but all with unique solutions (the first puzzle in the file is the example above).
	 *
	 * By solving all fifty puzzles find the sum of the 3-digit numbers found in the top left corner of each solution grid; for example, 483 is the 3-digit number found in the top left corner of the solution grid above.
	 *
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	@FunctionalInterface
	public interface IntTriConsumer {

	    void accept(int x, int y, int n);
	}

	@FunctionalInterface
	public interface IntTriPredicate {

	    boolean test(int x, int y, int n);
	}

	private final static class Sudoku {

		private final static int mask2 = 0b1111111110;
		private int remaining = -1;

		private int[][] s;
		private final int[] fx;
		private final int[] fy;
		private final int[][] fg;

		private Sudoku(int[][] s) {
			this.s = s;
			fx = new int[9];
			fy = new int[9];
			fg = new int[3][3];
		}

		private Sudoku(Sudoku other) {
			s = new int[9][];
			Arrays.setAll(s, i -> Arrays.copyOf(other.s[i], 9));
			fx = Arrays.copyOf(other.fx, 9);
			fy = Arrays.copyOf(other.fy, 9);
			fg = new int[3][];
			Arrays.setAll(fg, i -> Arrays.copyOf(other.fg[i], 3));

			remaining = other.remaining;
		}

		public int get(int x, int y) {
			return s[x][y];
		}

		private boolean isSolved(int n) {
			return n != 0;
		}

		private int getPossibilitiesFor(int x, int y) {
			return (fx[x] | fy[y] | fg[x/3][y/3]) ^ mask2;
		}

		private void forEach(IntTriConsumer f) {
			for (int x=0; x<9; x++) {
				for (int y=0; y<9; y++) {
					f.accept(x, y, s[x][y]);
				}
			}
		}

		private void forEachSolved(IntTriConsumer f) {
			forEach((x, y, n) -> {
				if (isSolved(n)) {
					f.accept(x, y, n);
				}
			});
		}

		private boolean forEachUntil(IntTriPredicate f) {
			for (int x=0; x<9; x++) {
				for (int y=0; y<9; y++) {
					if (!f.test(x, y, s[x][y])) {
						return false;
					}
				}
			}
			return true;
		}

		private boolean forEachUnsolvedUntil(IntTriPredicate f) {
			return forEachUntil((x, y, n) -> isSolved(n) || f.test(x, y, n));
		}

		public boolean trySolve() {
			if (remaining < 0) {
				remaining = 9 * 9;
				forEachSolved((x, y, n) -> fixNumber(x, y));
			}

			int solved = remaining;
			while (solved > 0) {
				solved = remaining;

				if (!forEachUnsolvedUntil((x, y, n) -> {
					if ((n = getPossibilitiesFor(x, y)) == 0) {
						return false; // there are no solutions to pick from for this cell
					}

					int c = 0;
					for (; (n % 2) == 0; c++) {
						n >>= 1;
					}

					if (n == 1) { // first and only solution
						s[x][y] = c;
						fixNumber(x, y);
					}
					return true;
				})) {
					return false; // propagate that there is a cell with no solution
				};

				solved -= remaining;
			};

			if (remaining > 0) {
				tryBruteForce();
			}

			return (remaining == 0);
		}

		private void tryBruteForce() {
			final int[] min = new int[4];
			min[3] = 9;

			forEachUnsolvedUntil((x, y, n) -> {
				if ((n = getPossibilitiesFor(x, y)) != 0) {
					final int bc = Integer.bitCount(n);
					if (bc < min[3]) {
						min[0] = x;
						min[1] = y;
						min[2] = n;
						min[3] = bc;
					}
					return (bc < 2);
				}
				return true;
			});

			final int x = min[0];
			final int y = min[1];
			int n = min[2];
			int c = 0;
			do {
				for (; (n % 2) == 0; c++) {
					n >>= 1;
				}
				final Sudoku other = clone();
				other.s[x][y] = c;
				other.fixNumber(x, y);

				if (other.trySolve()) {
					s = other.s;
					remaining = other.remaining;
					return;
				}
			} while (--n != 0);
		}

		@Override
		public Sudoku clone() {
			return new Sudoku(this);
		}

		private void fixNumber(int x, int y) {
			final int n = 1 << s[x][y];
			fx[x] |= n;
			fy[y] |= n;
			fg[x/3][y/3] |= n;
			remaining--;
		}

		public static Sudoku create(Iterator<String> lines) {
			final int[][] s = new int[9][];
			for (int i=0; (i < 9) && lines.hasNext(); i++) {

				int b = 9;
				long n = Long.parseLong(lines.next()) * 10;
				final int[] line = new int[b];
				while ((b-- > 0) && (n > 0)) {
					line[b] = (byte) ((n /= 10) % 10);
				}
				s[i] = line;
			}
			return (s[8] != null) ? new Sudoku(s) : null;
		}
	}

	@Override
	public Long call() throws NumberFormatException, IOException, URISyntaxException {
		return StreamUtils
			.generate(new Iterator<Sudoku>() {
				private final Iterator<String> lines =
						Files.lines(new File(Problem096.class.getResource("sudoku.txt").toURI()).toPath())
						.iterator();
				private boolean valueReady = false;
	            private Sudoku nextElement;

	            @Override
	            public boolean hasNext() {
	                if (!valueReady) {
	                	if (lines.hasNext()) {
		        			lines.next();
		                	nextElement = Sudoku.create(lines);
	                	}
	                	else {
	                		nextElement = null;
	                	}
	                	valueReady = true;
	                }
	                return (nextElement != null);
	            }

	            @Override
	            public Sudoku next() {
	                if (!valueReady && !hasNext()) {
	                    throw new NoSuchElementException();
	                }
	                else {
	                    valueReady = false;
	                    return nextElement;
	                }
	            }
			})
			.peek(Sudoku::trySolve)
			.mapToLong(sudoku ->
				(sudoku.get(0, 0) * 100) +
				(sudoku.get(0, 1) * 10) +
				sudoku.get(0, 2)
			)
			.sum();
    }
}
