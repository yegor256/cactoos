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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.cactoos.Text;

/**
 * Transliterate an UTF-8 value to ASCII.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Ix (ixmanuel@yahoo.com)
 * @version $Id$
 * @since 0.11
 */
public final class AsciiNumber implements Text {
    /**
     * Source text.
     */
    private final Text origin;

    /**
     * Ctor.
     *
     * @param string Source.
     */
    public AsciiNumber(final String string) {
        this(new StringAsText(string));
    }

    /**
     * Ctor.
     *
     * @param text Origin.
     */
    public AsciiNumber(final Text text) {
        this.origin = new Ascii(
            text,
            input -> {
                final Map<String, String[]> map = new HashMap<>(0);
                map.putAll(
                    Arrays.stream(
                        new Object[][]{
                            {"0", new String[]{"°", "₀", "۰"}},
                            {"1", new String[]{"¹", "₁", "۱"}},
                            {"2", new String[]{"²", "₂", "۲"}},
                            {"3", new String[]{"³", "₃", "۳"}},
                            {"4", new String[]{"⁴", "₄", "۴", "٤"}},
                            {"5", new String[]{"⁵", "₅", "۵", "٥"}},
                            {"6", new String[]{"⁶", "₆", "۶", "٦"}},
                            {"7", new String[]{"⁷", "₇", "۷"}},
                            {"8", new String[]{"⁸", "₈", "۸"}},
                            {"9", new String[]{"⁹", "₉", "۹"}},
                        }
                    ).collect(
                        Collectors.toMap(
                            kv -> (String) kv[0],
                            kv -> (String[]) kv[1]
                        )
                    )
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
