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
package org.cactoos.scalar;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import org.cactoos.Input;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.io.InputOf;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.cactoos.map.MapEntry;
import org.cactoos.map.MapOf;
import org.cactoos.text.TextOf;

/**
 * Map as {@link Properties}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.12
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class PropertiesOf implements Scalar<Properties> {

    /**
     * The underlying properties.
     */
    private final IoCheckedScalar<Properties> scalar;

    /**
     * Ctor.
     * @param content String
     */
    public PropertiesOf(final String content) {
        this(new TextOf(content));
    }

    /**
     * Ctor.
     * @param text Text
     */
    public PropertiesOf(final Text text) {
        this(new InputOf(text));
    }

    /**
     * Ctor.
     * @param input Input
     */
    public PropertiesOf(final Input input) {
        this(
            () -> {
                final Properties props = new Properties();
                try (final InputStream stream = input.stream()) {
                    props.load(stream);
                }
                return props;
            }
        );
    }

    /**
     * Ctor.
     * @param entries The map with properties
     */
    public PropertiesOf(final Map.Entry<?, ?>... entries) {
        this(new IterableOf<>(entries));
    }

    /**
     * Ctor.
     * @param entries The map with properties
     * @since 0.23
     */
    public PropertiesOf(final Iterable<Map.Entry<?, ?>> entries) {
        this(
            new MapOf<>(
                new Mapped<Map.Entry<?, ?>, Map.Entry<String, String>>(
                    input -> new MapEntry<>(
                        input.getKey().toString(), input.getValue().toString()
                    ),
                    entries
                )
            )
        );
    }

    /**
     * Ctor.
     * @param map The map with properties
     */
    public PropertiesOf(final Map<?, ?> map) {
        this(
            () -> {
                final Properties props = new Properties();
                for (final Map.Entry<?, ?> entry : map.entrySet()) {
                    props.setProperty(
                        entry.getKey().toString(),
                        entry.getValue().toString()
                    );
                }
                return props;
            }
        );
    }

    /**
     * Ctor.
     * @param sclr The underlying properties
     */
    private PropertiesOf(final Scalar<Properties> sclr) {
        this.scalar = new IoCheckedScalar<>(sclr);
    }

    @Override
    public Properties value() throws IOException {
        return this.scalar.value();
    }
}
