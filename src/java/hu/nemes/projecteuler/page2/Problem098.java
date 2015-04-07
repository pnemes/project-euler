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

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public final class Problem098 implements Callable<Long> {

	/**
	 * Anagramic squares
	 * Problem 98
	 *
	 * By replacing each of the letters in the word CARE with 1, 2, 9, and 6 respectively, we form a square number: 1296 = 362. What is remarkable is that, by using the same digital substitutions, the anagram, RACE, also forms a square number: 9216 = 962. We shall call CARE (and RACE) a square anagram word pair and specify further that leading zeroes are not permitted, neither may a different letter have the same digital value as another letter.
	 *
	 * Using words.txt (right click and 'Save Link/Target As...'), a 16K text file containing nearly two-thousand common English words, find all the square anagram word pairs (a palindromic word is NOT considered to be an anagram of itself).
	 *
	 * What is the largest square number formed by any member of such a pair?
	 *
	 * NOTE: All anagrams formed must be contained in the given text file.
	 */
	private <VI, KO, VO> Collector<Map.Entry<BitSet, VI>, ?, Map<KO, Set<VO>>> toMapSet(
			Function<BitSet, KO> classifier,
			Function<VI, VO> converter,
			BiPredicate<Set<VO>, VO> predicate) {

		return toMap(
			classifier,
			TreeSet::new,
			(set, vi) -> {
				final VO vo;
				if (predicate.test(set, vo = converter.apply(vi))) {
					set.add(vo);
				}
			},
            (set1, set2) -> {
            	set1.addAll(set2);
            	return set1;
            });
	}

	private <VI, KO, VO> Collector<Map.Entry<BitSet, VI>, ?, Map<KO, VO>> toMap(
			Function<BitSet, KO> classifier,
			Supplier<VO> supplier,
			BiConsumer<VO, VI> accumulator,
			BinaryOperator<VO> combiner) {

		return Collectors.groupingBy(
			e -> classifier.apply(e.getKey()),
			Collectors.mapping(
				Map.Entry::getValue,
				Collector.of(
					supplier,
					accumulator,
					combiner,
                    Characteristics.IDENTITY_FINISH)));
	}

	@Override
	public Long call() {
		return StreamUtils
			.generateTokenizerStream(Problem098.class.getResourceAsStream("words.txt"))
			.map(word -> {
				final BitSet key = new BitSet(('Z' - 'A') + 1);
				word.chars()
					.map(c -> 'Z' - c)
					.forEachOrdered(c -> {
						int i = c;
						while (key.get(i)) {
							i += ('Z' - 'A') + 1;
						}
						key.set(i);
					});
				return new AbstractMap.SimpleImmutableEntry<>(key, word);
			})
			.collect(toMapSet(
				Function.identity(),
				Function.identity(),
				(set, vo) -> !set.contains(new StringBuilder(vo).reverse().toString())
			))
			.entrySet()
			.stream()
			.filter(e -> e.getValue().size() > 1) // only anagrams from now on
			.collect(toMapSet(
				BitSet::cardinality,
				(words) -> Arrays.stream(
						words.stream()
							.findFirst()
							.get()
							.chars()
							.map(c -> 'Z' - c)
							.collect(
								() -> new int[('Z' - 'A') + 1],
								(a, c) -> a[c]++,
								(a1, a2) -> Arrays.setAll(a1, i -> a1[i] += a2[i]))
					)
					.filter(f -> f != 0)
					.sorted()
					.reduce((a, b) -> (10*a) + b)
					.getAsInt(),
				(set, vo) -> true
			))
			.entrySet()
			.stream()
			.map(e -> {
					final long n = (long) Math.pow(10, e.getKey());
					return LongStream
					.rangeClosed(
						(long) Math.ceil(Math.sqrt(n / 10)),
						(long) Math.sqrt(n - 1))
					.map(s -> s * s)
					.mapToObj(s -> {
						final BitSet key = new BitSet(20);
						long x = s;
						while (x > 0) {
							int i = (int) (x % 10);
							while (key.get(i)) {
								i += 10;
							}
							key.set(i);
							x /= 10;
						}
						return new AbstractMap.SimpleImmutableEntry<>(key, s);
					})
					.collect(toMapSet(
						Function.identity(),
						Function.identity(),
						(set, vo) -> true
					))
					.entrySet()
					.stream()
					.filter(e2 -> e2.getValue().size() > 1) // only pairs now on
					.map(e2 -> new AbstractMap.SimpleImmutableEntry<>(
						 	e2.getKey(),
						 	e2.getValue()
						 		.stream()
						 		.collect(Collectors.toCollection(TreeSet::new))
						 		.last()))
					.collect(
						Collectors.groupingBy(
							(e2) -> {
								final int[] freq = new int[10];
								long x = e2.getValue();
								while (x > 0) {
									final int i = (int) (x % 10);
									freq[i]++;
									x /= 10;
								}
								Arrays.sort(freq);
								return Arrays.stream(freq).filter(f -> f != 0).reduce((a, b) -> (10*a) + b).getAsInt();
							}
						)
					)
					.entrySet()
					.stream()
					.filter(e2 -> e.getValue().contains(e2.getKey()))
					.map(e2 -> new AbstractMap.SimpleImmutableEntry<>(
					 	e2.getKey(),
					 	e2.getValue()
					 		.stream()
					 		.map(e3 -> e3.getValue())
							.collect(Collectors.toCollection(TreeSet::new))
							.last()))
					.map(Map.Entry::getValue)
					.collect(Collectors.toCollection(TreeSet::new))
					.last()
					.longValue();
			}
			)
			.collect(Collectors.toCollection(TreeSet::new))
			.last()
			.longValue();
    }
}
