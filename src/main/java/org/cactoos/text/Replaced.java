/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.Text;

/**
 * Replace the Text.
 *
 * @since 0.2
 */
public final class Replaced extends TextEnvelope {

    /**
     * Ctor.
     * <p>
     * Will replace all instances of the substring matched by {@code find}
     * with {@code replace}.
     * @param text The text
     * @param find The regular expression
     * @param replace The replacement string
     */
    public Replaced(
        final Text text,
        final CharSequence find,
        final CharSequence replace
    ) {
        this(text, () -> Pattern.compile(find.toString()), matcher -> replace);
    }

    /**
     * Ctor.
     * <p>
     * The given {@link Pattern regex} is used to produce a
     * {@link Pattern#matcher(CharSequence) matcher} that will be
     * transformed by {@code func} into a replacement string to replace each
     * {@link Matcher#find() matching} substring.
     * <p>
     * Example usage:
     * <pre>{@code
     * final String result = new Replaced(
     *      new TextOf("one two THREE four FIVE six"),
     *      () -> Pattern.compile("[a-z]+"),
     *      matcher -> String.valueOf(matcher.group().length())
     * ).asString();  //will return the string "3 3 THREE 4 FIVE 3"
     * }</pre>
     * <p>
     * Note: a {@link PatternSyntaxException} will be thrown if the
     * regular expression's syntax is invalid.
     * @param text The text
     * @param regex The regular expression
     * @param func Transforms the resulting matcher object into a replacement
     *  string. Any exceptions will be wrapped in an {@link IOException}.
     */
    public Replaced(
        final Text text,
        final Scalar<Pattern> regex,
        final Func<? super Matcher, ? extends CharSequence> func
    ) {
        super(
            new Mapped(
                str -> {
                    final StringBuffer buffer = new StringBuffer();
                    final Matcher matcher = regex.value().matcher(str);
                    while (matcher.find()) {
                        matcher.appendReplacement(
                            buffer,
                            func.apply(matcher).toString()
                        );
                    }
                    matcher.appendTail(buffer);
                    return buffer.toString();
                },
                text
            )
        );
    }
}
