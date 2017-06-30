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
import java.util.Map;
import java.util.concurrent.Callable;
import org.cactoos.Func;
import org.cactoos.Text;
import org.cactoos.func.FuncAsCallable;
import org.cactoos.func.StickyFunc;

/**
 * Transliterate an UTF-8 value to ASCII.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Ix (ixmanuel@yahoo.com)
 * @version $Id$
 * @since 0.11
 */
public final class Ascii implements Text {
    /**
     * Source text.
     */
    private final Text text;

    /**
     * Unicode transliteration to ascii.
     */
    private Callable<Map<String, String[]>> mapping;

    /**
     * Ctor.
     *
     * @param string Source.
     * @param mapping The provided transliteration map.
     */
    public Ascii(final String string,
        final Func<Boolean, Map<String, String[]>> mapping) {
        this(new StringAsText(string), mapping);
    }

    /**
     * Ctor.
     *
     * @param itext Origin.
     * @param imapping The provided transliteration map.
     */
    public Ascii(final Text itext,
        final Func<Boolean, Map<String, String[]>> imapping) {
        this.text = itext;
        this.mapping = new FuncAsCallable<Map<String, String[]>>(
            new StickyFunc<Boolean, Map<String, String[]>>(imapping)
        );
    }

    @Override
    @SuppressWarnings(
        {
            "PMD.AvoidCatchingGenericException",
            "PMD.AvoidRethrowingException"
        }
    )
    // @todo Replace the loop by ReplacedAllText (approval pending).
    public String asString() throws IOException {
        final Map<String, String[]> map;
        String value;
        try {
            map = this.mapping.call();
            value = this.text.asString();
            // @checkstyle IllegalCatchCheck (1 line)
        } catch (final Exception ex) {
            throw new IOException(ex);
        }
        for (final Map.Entry<String, String[]> entry : map.entrySet()) {
            for (final String unicode : entry.getValue()) {
                value = value.replace(unicode, entry.getKey());
            }
        }
        return new TrimmedNonAscii(value).asString();
    }

    @Override
    public int compareTo(final Text itext) {
        return new UncheckedText(this).compareTo(itext);
    }
}
