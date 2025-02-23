/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import java.util.regex.Pattern;
import org.cactoos.Func;
import org.cactoos.Text;

/**
 * Validates encapsulated text using predicate
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 1.0
 */
public final class Strict extends TextEnvelope {

    /**
     * Ctor.
     * @param pattern The Pattern for validating encapsulated text
     * @param origin The Text
     */
    public Strict(final Pattern pattern, final Text origin) {
        this(str -> pattern.matcher(str).matches(), origin);
    }

    /**
     * Ctor.
     * @param predicate The Func as a predicate
     * @param origin The Text
     */
    public Strict(final Func<? super String, Boolean> predicate, final Text origin) {
        super(
            new Mapped(
                str -> {
                    if (!predicate.apply(str)) {
                        throw new IllegalArgumentException(
                            new FormattedText(
                                "String '%s' does not match a given predicate",
                                str
                            ).asString()
                        );
                    }
                    return str;
                },
                origin
            )
        );
    }
}
