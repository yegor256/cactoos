/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
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
package org.cactoos.set;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.cactoos.iterable.IterableOf;
import org.cactoos.scalar.And;

/**
 * Iterable as {@link Set}.
 *
 * <p>This class should be used very carefully. You must understand that
 * it will fetch the entire content of the encapsulated {@link Set} on each
 * method call. It doesn't cache the data anyhow. </p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Set type
 * @since 0.49.2
 */
public final class SetOf<T> extends SetEnvelope<T> {

    /**
     * Ctor.
     *
     * @param array An array of some elements
     */
    @SafeVarargs
    public SetOf(final T... array) {
        this(new IterableOf<>(array));
    }

    /**
     * Ctor.
     * @param src An {@link Iterable}
     */
    public SetOf(final Iterable<T> src) {
        super(() -> {
            final Set<T> tmp = new HashSet<>();
            new And(tmp::add, src)
                .value();
            return Collections.unmodifiableSet(tmp);
        });
    }

}
