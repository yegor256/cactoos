/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import java.util.Collection;
import java.util.Formatter;
import java.util.Locale;
import org.cactoos.Text;
import org.cactoos.list.ListOf;

/**
 * Text in Sprintf format.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.1
 */
public final class FormattedText extends TextEnvelope {

    /**
     * New formatted string with default locale.
     *
     * @param ptn Pattern
     * @param arguments Arguments
     */
    public FormattedText(final String ptn, final Object... arguments) {
        this(ptn, new ListOf<>(arguments));
    }

    /**
     * New formatted string with default locale.
     *
     * @param ptn Pattern
     * @param arguments Arguments
     */
    public FormattedText(final Text ptn, final Object... arguments) {
        this(ptn, new ListOf<>(arguments));
    }

    /**
     * New formatted string with specified locale.
     *
     * @param ptn Pattern
     * @param locale Format locale
     * @param arguments Arguments
     */
    public FormattedText(
        final String ptn,
        final Locale locale,
        final Object... arguments
    ) {
        this(ptn, locale, new ListOf<>(arguments));
    }

    /**
     * New formatted string with specified locale.
     *
     * @param ptn Pattern
     * @param locale Format locale
     * @param arguments Arguments
     */
    public FormattedText(
        final Text ptn,
        final Locale locale,
        final Object... arguments
    ) {
        this(ptn, locale, new ListOf<>(arguments));
    }

    /**
     * New formatted string with default locale.
     *
     * @param ptn Pattern
     * @param arguments Arguments
     */
    public FormattedText(final CharSequence ptn, final Collection<?> arguments) {
        this(ptn, Locale.getDefault(Locale.Category.FORMAT), arguments);
    }

    /**
     * New formatted string with default locale.
     *
     * @param ptn Pattern
     * @param arguments Arguments
     */
    public FormattedText(final Text ptn, final Collection<Object> arguments) {
        this(ptn, Locale.getDefault(Locale.Category.FORMAT), arguments);
    }

    /**
     * New formatted string with specified locale.
     *
     * @param ptn Pattern
     * @param locale Format locale
     * @param arguments Arguments
     */
    public FormattedText(
        final CharSequence ptn,
        final Locale locale,
        final Collection<?> arguments
    ) {
        this(new TextOf(ptn), locale, arguments);
    }

    /**
     * New formatted string with specified locale.
     *
     * @param ptn Pattern
     * @param locale Format locale
     * @param args Arguments
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public FormattedText(
        final Text ptn,
        final Locale locale,
        final Collection<?> args
    ) {
        super(
            new Mapped(
                pattern -> {
                    final StringBuilder out = new StringBuilder(pattern.length());
                    try (Formatter fmt = new Formatter(out, locale)) {
                        fmt.format(pattern, args.toArray());
                    }
                    return out.toString();
                },
                ptn
            )
        );
    }
}
