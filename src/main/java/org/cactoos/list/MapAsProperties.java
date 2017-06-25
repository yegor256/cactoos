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
package org.cactoos.list;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Properties;
import org.cactoos.Scalar;

/**
 * Map as {@link java.util.Properties}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.7
 */
public final class MapAsProperties implements Scalar<Properties> {

    /**
     * The map.
     */
    private final Map<?, ?> map;

    /**
     * Ctor.
     * @param entries The map with properties
     */
    public MapAsProperties(final Map.Entry<?, ?>... entries) {
        this(
            new IterableAsMap<>(
                new MappedIterable<Map.Entry<?, ?>, Map.Entry<String, String>>(
                    new ArrayAsIterable<>(entries),
                    input -> new AbstractMap.SimpleEntry<>(
                        input.getKey().toString(), input.getValue().toString()
                    )
                )
            )
        );
    }

    /**
     * Ctor.
     * @param src The map with properties
     */
    public MapAsProperties(final Map<?, ?> src) {
        this.map = src;
    }

    @Override
    public Properties asValue() {
        final Properties props = new Properties();
        for (final Map.Entry<?, ?> entry : this.map.entrySet()) {
            props.setProperty(
                entry.getKey().toString(),
                entry.getValue().toString()
            );
        }
        return props;
    }
}
