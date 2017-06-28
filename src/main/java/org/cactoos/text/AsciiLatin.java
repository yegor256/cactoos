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
@SuppressWarnings(
    {
        "PMD.TooManyFields",
        "PMD.ConstructorShouldDoInitialization"
    }
)
public final class AsciiLatin implements Text {
    /**
     * Source text.
     */
    private final Text origin;

    /**
     * Unicodes of the letter a.
     */
    private final String[] lowera = new String[]{
        "à", "á", "ả", "ã", "ạ", "ă", "ắ", "ằ", "ẳ",
        "ẵ", "ặ", "â", "ấ", "ầ", "ẩ", "ẫ", "ậ", "ā",
        "ą", "å", "α", "ά", "ἀ", "ἁ", "ἂ", "ἃ", "ἄ",
        "ἅ", "ἆ", "ἇ", "ᾀ", "ᾁ", "ᾂ", "ᾃ", "ᾄ", "ᾅ",
        "ᾆ", "ᾇ", "ὰ", "ά", "ᾰ", "ᾱ", "ᾲ", "ᾳ", "ᾴ",
        "ᾶ", "ᾷ", "а", "أ", "အ", "ာ", "ါ", "ǻ", "ǎ",
        "ª", "ა", "अ", "ا",
    };

    /**
     * Unicodes of the letter d.
     */
    private final String[] lowerd = new String[]{
        "ď", "ð", "đ", "ƌ", "ȡ", "ɖ", "ɗ", "ᵭ", "ᶁ",
        "ᶑ", "д", "δ", "د", "ض", "ဍ", "ဒ", "დ",
    };

    /**
     * Unicodes of the letter e.
     */
    private final String[] lowere = new String[]{
        "é", "è", "ẻ", "ẽ", "ẹ", "ê", "ế", "ề", "ể",
        "ễ", "ệ", "ë", "ē", "ę", "ě", "ĕ", "ė", "ε",
        "έ", "ἐ", "ἑ", "ἒ", "ἓ", "ἔ", "ἕ", "ὲ", "έ",
        "е", "ё", "э", "є", "ə", "ဧ", "ေ", "ဲ", "ე",
        "ए", "إ", "ئ",
    };

    /**
     * Unicodes of the letter i.
     */
    private final String[] loweri = new String[]{
        "í", "ì", "ỉ", "ĩ", "ị", "î", "ï", "ī", "ĭ",
        "į", "ı", "ι", "ί", "ϊ", "ΐ", "ἰ", "ἱ", "ἲ",
        "ἳ", "ἴ", "ἵ", "ἶ", "ἷ", "ὶ", "ί", "ῐ", "ῑ",
        "ῒ", "ΐ", "ῖ", "ῗ", "і", "ї", "и", "ဣ", "ိ",
        "ီ", "ည်", "ǐ", "ი", "इ",
    };

    /**
     * Unicodes of the letter k.
     */
    private final String[] lowerk = new String[]{
        "ķ", "ĸ", "к", "κ", "Ķ", "ق", "ك", "က", "კ",
        "ქ", "ک",
    };

    /**
     * Unicodes of the letter l.
     */
    private final String[] lowerl = new String[]{
        "ł", "ľ", "ĺ", "ļ", "ŀ", "л", "λ", "ل", "လ",
        "ლ",
    };

    /**
     * Unicodes of the letter n.
     */
    private final String[] lowern = new String[]{
        "ñ", "ń", "ň", "ņ", "ŉ", "ŋ", "ν", "н", "ن",
        "န", "ნ",
    };

    /**
     * Unicodes of the letter o.
     */
    private final String[] lowero = new String[]{
        "ó", "ò", "ỏ", "õ", "ọ", "ô", "ố", "ồ", "ổ",
        "ỗ", "ộ", "ơ", "ớ", "ờ", "ở", "ỡ", "ợ", "ø",
        "ō", "ő", "ŏ", "ο", "ὀ", "ὁ", "ὂ", "ὃ", "ὄ",
        "ὅ", "ὸ", "ό", "о", "و", "θ", "ို", "ǒ", "ǿ",
        "º", "ო", "ओ",
    };

    /**
     * Unicodes of the letter u.
     */
    private final  String[] loweru = new String[]{
        "ú", "ù", "ủ", "ũ", "ụ", "ư", "ứ", "ừ", "ử",
        "ữ", "ự", "û", "ū", "ů", "ű", "ŭ", "ų", "µ",
        "у", "ဉ", "ု", "ူ", "ǔ", "ǖ", "ǘ", "ǚ", "ǜ",
        "უ", "उ",
    };

    /**
     * Unicodes of the letter s.
     */
    private final String[] lowers = new String[]{
        "ś", "š", "ş", "с", "σ", "ș", "ς", "س", "ص",
        "စ", "ſ", "ს",
    };

