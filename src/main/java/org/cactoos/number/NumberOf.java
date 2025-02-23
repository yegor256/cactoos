/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.number;

import java.math.BigDecimal;
import org.cactoos.Text;

/**
 * Text as {@link Number}.
 *
 * <pre>
 * long value = new NumberOf("186789235425346").longValue();
 * int value = new NumberOf("1867892354").intValue();
 * double value = new NumberOf("185.65156465123").doubleValue();
 * </pre>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 1.0.0
 */
public final class NumberOf extends NumberEnvelope {

    /**
     * Serialization marker.
     */
    private static final long serialVersionUID = 1572355842425667751L;

    /**
     * Ctor.
     * @param txt Number-string
     */
    public NumberOf(final String txt) {
        this(new BigDecimal(txt));
    }

    /**
     * Ctor.
     * @param text Number-text
     */
    public NumberOf(final Text text) {
        this(new NumberOfScalars(() -> new BigDecimal(text.asString())));
    }

    /**
     * Ctor.
     * @param nbr Number
     */
    private NumberOf(final Number nbr) {
        super(nbr);
    }
}
