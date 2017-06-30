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
 * Transliterate an UTF-8 value to ASCII. Error in windows. There is 1 string
 * literal in unicode "�?" that are not encoded in this OS, and thus, this
 * object has suppressed AvoidDuplicateLiterals in order to pass appveyor.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Ix (ixmanuel@yahoo.com)
 * @version $Id$
 * @since 0.11
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class AsciiNonLatin implements Text {

    /**
     * Non-latin alphabet.
     */
    private static final Map<String, String[]> MAPPING =
        Arrays.stream(
            new Object[][]{
                {"aa", new String[]{"ع", "आ", "آ"}},
                {"ae", new String[]{"ä", "æ", "ǽ"}},
                {"ai", new String[]{"ऐ"}},
                {"at", new String[]{"@"}},
                {"ch", new String[]{"ч", "ჩ", "ჭ", "چ"}},
                {"dj", new String[]{"ђ", "đ"}},
                {"dz", new String[]{"џ", "ძ"}},
                {"ei", new String[]{"ऍ"}},
                {"gh", new String[]{"غ", "ღ"}},
                {"ii", new String[]{"ई"}},
                {"ij", new String[]{"ĳ"}},
                {"kh", new String[]{"х", "خ", "ხ"}},
                {"lj", new String[]{"љ"}},
                {"nj", new String[]{"њ"}},
                {"oe", new String[]{"ö", "œ", "ؤ"}},
                {"oi", new String[]{"ऑ"}},
                {"oii", new String[]{"ऒ"}},
                {"ps", new String[]{"ψ"}},
                {"sh", new String[]{"ш", "შ", "ش"}},
                {"shch", new String[]{"щ"}},
                {"ss", new String[]{"ß"}},
                {"sx", new String[]{"ŝ"}},
                {"th", new String[]{"þ", "ϑ", "ث", "ذ", "ظ"}},
                {"ts", new String[]{"ц", "ც", "წ"}},
                {"ue", new String[]{"ü"}},
                {"uu", new String[]{"ऊ"}},
                {"ya", new String[]{"я"}},
                {"yu", new String[]{"ю"}},
                {"zh", new String[]{"ж", "ჟ", "ژ"}},
                {"(c)", new String[]{"©"}},
                {"AE", new String[]{"Ä", "Æ", "Ǽ"}},
                {"CH", new String[]{"Ч"}},
                {"DJ", new String[]{"Ђ"}},
                {"DZ", new String[]{"Џ"}},
                {"GX", new String[]{"Ĝ"}},
                {"HX", new String[]{"Ĥ"}},
                {"IJ", new String[]{"Ĳ"}},
                {"JX", new String[]{"Ĵ"}},
                {"KH", new String[]{"Х"}},
                {"LJ", new String[]{"Љ"}},
                {"NJ", new String[]{"Њ"}},
                {"OE", new String[]{"Ö", "Œ"}},
                {"PS", new String[]{"Ψ"}},
                {"SH", new String[]{"Ш"}},
                {"SHCH", new String[]{"Щ"}},
                {"SS", new String[]{"ẞ"}},
                {"TH", new String[]{"Þ"}},
                {"TS", new String[]{"Ц"}},
                {"UE", new String[]{"Ü"}},
                {"YA", new String[]{"Я"}},
                {"YU", new String[]{"Ю"}},
                {"ZH", new String[]{"Ж"}},
            }
        ).collect(
            Collectors.toMap(
                kv -> (String) kv[0],
                kv -> (String[]) kv[1]
            )
        );

    /**
     * Source text.
     */
    private final Text origin;

    /**
     * Ctor.
     *
     * @param string Source.
     */
    public AsciiNonLatin(final String string) {
        this(new StringAsText(string));
    }

    /**
     * Ctor.
     *
     * @param text Origin.
     */
    public AsciiNonLatin(final Text text) {
        this.origin = new Ascii(
            text,
            input -> {
                final Map<String, String[]> map = new HashMap<>(0);
                map.putAll(AsciiNonLatin.MAPPING);
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