    /**
     * Unicodes of the letter t.
     */
    private final String[] lowert = new String[]{
        "ť", "ţ", "т", "τ", "ț", "ت", "ط", "ဋ", "တ",
        "ŧ", "თ", "ტ",
    };

    /**
     * Unicodes of the letter y.
     */
    private final String[] lowery = new String[]{
        "ý", "ỳ", "ỷ", "ỹ", "ỵ", "ÿ", "ŷ", "й", "ы",
        "υ", "ϋ", "ύ", "ΰ", "ي", "ယ",
    };

    /**
     * Unicodes of the letter z.
     */
    private final String[] lowerz = new String[]{
        "ź", "ž", "ż", "з", "ζ", "ز", "ဇ", "ზ",
    };

    /**
     * Unicodes of the letter g.
     */
    private final String[] lowerg = new String[]{
        "ĝ", "ğ", "ġ", "ģ", "г", "ґ", "γ", "ဂ", "გ",
        "گ",
    };

    /**
     * Unicodes of the letter h.
     */
    private final String[] lowerh = new String[]{
        "ĥ", "ħ", "η", "ή", "ح", "ه", "ဟ", "ှ", "ჰ",
    };

    /**
     * Unicodes of the letter A.
     */
    private final String[] uppera = new String[]{
        "Á", "À", "Ả", "Ã", "Ạ", "Ă", "Ắ", "Ằ", "Ẳ",
        "Ẵ", "Ặ", "Â", "Ấ", "Ầ", "Ẩ", "Ẫ", "Ậ", "Å",
        "Ā", "Ą", "Α", "Ά", "Ἀ", "Ἁ", "Ἂ", "Ἃ", "Ἄ",
        "Ἅ", "Ἆ", "Ἇ", "ᾈ", "ᾉ", "ᾊ", "ᾋ", "ᾌ", "ᾍ",
        "ᾎ", "ᾏ", "Ᾰ", "Ᾱ", "Ὰ", "Ά", "ᾼ", "А", "Ǻ",
        "Ǎ",
    };

    /**
     * Unicoded of the letter D.
     */
    private final String[] upperd = new String[]{
        "Ď", "Ð", "Đ", "Ɖ", "Ɗ", "Ƌ", "ᴅ", "ᴆ", "Д",
        "Δ",
    };

    /**
     * Unicoded of the letter E.
     */
    private final String[] uppere = new String[]{
        "É", "È", "Ẻ", "Ẽ", "Ẹ", "Ê", "Ế", "Ề", "Ể",
        "Ễ", "Ệ", "Ë", "Ē", "Ę", "Ě", "Ĕ", "Ė", "Ε",
        "Έ", "Ἐ", "Ἑ", "Ἒ", "Ἓ", "Ἔ", "Ἕ", "Έ", "Ὲ",
        "Е", "Ё", "Э", "Є", "Ə",
    };

    /**
     * Unicoded of the letter I.
     */
    private final String[] upperi = new String[]{
        "Í", "Ì", "Ỉ", "Ĩ", "Ị", "Î", "Ï", "Ī", "Ĭ",
        "Į", "İ", "Ι", "Ί", "Ϊ", "Ἰ", "Ἱ", "Ἳ", "Ἴ",
        "Ἵ", "Ἶ", "Ἷ", "Ῐ", "Ῑ", "Ὶ", "Ί", "И", "І",
        "Ї", "Ǐ", "ϒ",
    };

    /**
     * Unicoded of the letter L.
     */
    private final String[] upperl = new String[]{
        "Ĺ", "Ł", "Л", "Λ", "Ļ", "Ľ", "Ŀ", "ल",
    };

    /**
     * Unicoded of the letter O.
     */
    private final String[] uppero = new String[]{
        "Ó", "Ò", "Ỏ", "Õ", "Ọ", "Ô", "Ố", "Ồ", "Ổ",
        "Ỗ", "Ộ", "Ơ", "Ớ", "Ờ", "Ở", "Ỡ", "Ợ", "Ø",
        "Ō", "Ő", "Ŏ", "Ο", "Ό", "Ὀ", "Ὁ", "Ὂ", "Ὃ",
        "Ὄ", "Ὅ", "Ὸ", "Ό", "О", "Θ", "Ө", "Ǒ", "Ǿ",
    };

    /**
     * Unicoded of the letter U.
     */
    private final String[] upperu = new String[]{
        "Ú", "Ù", "Ủ", "Ũ", "Ụ", "Ư", "Ứ", "Ừ", "Ử",
        "Ữ", "Ự", "Û", "Ū", "Ů", "Ű", "Ŭ", "Ų", "У",
        "Ǔ", "Ǖ", "Ǘ", "Ǚ", "Ǜ",
    };

