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
package org.cactoos.collection;

/**
 * Joined collection.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Mykola Yashchenko (vkont4@gmail.com)
 * @version $Id$
 * @param <X> Type of source item
 * @since 1.16
 */
public final class Joined<X> extends CollectionEnvelope<X> {

    /**
     * Ctor.
     * @param list Items to concatenate
     */
    @SafeVarargs
    public Joined(final Iterable<X>... list) {
        this(new CollectionOf<>(list));
    }

    /**
     * Ctor.
     * @param list Items to concatenate
     */
    public Joined(final Iterable<Iterable<X>> list) {
        super(() -> new CollectionOf<X>(
            new org.cactoos.iterable.Joined<>(list)
        ));
    }

}
