/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
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
import org.cactoos.map.MapEntry;
import org.cactoos.map.MapOf;
import org.cactoos.text.TextOf;

/**
 * Map as {@link Properties}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.12
 */
@SuppressWarnings("PMD.OnlyOneConstructorShouldDoInitialization")
public final class PropertiesOf implements Scalar<Properties> {

    /**
     * The underlying properties.
     */
    private final IoChecked<Properties> scalar;

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
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public PropertiesOf(final Input input) {
        this(
            () -> {
                final Properties props = new Properties();
                try (InputStream stream = input.stream()) {
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
    public PropertiesOf(final Iterable<? extends Map.Entry<?, ?>> entries) {
        this(
            new MapOf<>(
                input -> new MapEntry<>(
                    input.getKey().toString(), input.getValue().toString()
                ),
                entries
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
    private PropertiesOf(final Scalar<? extends Properties> sclr) {
        this.scalar = new IoChecked<>(sclr);
    }

    @Override
    public Properties value() throws IOException {
        return this.scalar.value();
    }
}
