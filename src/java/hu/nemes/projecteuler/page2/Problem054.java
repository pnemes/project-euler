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
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

public final class Problem054 implements Callable<Long> {

	/**
	 * Poker hands
	 * Problem 54
	 *
	 * In the card game poker, a hand consists of five cards and are ranked, from lowest to highest, in the following way:
	 *
	 *     High Card: Highest value card.
	 *     One Pair: Two cards of the same value.
	 *     Two Pairs: Two different pairs.
	 *     Three of a Kind: Three cards of the same value.
	 *     Straight: All cards are consecutive values.
	 *     Flush: All cards of the same suit.
	 *     Full House: Three of a kind and a pair.
	 *     Four of a Kind: Four cards of the same value.
	 *     Straight Flush: All cards are consecutive values of same suit.
	 *     Royal Flush: Ten, Jack, Queen, King, Ace, in same suit.
	 *
	 * The cards are valued in the order:
	 * 2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen, King, Ace.
	 *
	 * If two players have the same ranked hands then the rank made up of the highest value wins; for example, a pair of eights beats a pair of fives (see example 1 below). But if two ranks tie, for example, both players have a pair of queens, then highest cards in each hand are compared (see example 4 below); if the highest cards tie then the next highest cards are compared, and so on.
	 *
	 *
	 * The file, poker.txt, contains one-thousand random hands dealt to two players. Each line of the file contains ten cards (separated by a single space): the first five are Player 1's cards and the last five are Player 2's cards. You can assume that all hands are valid (no invalid characters or repeated cards), each player's hand is in no specific order, and in each hand there is a clear winner.
	 *
	 * How many hands does Player 1 win?
	 */
	private final static int CARD_VALUE_LENGTH     = 4;
	private final static int CARD_FREQUENCY_LENGTH = 2;

	private final static int CARD_VALUE_MASK       = (1 << CARD_VALUE_LENGTH) - 1;  // 0b001111
	private final static int CARD_FREQUENCY_MASK   = ((1 << CARD_FREQUENCY_LENGTH) - 1) << CARD_VALUE_LENGTH;  // 0b110000

	@SuppressWarnings("unused")
	private final static String suits  = "HDSC";
	private final static String values = "23456789TJQKA";

	private final static int MAX_CARD_VALUE = values.length() + 1;

	private final static boolean isSameSuit(String s, int startPos) {
		final char suit = s.charAt(++startPos);
		for (int i = 3; i < 15; i += 3) {
			if (suit != s.charAt(startPos + i)) {
				return false;
			}
		}
		return true;
	}

	private final static boolean isConsecutive(int[] frequency) {
		if ((frequency[0] >> CARD_VALUE_LENGTH) != 1) {
			return false;
		}
		int card = frequency[0] & CARD_VALUE_MASK;
		for (int i = 1; i < frequency.length; i++) {
			if (--card != (frequency[i] & CARD_VALUE_MASK)) {
				return false;
			}
		}
		return true;
	}

	private final static int[] frequency(String s, int startPos) {
		final int[] freq = new int[MAX_CARD_VALUE];

		Arrays.parallelSetAll(freq, IntUnaryOperator.identity());

		// calculate histogram
		for (int i = 0; i <= MAX_CARD_VALUE; i += 3) {
			final int value = values.indexOf(s.charAt(startPos + i)) + 1;
			freq[value] += (1 << CARD_VALUE_LENGTH);
		}

		Arrays.sort(freq);

		// move last i into the first i in reverse order
		// (i = number of values with nonzero frequency)
		int i = 0;
		int b = MAX_CARD_VALUE;
		while ((b-- > 0) && ((freq[b] & CARD_FREQUENCY_MASK) != 0)) {
			freq[i] += freq[b];
			freq[b]  = freq[i] - freq[b];
			freq[i] -= freq[b];
			i++;
		}

		return Arrays.copyOf(freq, i);
	}


	private final static Collection<Predicate<Hand>> RULES = Arrays.asList(
			hand -> (hand.consecutive && hand.sameSuit),					// Royal/Straight Flush
			hand -> (hand.firstFreq == 4),								// Four of a Kind
			hand -> ((hand.firstFreq == 3) && (hand.secondFreq == 2)),	// Full House
			hand -> (hand.sameSuit),										// Flush
			hand -> (hand.consecutive),									// Straight
			hand -> (hand.firstFreq == 3),								// Three of a Kind
			hand -> (hand.firstFreq == 2) && (hand.secondFreq == 2),		// Two Pairs
			hand -> (hand.firstFreq == 2)									// One Pair
		);

	private final static int RULES_SIZE = RULES.size();

	private final static ToIntFunction<Hand> rankHand = (hand) -> {
		int m = RULES_SIZE;

		final Iterator<Predicate<Hand>> iterator = RULES.iterator();
		while (iterator.hasNext() && !iterator.next().test(hand)) {
			m--;
		}
		return m;
	};

	private static class Hand {

		final String h;

		final int[] frequency;
		final boolean sameSuit;
		final boolean consecutive;
		final int firstFreq;
		final int secondFreq;

		final int rank;
		final int score;

		public Hand(String s, int startPos) {
			h = s.substring(startPos, startPos + 14);
			frequency = frequency(s, startPos);
			sameSuit = isSameSuit(s, startPos);
			consecutive = isConsecutive(frequency);
			firstFreq = frequency[0] >> CARD_VALUE_LENGTH;
			secondFreq = frequency[1] >> CARD_VALUE_LENGTH;

			rank = rankHand.applyAsInt(this);
			score = (frequency[0] & CARD_VALUE_MASK) + (MAX_CARD_VALUE * rank);
		}

		@Override
		public String toString() {
			return String.format("%s, [%d -> %d] frequency: %s, sameSuit: %5b, consecutive: %5b, %d %d",
					h,
					rank,
					score,
					Arrays.toString(Arrays.stream(frequency).mapToObj(Integer::toBinaryString).toArray()),
					sameSuit,
					consecutive,
					firstFreq,
					secondFreq);
		}
	}

	@Override
	public Long call() throws IOException, URISyntaxException {
		return Files
			.lines(new File(Problem054.class.getResource("poker.txt").toURI()).toPath())
			.parallel()
			.filter(s -> new Hand(s, 0).score > new Hand(s, 3 * 5).score)
			.count();
	}
}
