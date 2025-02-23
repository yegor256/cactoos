/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import java.io.IOException;
import org.cactoos.Func;
import org.cactoos.Text;
import org.cactoos.func.UncheckedFunc;

/**
 * Text that doesn't throw checked {@link Exception}, but only
 * throws {@link IOException}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.51
 */
public final class IoCheckedText implements Text {

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
    public IoCheckedText(final CharSequence txt) {
        this(new TextOf(txt));
    }

    /**
     * Ctor.
     * @param txt Encapsulated text
     */
    @SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
    public IoCheckedText(final Text txt) {
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
    public IoCheckedText(final Text txt, final Func<Exception, String> fbk) {
        this.text = txt;
        this.fallback = fbk;
    }

    @Override
    @SuppressWarnings({"PMD.AvoidCatchingGenericException", "PMD.AvoidRethrowingException"})
    public String asString() throws IOException {
        String txt;
        try {
            txt = this.text.asString();
        } catch (final IOException ex) {
            throw ex;
            // @checkstyle IllegalCatchCheck (1 line)
        } catch (final Exception ex) {
            txt = new UncheckedFunc<>(this.fallback).apply(ex);
        }
        return txt;
    }

    @Override
    public String toString() {
        return new UncheckedText(this).toString();
    }

    @Override
    public boolean equals(final Object obj) {
        return new UncheckedText(this).equals(obj);
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

}
