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
package hu.nemes.projecteuler.page1;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.concurrent.Callable;

public final class Problem019 implements Callable<Long> {

	/**
	 * Counting Sundays
	 * Problem 19
	 *
	 * You are given the following information, but you may prefer to do some research for yourself.
	 *
	 * 1 Jan 1900 was a Monday.
	 * Thirty days has September,
	 * April, June and November.
     * All the rest have thirty-one,
	 * Saving February alone,
	 * Which has twenty-eight, rain or shine.
	 * And on leap years, twenty-nine.
	 * A leap year occurs on any year evenly divisible by 4, but not on a century unless it is divisible by 400.
	 *
	 * How many Sundays fell on the first of the month during the twentieth century (1 Jan 1901 to 31 Dec 2000)?
	 */
	@Override
	public Long call() {
		final LocalDate maxDate = LocalDate.of(2000, 12, 31);

		long i = 0;
		LocalDate date = LocalDate.of(1901, 1, 1);
		while (!date.isAfter(maxDate)) {
			if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
				i++;
			}
			date = date.plusMonths(1);
		}

		return i;
	}
}