    /**
     * Unicoded of the letter Y.
     */
    private final String[] uppery = new String[]{
        "Ý", "Ỳ", "Ỷ", "Ỹ", "Ỵ", "Ÿ", "Ῠ", "Ῡ", "Ὺ",
        "Ύ", "Ы", "Й", "Υ", "Ϋ", "Ŷ",
    };

    /**
     * Ctor.
     *
     * @param string Source.
     */
    public AsciiLatin(final String string) {
        this(new StringAsText(string));
    }

    // @checkstyle ExecutableStatementCountCheck (53 line)
    // @checkstyle JavaNCSSCheck (54 line)
    /**
     * Ctor.
     *
     * @param text Origin.
     */
    public AsciiLatin(final Text text) {
        this.origin = new Ascii(
            text,
            input -> {
                final Map<String, String[]> map = new HashMap<>(0);
                map.put("a", this.lowera);
                map.put(
                    "b",
                    new String[] {
                        "б", "β", "Ъ", "Ь", "ب", "ဗ", "ბ",
                    }
                );
                map.put(
                    "c",
                    new String[] {
                        "ç", "ć", "č", "ĉ", "ċ",
                    }
                );
                map.put("d", this.lowerd);
                map.put("e", this.lowere);
                map.put(
                    "f",
                    new String[]{
                        "ф", "φ", "ف", "ƒ", "ფ",
                    }
                );
                map.put("g", this.lowerg);
                map.put("h", this.lowerh);
                map.put("i", this.loweri);
                map.put(
                    "j",
                    new String[]{
                        "ĵ", "ј", "Ј", "ჯ", "ج",
                    }
                );
                map.put("k", this.lowerk);
                map.put("l", this.lowerl);
                map.put(
                    "m",
                    new String[]{
                        "м", "μ", "م", "မ", "მ",
                    }
                );
                map.put("n", this.lowern);
                map.put("o", this.lowero);
                map.put(
                    "p",
                    new String[]{
                        "п", "π", "ပ", "პ", "پ",
                    }
                );
                map.put("q", new String[]{"ყ"});
                map.put(
                    "r",
                    new String[]{
                        "ŕ", "ř", "ŗ", "р", "ρ", "ر", "რ",
                    }
                );
                map.put("s", this.lowers);
                map.put("t", this.lowert);
                map.put("u", this.loweru);
                map.put(
                    "v",
                    new String[]{
                        "в", "ვ", "ϐ",
                    }
                );
                map.put(
                    "w",
                    new String[]{
                        "ŵ", "ω", "ώ", "ဝ", "ွ",
                    }
                );
                map.put("x", new String[]{"χ", "ξ"});
                map.put("y", this.lowery);
                map.put("z", this.lowerz);
                map.put("A", this.uppera);
                map.put("B", new String[]{"Б", "Β", "ब"});
                map.put(
                    "C",
                    new String[]{
                        "Ç", "Ć", "Č", "Ĉ", "Ċ",
                    }
                );
                map.put("D", this.upperd);
                map.put("E", this.uppere);
                map.put("F", new String[]{"Ф", "Φ"});
                map.put(
                    "G",
                    new String[]{
                        "Ğ", "Ġ", "Ģ", "Г", "Ґ", "Γ",
                    }
                );
                map.put("H", new String[]{"Η", "Ή", "Ħ"});
                map.put("I", this.upperi);
                map.put("K", new String[]{"К", "Κ"});
                map.put("L", this.upperl);
                map.put("M", new String[]{"М", "Μ"});
                map.put(
                    "N",
                    new String[]{
                        "Ń", "Ñ", "Ň", "Ņ", "Ŋ", "Н", "Ν",
                    }
                );
                map.put("O", this.uppero);
                map.put("P", new String[]{"П", "Π"});
                map.put(
                    "R",
                    new String[]{
                        "Ř", "Ŕ", "Р", "Ρ", "Ŗ",
                    }
                );
                map.put(
                    "S",
                    new String[]{
                        "Ş", "Ŝ", "Ș", "Š", "Ś", "С", "Σ",
                    }
                );
                map.put(
                    "T",
                    new String[]{
                        "Ť", "Ţ", "Ŧ", "Ț", "Т", "Τ",
                    }
                );
                map.put("U", this.upperu);
                map.put("V", new String[]{"В"});
                map.put("W", new String[]{"Ω", "Ώ", "Ŵ"});
                map.put("X", new String[]{"Χ", "Ξ"});
                map.put("Y", this.uppery);
                map.put(
                    "Z",
                    new String[]{
                        "Ź", "Ž", "Ż", "З", "Ζ",
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
