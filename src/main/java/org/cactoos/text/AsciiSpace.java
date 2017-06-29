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
import java.util.HashMap;
import java.util.Map;
import org.cactoos.Text;

/**
 * Transliterate an UTF-8 value to ASCII.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Ix (ixmanuel@yahoo.com)
 * @version $Id$
 * @since 0.9
 */
public final class AsciiSpace implements Text {
    /**
     * Source text.
     */
    private final Text origin;

    /**
     * Ctor.
     *
     * @param string Source.
     */
    public AsciiSpace(final String string) {
        this(new StringAsText(string));
    }

    /**
     * Ctor.
     *
     * @param text Origin.
     */
    public AsciiSpace(final Text text) {
        this.origin = new Ascii(
            text,
            input -> {
                final Map<String, String[]> map = new HashMap<>(0);
                map.put(
                    " ",
                    new String[]{
                        "\\xC2\\xA0", "\\xE2\\x80\\x80", "\\xE2\\x80\\x81",
                        "\\xE2\\x80\\x82", "\\xE2\\x80\\x83", "\\xE2\\x80\\x84",
                        "\\xE2\\x80\\x85", "\\xE2\\x80\\x86", "\\xE2\\x80\\x87",
                        "\\xE2\\x80\\x88", "\\xE2\\x80\\x89", "\\xE2\\x80\\x8A",
                        "\\xE2\\x80\\xAF", "\\xE2\\x81\\x9F", "\\xE3\\x80\\x80",
                    }
                );
                return map;
            }
        );
    }

    @Override
    public String asString() throws IOException {
        return this.origin.asString();
    }

    @Override
    public int compareTo(final Text text) {
        return new UncheckedText(this).compareTo(text);
    }
}
