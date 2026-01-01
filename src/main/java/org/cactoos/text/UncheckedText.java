/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.cactoos.Func;
import org.cactoos.Text;
import org.cactoos.func.UncheckedFunc;
import org.cactoos.scalar.And;
import org.cactoos.scalar.Or;
import org.cactoos.scalar.Unchecked;

/**
 * Text that doesn't throw checked {@link Exception}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.3
 */
public final class UncheckedText implements Text {

    /**
     * Original text.
     */
    private final Text text;

    /**
     * Fallback.
     */
    private final Func<Exception, String> fallback;

    /**
     * Ctor.
     * @param txt Encapsulated text
     * @since 0.9
     */
    public UncheckedText(final CharSequence txt) {
        this(new TextOf(txt));
    }

    /**
     * Ctor.
     * @param txt Encapsulated text
     */
    @SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
    public UncheckedText(final Text txt) {
        this(
            txt,
            error -> {
                throw new RuntimeException(error);
            }
        );
    }

    /**
     * Ctor.
     * @param txt Encapsulated text
     * @param fbk Fallback func if {@link Exception} happens
     * @since 0.5
     */
    public UncheckedText(final Text txt, final Func<Exception, String> fbk) {
        this.text = txt;
        this.fallback = fbk;
    }

    @Override
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public String asString() {
        String txt;
        try {
            txt = this.text.asString();
            // @checkstyle IllegalCatchCheck (1 line)
        } catch (final Exception ex) {
            txt = new UncheckedFunc<>(this.fallback).apply(ex);
        }
        return txt;
    }

    @Override
    public String toString() {
        return this.asString();
    }

    @Override
    @SuppressFBWarnings("EQ_UNUSUAL")
    public boolean equals(final Object obj) {
        return new Unchecked<>(
            new Or(
                () -> this == obj,
                new And(
                    () -> obj instanceof Text,
                    () -> this.asString().equals(
                        Text.class.cast(obj).asString()
                    )
                )
            )
        ).value();
    }

    @Override
    public int hashCode() {
        return this.asString().hashCode();
    }

}
