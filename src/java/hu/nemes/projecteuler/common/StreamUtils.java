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

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongPredicate;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class StreamUtils {

	public static LongStream makeFibonacciStream() {
		return generate(makeFibonacciSupplier());
	}

	public static LongStream makeFibonacciStream(LongPredicate predicate) {
		return generate(makeFibonacciSupplier(), predicate);
	}

	private static LongSupplier makeFibonacciSupplier() {
		return new LongSupplier() {

        	private long f1 = 1;
        	private long f2 = 0;

			@Override
			public long getAsLong() {
				f2 += f1;
				f1 = f2 - f1;
				return f2;
			}
        };
	}

	public static LongStream makeTriangleStream() {
		return generate(makeTriangleSupplier());
	}

	public static LongStream makeTriangleStream(LongPredicate predicate) {
		return generate(makeTriangleSupplier(), predicate);

	}

	private static LongSupplier makeTriangleSupplier() {
		return new LongSupplier() {

        	private long f1 = 0;
        	private long f2 = 0;

			@Override
			public long getAsLong() {
				return f2+=++f1;
			}
        };
	}

	public static LongStream makeCollatzStream(long seed) {
        final PrimitiveIterator.OfLong iterator = new PrimitiveIterator.OfLong() {
            private long t = seed;

            @Override
            public boolean hasNext() {
                return t > 1;
            }

            @Override
            public long nextLong() {
                final long v = t;
                t = ((t % 2) == 0) ? t / 2 : (3 * t) + 1;
                return v;
            }
        };

        return generate(iterator);
	}

	public static LongStream makePandigitalStream() {
		return makePandigitalStream(10);
	}

	public static LongStream makePandigitalStream(int base) {
        return makeSubPermutationStream(IntStream.range(1, base).toArray(), base);
	}

	public static LongStream makePermutationStream(int... array) {
		return makePermutationStream(array, 10);
	}

	public static LongStream makePermutationStream(final int[] array, int base) {
        final PrimitiveIterator.OfLong iterator = new PrimitiveIterator.OfLong() {
            private final Iterator<int[]> iter = makePermutationIterator(array);

            @Override
            public boolean hasNext() {
                return iter.hasNext();
            }

            @Override
            public long nextLong() {
            	final int[] a = iter.next();
            	long n = 0;
            	for (final long d : a) {
            		n = (n * base) + d;
            	}
                return n;
            }
        };

        return generate(iterator);
	}

	public static LongStream makeSubPermutationStream(final int[] array, int base) {
        final PrimitiveIterator.OfLong iterator = new PrimitiveIterator.OfLong() {
        	private int i = 0;
            private Iterator<int[]> iter = makePermutationIterator(Arrays.copyOf(array, i++));

            @Override
            public boolean hasNext() {
                if (!iter.hasNext()) {
                	if (i < array.length) {
                		iter = makePermutationIterator(Arrays.copyOf(array, i++));
                	}
                	else {
                		return false;
                	}
                }
                return true;
            }

            @Override
            public long nextLong() {
            	final int[] a = iter.next();
            	long n = 0;
            	for (final long d : a) {
            		n = (n * base) + d;
            	}
                return n;
            }
        };

        return generate(iterator);
	}

	public static Iterator<int[]> makePermutationIterator(final int[] array) {
		return new Iterator<int[]>() {
			private int i = array.length;

			/*
			 * Next lexicographical permutation algorithm
			 * Condensed mathematical description:
			 *
			 * 1) Find largest index i such that array[i - 1] < array[i].
			 * 2) Find largest index j such that j >= i and array[j] > array[i − 1].
			 * 3) Swap array[j] and array[i − 1]
			 * 4) Reverse the suffix starting at array[i]
			 */
			@Override
			public boolean hasNext() {
				// part 1)
				if (i == (array.length - 1)) {
				    while ((i > 0) && (array[i - 1] >= array[i])) {
				        i--;
				    }
				}
				return (i != 0);
			}

			@Override
			public int[] next() {
				// if we are at the first just return
				if (i == array.length) {
					i--;
					return array;
				}
				// part 2)
				int j = array.length - 1;
			    while (array[j] <= array[i - 1]) {
			        j--;
			    }

				// part 3)
			    int temp = array[i - 1];
			    array[i - 1] = array[j];
			    array[j] = temp;

				// part 4)
			    j = array.length - 1;
			    while (i < j) {
			        temp = array[i];
			        array[i] = array[j];
			        array[j] = temp;
			        i++;
			        j--;
			    }

				// signal that we used up the array, so part 1) can begin again
			    i = array.length - 1;
			    return array;
			}
		};
	}

	public static LongStream generate(LongSupplier supplier) {
		return LongStream.generate(supplier);
	}

	public static LongStream generate(PrimitiveIterator.OfLong iterator) {
		return StreamSupport.longStream(
				Spliterators.spliteratorUnknownSize(
					iterator,
					Spliterator.IMMUTABLE | Spliterator.NONNULL | Spliterator.ORDERED),
				false);
	}

	public static LongStream generate(LongSupplier supplier, LongPredicate predicate) {
        final PrimitiveIterator.OfLong iterator = new PrimitiveIterator.OfLong() {
        	private boolean valueReady = false;
            private long nextElement;

            @Override
            public boolean hasNext() {
                if (!valueReady) {
                	nextElement = supplier.getAsLong();
                	valueReady = true;
                }
                return predicate.test(nextElement);
            }

            @Override
            public long nextLong() {
                if (!valueReady && !hasNext()) {
                    throw new NoSuchElementException();
                }
                else {
                    valueReady = false;
                    return nextElement;
                }
            }
        };

        return generate(iterator);
	}

	public static <T> Stream<T> generate(Supplier<T> supplier) {
		return generate(new Iterator<T>() {

			@Override
			public boolean hasNext() {
				return true;
			}

			@Override
			public T next() {
				return supplier.get();
			}
		});
	}


	public static <T> Stream<T> generate(Iterator<T> iterator) {
		return StreamSupport.stream(
				Spliterators.spliteratorUnknownSize(
					iterator,
					Spliterator.IMMUTABLE | Spliterator.NONNULL | Spliterator.ORDERED),
					false);
	}

	public static Stream<String> generateTokenizerStream(InputStream in) {
		final Reader reader = new BufferedReader(new InputStreamReader(in));
		final StreamTokenizer tokenizer = new StreamTokenizer(reader);
		tokenizer.ordinaryChars('0', '9');
		tokenizer.ordinaryChars('-', '.');
		tokenizer.wordChars('0', '9');
		tokenizer.wordChars('-', '.');

		return generate(
				f -> {
					try {
						boolean hasNext;
		                do {
		                	hasNext = (tokenizer.nextToken() != StreamTokenizer.TT_EOF);
		                } while (hasNext && (tokenizer.sval == null));
		                if (hasNext) {
		                	f.accept(tokenizer.sval);
		                }
		                return hasNext;
					}
					catch (final Exception e) {
						throw new RuntimeException(e);
					}
				},
				reader);
	}

	public static <T> Stream<T> generate(Function<Consumer<T>, Boolean> tryAdvance, final Closeable closable) {

		final Runnable close = () -> {
			if (closable != null) {
				try {
					closable.close();
				}
				catch (final IOException e2) { }
			}
		};

        final Iterator<T> iterator = new Iterator<T>() {
        	private boolean valueReady = false;
        	private boolean hasNext = true;
            private T value;

            @Override
            public boolean hasNext() {
            	try {
	                if (!valueReady && hasNext) {
	                	hasNext = tryAdvance.apply(t -> value = t);
	                	valueReady = hasNext;

	                	if (!hasNext && (closable != null)) {
	                		closable.close();
	                	}
	                }
	                return valueReady;
				}
				catch (final Exception e) {
					close.run();
					throw new RuntimeException(e);
				}
            }

            @Override
            public T next() {
                if (!valueReady && !hasNext()) {
                    throw new NoSuchElementException();
                }
                else {
                    valueReady = false;
                    return value;
                }
            }
        };

        return generate(iterator).onClose(close);
	}
}
