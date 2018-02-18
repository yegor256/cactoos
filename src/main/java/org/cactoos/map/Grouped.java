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
package org.cactoos.map;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Iterable as {@link Map}.
 *
 * <p>This class groups objects from iterable by applying
 * functions for keys and values</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Nikita Salomatin (nsalomatin@hotmail.com)
 * @version $Id$
 * @param <K> Type of key
 * @param <V> Type of value
 * @param <T> Type of entry objects of functions
 * @since 0.30
 */
public final class Grouped<K, V, T> extends MapEnvelope<K, List<V>> {

    /**
     * Ctor.
     *
     * @param list Iterable which is used to retrieve data from
     * @param keys Function to get a key
     * @param values Function to get a value
     */
    public Grouped(
        final Iterable<T> list,
        final Function<T, K> keys,
        final Function<T, V> values
    ) {
        super(() -> {
            final Stream<T> stream = StreamSupport.stream(
                list.spliterator(), false
            );
            return stream.collect(
                Collectors.groupingBy(
                    keys,
                    Collectors.mapping(values, Collectors.toList())
                )
            );
        });
    }
}
