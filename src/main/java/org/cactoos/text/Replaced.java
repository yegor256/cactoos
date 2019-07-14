/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
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
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.func.IoCheckedFunc;
import org.cactoos.scalar.IoChecked;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

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
        final String find,
        final String replace) {
        this(text, () -> Pattern.compile(find), matcher -> replace);
    }

    /**
     * Ctor.
     * <p>
     * The given {@link Pattern regex} is used to produce a
     * {@link Pattern#matcher(java.lang.CharSequence) matcher} that will be
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
        final Func<Matcher, String> func) {
        super((Scalar<String>) () -> {
            final StringBuffer buffer = new StringBuffer();
            final Matcher matcher = new IoChecked<>(regex)
                .value()
                .matcher(text.asString());
            final IoCheckedFunc<Matcher, String> safe =
                new IoCheckedFunc<>(func);
            while (matcher.find()) {
                matcher.appendReplacement(
                    buffer,
                    safe.apply(matcher)
                );
            }
            matcher.appendTail(buffer);
            return buffer.toString();
        });
    }
}
