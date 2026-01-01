/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.func.FuncOf;
import org.cactoos.scalar.Constant;

/**
 * Extract a substring from a Text.
 *
 * <p>There is no thread-safety guarantee.
 * @since 0.11
 */
public final class Sub extends TextEnvelope {

    /**
     * Ctor.
     * @param text The String
     * @param strt Start position in the text
     */
    public Sub(final CharSequence text, final int strt) {
        this(new TextOf(text), strt);
    }

    /**
     * Ctor.
     * @param text The String
     * @param strt Start position in the text
     * @param finish End position in the text
     */
    public Sub(final CharSequence text, final int strt, final int finish) {
        this(new TextOf(text), strt, finish);
    }

    /**
     * Ctor.
     * @param text The Text
     * @param strt Start position in the text
     */
    public Sub(final Text text, final int strt) {
        this(text, new Constant<>(strt));
    }

    /**
     * Ctor.
     * @param text The Text
     * @param strt Start position in the text
     */
    public Sub(final Text text, final Scalar<Integer> strt) {
        this(text, new FuncOf<>(strt));
    }

    /**
     * Ctor.
     * @param text The Text
     * @param strt Start position in the text
     */
    public Sub(final Text text, final Func<? super String, Integer> strt) {
        this(text, strt, String::length);
    }

    /**
     * Ctor.
     * @param text The Text
     * @param strt Start position in the text
     * @param finish End position in the text
     */
    public Sub(final Text text, final int strt, final int finish) {
        this(text, new Constant<>(strt), new Constant<>(finish));
    }

    /**
     * Ctor.
     * @param text The Text
     * @param strt Start position in the text
     * @param finish End position in the text
     */
    public Sub(final Text text, final int strt, final Scalar<Integer> finish) {
        this(text, new Constant<>(strt), finish);
    }

    /**
     * Ctor.
     * @param text The Text
     * @param strt Start position in the text
     * @param finish End position in the text
     */
    public Sub(final Text text, final int strt,
        final Func<? super String, Integer> finish) {
        this(text, new Constant<>(strt), finish);
    }

    /**
     * Ctor.
     * @param text The Text
     * @param strt Start position in the text
     * @param finish End position in the text
     */
    public Sub(final Text text, final Scalar<Integer> strt,
        final Scalar<Integer> finish) {
        this(text, new FuncOf<>(strt), new FuncOf<>(finish));
    }

    /**
     * Ctor.
     * @param text The Text
     * @param strt Start position in the text
     * @param finish End position in the text
     */
    public Sub(final Text text, final Scalar<Integer> strt,
        final Func<? super String, Integer> finish) {
        this(text, new FuncOf<>(strt), finish);
    }

    /**
     * Ctor.
     * @param text The Text
     * @param start Start position in the text
     * @param end End position in the text
     */
    public Sub(final Text text, final Func<? super String, Integer> start,
        final Func<? super String, Integer> end) {
        super(
            new Mapped(
                origin -> {
                    int begin = start.apply(origin);
                    if (begin < 0) {
                        begin = origin.length() + begin;
                    }
                    if (begin < 0) {
                        begin = 0;
                    }
                    int finish = end.apply(origin);
                    if (finish < 0) {
                        finish = origin.length() + finish;
                    }
                    if (finish > origin.length()) {
                        finish = origin.length();
                    }
                    if (finish < 0) {
                        finish = 0;
                    }
                    return origin.substring(begin, finish);
                },
                text
            )
        );
    }
}
