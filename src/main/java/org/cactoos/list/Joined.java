/*
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
package org.cactoos.list;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Joined list.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <X> Type of source item
 * @since 0.20
 */
public final class Joined<X> extends ListEnvelope<X> {

    /**
     * Ctor.
     * @param src Source lists
     */
    @SafeVarargs
    @SuppressWarnings("PMD.UseVarargs")
    public Joined(final List<X>... src) {
        this(new ListOf<>(src));
    }

    /**
     * Ctor.
     * @param item First item
     * @param items List
     * @since 0.32
     */
    public Joined(final X item, final List<X> items) {
        super(() -> new Joined<X>(new ListOf<X>(item), items));
    }

    /**
     * Ctor.
     * @param src Source lists
     */
    public Joined(final Iterable<List<X>> src) {
        super(() -> Collections.unmodifiableList(
            new ListOf<>(src).stream()
                .flatMap(List::stream)
                .collect(Collectors.toList())
            )
        );
    }

}
