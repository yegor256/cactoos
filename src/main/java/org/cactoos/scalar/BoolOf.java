/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.text.TextOf;

/**
 * Text as {@link Boolean}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * <p>This class implements {@link Scalar}, which throws a checked
 * {@link Exception}. This may not be convenient in many cases. To make
 * it more convenient and get rid of the checked exception you can
 * use the {@link Unchecked} decorator. Or you may use
 * {@link IoChecked} to wrap it in an IOException.</p>
 *
 * @since 0.2
 */
public final class BoolOf implements Scalar<Boolean> {

    /**
     * Source text.
     */
    private final Text origin;

    /**
     * Ctor.
     *
     * @param txt True or false string
     */
    public BoolOf(final String txt) {
        this(new TextOf(txt));
    }

    /**
     * Ctor.
     *
     * @param text True or false text
     */
    public BoolOf(final Text text) {
        this.origin = text;
    }

    @Override
    public Boolean value() throws Exception {
        return Boolean.valueOf(this.origin.asString());
    }
}
