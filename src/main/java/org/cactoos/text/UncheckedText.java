/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
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
package org.cactoos.text;

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
    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("EQ_UNUSUAL")
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
