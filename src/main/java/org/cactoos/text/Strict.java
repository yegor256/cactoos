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

import java.util.regex.Pattern;
import org.cactoos.Func;
import org.cactoos.Scalar;
import org.cactoos.Text;

/**
 * Validates encapsulated text using predicate
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 1.0
 */
public final class Strict extends TextEnvelope {

    /**
     * Ctor.
     * @param pattern The Pattern for validating encapsulated text
     * @param origin The Text
     */
    public Strict(final Pattern pattern, final Text origin) {
        this(str -> pattern.matcher(str).matches(), origin);
    }

    /**
     * Ctor.
     * @param predicate The Func as a predicate
     * @param origin The Text
     */
    public Strict(final Func<String, Boolean> predicate, final Text origin) {
        super((Scalar<String>) () -> {
            final String str = origin.asString();
            if (!predicate.apply(str)) {
                throw new IllegalArgumentException(
                    new FormattedText(
                        "String '%s' does not match a given predicate",
                        str
                    ).asString()
                );
            }
            return str;
        });
    }
}
