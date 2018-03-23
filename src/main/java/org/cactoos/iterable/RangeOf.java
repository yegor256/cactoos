/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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
public final class
    RangeOf<T extends Comparable<T>> extends IterableEnvelope<T> {

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

}
