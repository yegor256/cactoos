/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Scalar;
import org.cactoos.Text;

/**
 * Determines if text starts with a given prefix.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.44
 */
public final class StartsWith implements Scalar<Boolean> {

    /**
     * The text.
     */
    private final Text text;

    /**
     * The prefix.
     */
    private final Text prefix;

    /**
     * Ctor.
     *
     * @param text The text
     * @param prefix The prefix
     */
    public StartsWith(final CharSequence text, final CharSequence prefix) {
        this(new TextOf(text), new TextOf(prefix));
    }

    /**
     * Ctor.
     *
     * @param text The text
     * @param prefix The prefix
     */
    public StartsWith(final Text text, final CharSequence prefix) {
        this(text, new TextOf(prefix));
    }

    /**
     * Ctor.
     *
     * @param text The text
     * @param prefix The prefix
     */
    public StartsWith(final CharSequence text, final Text prefix) {
        this(new TextOf(text), prefix);
    }

    /**
     * Ctor.
     *
     * @param text The text
     * @param prefix The prefix
     */
    public StartsWith(final Text text, final Text prefix) {
        this.text = text;
        this.prefix = prefix;
    }

    @Override
    public Boolean value() throws Exception {
        return this.text.asString().startsWith(this.prefix.asString());
    }

}
