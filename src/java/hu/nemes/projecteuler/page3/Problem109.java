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

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.stream.IntStream;

public final class Problem109 implements Callable<Integer> {

	/**
	 * Darts
	 * Problem 109
	 *
	 * In the game of darts a player throws three darts at a target board which is split into twenty equal sized sections numbered one to twenty.
	 *
	 * The score of a dart is determined by the number of the region that the dart lands in. A dart landing outside the red/green outer ring scores zero. The black and cream regions inside this ring represent single scores. However, the red/green outer ring and middle ring score double and treble scores respectively.
	 *
	 * At the centre of the board are two concentric circles called the bull region, or bulls-eye. The outer bull is worth 25 points and the inner bull is a double, worth 50 points.
	 *
	 * There are many variations of rules but in the most popular game the players will begin with a score 301 or 501 and the first player to reduce their running total to zero is a winner. However, it is normal to play a "doubles out" system, which means that the player must land a double (including the double bulls-eye at the centre of the board) on their final dart to win; any other dart that would reduce their running total to one or lower means the score for that set of three darts is "bust".
	 *
	 * When a player is able to finish on their current score it is called a "checkout" and the highest checkout is 170: T20 T20 D25 (two treble 20s and double bull).
	 *
	 * There are exactly eleven distinct ways to checkout on a score of 6:
	 */
	@Override
	public Integer call() {

		final int limit = 100;
		int result = 0;

		final int[] allScores = IntStream.concat(
					IntStream.range(1, 21).flatMap(i -> IntStream.of(i, 2*i, 3*i)),
					IntStream.of(25, 50))
				.toArray();

		final int[] doubles = IntStream.concat(
					IntStream.range(1, 21).map(i -> 2*i),
					IntStream.of(50))
				.toArray();

		// count the single hit exit
		result += Arrays
				.stream(doubles)
				.filter(h -> h < limit)
				.count();

		// count the 2 hit exit
		result += Arrays
				.stream(allScores)
				.map(h1 -> (int) Arrays
						.stream(doubles)
						.filter(h2 -> (h1 + h2) < limit)
						.count())
				.sum();

		// count the 3 hit exit (filter out where 1st hit and 2nd hit are the same by explicit indexing)
		result += IntStream
				.range(0, allScores.length)
				.map(i1 -> IntStream
						.range(i1, allScores.length)
						.map(i2 -> (int) Arrays
							.stream(doubles)
							.filter(h3 -> (allScores[i1] + allScores[i2] + h3) < limit)
							.count())
						.sum())
				.sum();

		return result;
    }
}
