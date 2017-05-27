/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
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

import java.util.Collection;
import java.util.Formatter;
import java.util.Locale;

/**
 * Localized string format.
 *
 * @author Kirill (g4s8.public@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public final class LocalizedFormat implements StringFormat {

    /**
     * Format locale.
     */
    private final Locale locale;

    /**
     * Format pattern.
     */
    private final String pattern;

    /**
     * New string format with specified locale.
     *
     * @param locale A locale
     * @param pattern Format pattern
     */
    public LocalizedFormat(final Locale locale, final String pattern) {
        this.locale = locale;
        this.pattern = pattern;
    }

    @Override
    public String apply(final Collection<?> arguments) {
        final StringBuilder out = new StringBuilder(0);
        try (final Formatter fmt = new Formatter(out, this.locale)) {
            fmt.format(
                this.pattern,
                arguments.toArray(new Object[arguments.size()])
            );
        }
        return out.toString();
    }
}
