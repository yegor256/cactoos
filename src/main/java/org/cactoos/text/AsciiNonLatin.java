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
 * @checkstyle JavaNCSSCheck (54 lines)
 */
public final class AsciiNonLatin implements Text {
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

    // @checkstyle ExecutableStatementCountCheck (55 line)
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
                map.put("aa", new String[]{"ع", "आ", "آ"});
                map.put("ae", new String[]{"ä", "æ", "ǽ"});
                map.put("ai", new String[]{"ऐ"});
                map.put("at", new String[]{"@"});
                map.put("ch", new String[]{"ч", "ჩ", "ჭ", "چ"});
                map.put("dj", new String[]{"ђ", "đ"});
                map.put("dz", new String[]{"џ", "ძ"});
                map.put("ei", new String[]{"ऍ"});
                map.put("gh", new String[]{"غ", "ღ"});
                map.put("ii", new String[]{"ई"});
                map.put("ij", new String[]{"ĳ"});
                map.put("kh", new String[]{"х", "خ", "ხ"});
                map.put("lj", new String[]{"љ"});
                map.put("nj", new String[]{"њ"});
                map.put("oe", new String[]{"ö", "œ", "ؤ"});
                map.put("oi", new String[]{"ऑ"});
                map.put("oii", new String[]{"ऒ"});
                map.put("ps", new String[]{"ψ"});
                map.put("sh", new String[]{"ш", "შ", "ش"});
                map.put("shch", new String[]{"щ"});
                map.put("ss", new String[]{"ß"});
                map.put("sx", new String[]{"ŝ"});
                map.put("th", new String[]{"þ", "ϑ", "ث", "ذ", "ظ"});
                map.put("ts", new String[]{"ц", "ც", "წ"});
                map.put("ue", new String[]{"ü"});
                map.put("uu", new String[]{"ऊ"});
                map.put("ya", new String[]{"я"});
                map.put("yu", new String[]{"ю"});
                map.put("zh", new String[]{"ж", "ჟ", "ژ"});
                map.put("(c)", new String[]{"©"});
                map.put("AE", new String[]{"Ä", "Æ", "Ǽ"});
                map.put("CH", new String[]{"Ч"});
                map.put("DJ", new String[]{"Ђ"});
                map.put("DZ", new String[]{"Џ"});
                map.put("GX", new String[]{"Ĝ"});
                map.put("HX", new String[]{"Ĥ"});
                map.put("IJ", new String[]{"Ĳ"});
                map.put("JX", new String[]{"Ĵ"});
                map.put("KH", new String[]{"Х"});
                map.put("LJ", new String[]{"Љ"});
                map.put("NJ", new String[]{"Њ"});
                map.put("OE", new String[]{"Ö", "Œ"});
                map.put("PS", new String[]{"Ψ"});
                map.put("SH", new String[]{"Ш"});
                map.put("SHCH", new String[]{"Щ"});
                map.put("SS", new String[]{"ẞ"});
                map.put("TH", new String[]{"Þ"});
                map.put("TS", new String[]{"Ц"});
                map.put("UE", new String[]{"Ü"});
                map.put("YA", new String[]{"Я"});
                map.put("YU", new String[]{"Ю"});
                map.put("ZH", new String[]{"Ж"});
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
