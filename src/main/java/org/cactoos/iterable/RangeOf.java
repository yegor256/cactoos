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

import java.util.ArrayList;
import java.util.List;
import org.cactoos.Func;
import org.cactoos.func.UncheckedFunc;
import org.cactoos.scalar.StickyScalar;

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
        super(
            new StickyScalar<>(
                () -> {
                    final UncheckedFunc<T, T> func =
                        new UncheckedFunc<>(incrementor);
                    final List<T> values =
                        new ArrayList<>(0);
                    T value = min;
                    while (value.compareTo(max) < 1) {
                        values.add(value);
                        value = func.apply(value);
                    }
                    return values;
                }
            )
        );
    }

}
