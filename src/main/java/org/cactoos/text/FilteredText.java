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

import java.io.IOException;
import org.cactoos.Func;
import org.cactoos.Text;
import org.cactoos.func.IoCheckedFunc;

/**
 * Text filtered by a {@link org.cactoos.Func} working as a filter.
 *
 * @author Sven Diedrichsen (sven.diedrichsen@gmail.com)
 * @version $Id$
 * @since 24.07.17
 */
public final class FilteredText implements Text {

    /**
     * Original text to filter.
     */
    private final Text origin;
    /**
     * The {@link Func} as {@link IoCheckedFunc} to use for filtering.
     */
    private final IoCheckedFunc<Character, Boolean> filter;

    /**
     * Ctor.
     * @param origin The original text to filter.
     * @param filter The filter to use.
     */
    public FilteredText(final Text origin,
        final Func<Character, Boolean> filter) {
        this.origin = origin;
        this.filter = new IoCheckedFunc<>(filter);
    }

    @Override
    public String asString() throws IOException {
        final StringBuilder filtered = new StringBuilder();
        for (final char character : this.origin.asString().toCharArray()) {
            if (this.filter.apply(character)) {
                filtered.append(character);
            }
        }
        return filtered.toString();
    }

    @Override
    public int compareTo(final Text text) {
        return new UncheckedText(this).compareTo(text);
    }

}
