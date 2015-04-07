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

import java.util.Arrays;
import java.util.Comparator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

public final class Combinatorics {

	public static final class Selection {

		private final int n;
		private final int k;

		private Selection(int n, int k) {
			this.n = n;
			this.k = k;
		}

		public static Selection of(int n, int k) {
			return new Selection(n, k);
		}
	}

	public static final class PermutationSpliterator implements Spliterator<int[]> {

		private final Selection s;
		private long cur;
		private long max;
		private final int[] array;

		private PermutationSpliterator(Selection s) {
			this(s, 0);

			long tMax = 1;
			int i = s.n;
			int j = 1;
			while (j <= s.k) {
				tMax *= i--;
				tMax /= j++;
			}
			max = tMax;
		}

		private PermutationSpliterator(Selection s, long cur) {
			this.s = s;
			this.cur = cur;
			array = new int[s.k];

			for (int i=1; i<s.k; i++) {
				array[i] += i;
			}
		}

		private PermutationSpliterator(Selection s, long cur, long max) {
			this(s, cur);
			this.max = max;
		}

		@Override
		public boolean tryAdvance(Consumer<? super int[]> action) {
			if (cur < max) {
				cur++;

				final int[] g = Arrays.copyOf(array, array.length);
				for (int i=1; i<s.k; i++) {
					g[i] -= i;
				}

				action.accept(array);
				if (cur < max) {
					int i = s.k-1;
					int j = 0;
					while ((++array[i]+j) >= s.n) {
						j++;
						i--;
					}

					for (j=1; (i+j)<s.k; j++) {
						array[i+j] = array[i]+j;
					}
				}
				return true;
			}
			return false;
		}

		@Override
		public PermutationSpliterator trySplit() {
			if (estimateSize() > 1) {
				final long split = (cur + max) / 2;
				final PermutationSpliterator p = new PermutationSpliterator(s, split, max);
				max = split;
				return p;
			}
			return null;
		}

		@Override
		public long estimateSize() {
			return (max - cur);
		}

		@Override
		public int characteristics() {
			return ORDERED | DISTINCT | SORTED | SIZED | NONNULL | IMMUTABLE | SUBSIZED;
		}

		@Override
		public Comparator<int[]> getComparator() {
			return (a1, a2) -> {
				int d = 0;
				for (int i=0; i<s.k; i++) {
					d *= s.n;
					d += (a2[i] - a1[i]);
				}
				return d;
			};
		}
	}

	/*
	x i
	8 1
	7 2
	6 3
	5 4*/
	public final static void main(String[] args) {
		final int n = 8;
		final int k = 4;

		StreamSupport.stream(
				new PermutationSpliterator(Selection.of(n, k)),
				false)
				.forEachOrdered(a -> {});

		for (int cur = 0; cur < 70; cur++) {
			long tCur = cur;
			final int x = (n - k)+1;
			int i = k;
			final int[] array = new int[k];
			while (i >= 1) {
				array[i-1] = (int) (tCur % x);
				tCur /= i--;
			}
		}
	}

	public static void makePermutationStream() {

	}
}
