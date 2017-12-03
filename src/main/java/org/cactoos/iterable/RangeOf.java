/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cactoos.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.cactoos.Func;
import org.cactoos.func.UncheckedFunc;

/**
 * Iterable implementation to model range functionality.
 * @author Sven Diedrichsen (sven.diedrichsen@gmail.com)
 * @version $Id$
 * @param <T> Range value type
 * @since 1.0
 */
public class RangeOf<T extends Comparable<T>> extends IterableEnvelope<T> {

    /**
     * Ctor.
     * @param min Start of the range.
     * @param max End of the range.
     * @param incrementor The {@link Func} to process for the next value.
     */
    @SuppressWarnings({
        "PMD.CallSuperInConstructor",
        "PMD.ConstructorOnlyInitializesOrCallOtherConstructors"
        }
    )
    public RangeOf(final T min, final T max, final Func<T, T> incrementor) {
        super(() -> new IterableOf<>(
            new Iterator<T>() {
                private final UncheckedFunc<T, T> inc =
                    new UncheckedFunc<>(incrementor);
                private T value = min;

                @Override
                public boolean hasNext() {
                    return this.value.compareTo(max) < 1;
                }

                @Override
                public T next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    final T result = this.value;
                    this.value = this.inc.apply(this.value);
                    return result;
                }
            }
        ));
    }

    /**
     * Implements an integer range.
     */
    public static class Integer extends RangeOf<java.lang.Integer> {
        /**
         * Ctor.
         * @param min Minimum integer value.
         * @param max Maximum integer value.
         * @param incrementor Increments the integer value.
         */
        public Integer(
            final java.lang.Integer min,
            final java.lang.Integer max,
            final Func<java.lang.Integer, java.lang.Integer> incrementor) {
            super(min, max, incrementor);
        }
        /**
         * Ctor. Uses a default increment of 1.
         * @param min Minimum integer value.
         * @param max Maximum integer value.
         */
        public Integer(
            final java.lang.Integer min,
            final java.lang.Integer max) {
            super(min, max, value -> ++value);
        }
    }

    /**
     * Implements an long range.
     */
    public static class Long extends RangeOf<java.lang.Long> {
        /**
         * Ctor.
         * @param min Minimum long value.
         * @param max Maximum long value.
         * @param incrementor Increments the long value.
         */
        public Long(
            final java.lang.Long min,
            final java.lang.Long max,
            final Func<java.lang.Long, java.lang.Long> incrementor) {
            super(min, max, incrementor);
        }
        /**
         * Ctor. Uses a default increment of 1.
         * @param min Minimum long value.
         * @param max Maximum long value.
         */
        public Long(
            final java.lang.Long min,
            final java.lang.Long max) {
            super(min, max, value -> ++value);
        }
    }

    /**
     * Implements an character range.
     */
    public static class Character extends RangeOf<java.lang.Character> {
        /**
         * Ctor.
         * @param min Minimum character value.
         * @param max Maximum character value.
         * @param incrementor Increments the character value.
         */
        public Character(
            final java.lang.Character min,
            final java.lang.Character max,
            final Func<java.lang.Character, java.lang.Character> incrementor) {
            super(min, max, incrementor);
        }
        /**
         * Ctor. Uses a default increment of 1.
         * @param min Minimum character value.
         * @param max Maximum character value.
         */
        public Character(
            final java.lang.Character min,
            final java.lang.Character max) {
            super(min, max, value -> ++value);
        }
    }

}
