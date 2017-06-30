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
 * Transliterate an UTF-8 value to ASCII. Error in windows. There are 5 string
 * literals in unicode "�?" that are not encoded in this OS, and thus, this
 * object has suppressed AvoidDuplicateLiterals in order to pass appveyor.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Ix (ixmanuel@yahoo.com)
 * @version $Id$
 * @since 0.11
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class AsciiLatin implements Text {

    /**
     * Lowercase alphabet.
     */
    private static final Map<String, String[]> LOWERCASE =
        Arrays.stream(
            new Object[][]{
                {
                    "a",
                    new String[]{
                        "à", "á", "ả", "ã", "ạ", "ă", "ắ", "ằ",
                        "ẳ", "ẵ", "ặ", "â", "ấ", "ầ", "ẩ", "ẫ",
                        "ậ", "ā", "ą", "å", "α", "ά", "ἀ", "ἁ",
                        "ἂ", "ἃ", "ἄ", "ἅ", "ἆ", "ἇ", "ᾀ", "ᾁ",
                        "ᾂ", "ᾃ", "ᾄ", "ᾅ", "ᾆ", "ᾇ", "ὰ", "ά",
                        "ᾰ", "ᾱ", "ᾲ", "ᾳ", "ᾴ", "ᾶ", "ᾷ", "а",
                        "أ", "အ", "ာ", "ါ", "ǻ", "ǎ", "ª", "ა",
                        "अ", "ا",
                    },
                },
                {
                    "b",
                    new String[] {
                        "б", "β", "Ъ", "Ь", "ب", "ဗ", "ბ",
                    },
                },
                {
                    "c",
                    new String[] {
                        "ç", "ć", "č", "ĉ", "ċ",
                    },
                },
                {
                    "d",
                    new String[]{
                        "ď", "ð", "đ", "ƌ", "ȡ", "ɖ", "ɗ", "ᵭ",
                        "ᶁ", "ᶑ", "д", "δ", "د", "ض", "ဍ", "ဒ",
                        "დ",
                    },
                },
                {
                    "e",
                    new String[]{
                        "é", "è", "ẻ", "ẽ", "ẹ", "ê", "ế", "ề",
                        "ể", "ễ", "ệ", "ë", "ē", "ę", "ě", "ĕ",
                        "ė", "ε", "έ", "ἐ", "ἑ", "ἒ", "ἓ", "ἔ",
                        "ἕ", "ὲ", "έ", "е", "ё", "э", "є", "ə",
                        "ဧ", "ေ", "ဲ", "ე", "ए", "إ", "ئ",
                    },
                },
                {
                    "f",
                    new String[]{
                        "ф", "φ", "ف", "ƒ", "ფ",
                    },
                },
                {
                    "g",
                    new String[]{
                        "ĝ", "ğ", "ġ", "ģ", "г", "ґ", "γ", "ဂ",
                        "გ", "گ",
                    },
                },
                {
                    "h",
                    new String[]{
                        "ĥ", "ħ", "η", "ή", "ح", "ه", "ဟ", "ှ",
                        "ჰ",
                    },
                },
                {
                    "i",
                    new String[]{
                        "í", "ì", "ỉ", "ĩ", "ị", "î", "ï", "ī",
                        "ĭ", "į", "ı", "ι", "ί", "ϊ", "ΐ", "ἰ",
                        "ἱ", "ἲ", "ἳ", "ἴ", "ἵ", "ἶ", "ἷ", "ὶ",
                        "ί", "ῐ", "ῑ", "ῒ", "ΐ", "ῖ", "ῗ", "і",
                        "ї", "и", "ဣ", "ိ", "ီ", "ည်", "ǐ", "ი",
                        "इ",
                    },
                },
                {
                    "j",
                    new String[]{
                        "ĵ", "ј", "Ј", "ჯ", "ج",
                    },
                },
                {
                    "k",
                    new String[]{
                        "ķ", "ĸ", "к", "κ", "Ķ", "ق", "ك", "က",
                        "კ", "ქ", "ک",
                    },
                },
                {
                    "l",
                    new String[]{
                        "ł", "ľ", "ĺ", "ļ", "ŀ", "л", "λ", "ل",
                        "လ", "ლ",
                    },
                },
                {
                    "m",
                    new String[]{
                        "м", "μ", "م", "မ", "მ",
                    },
                },
                {
                    "n",
                    new String[]{
                        "ñ", "ń", "ň", "ņ", "ŉ", "ŋ", "ν", "н",
                        "ن", "န", "ნ",
                    },
                },
                {
                    "o",
                    new String[]{
                        "ó", "ò", "ỏ", "õ", "ọ", "ô", "ố", "ồ",
                        "ổ", "ỗ", "ộ", "ơ", "ớ", "ờ", "ở", "ỡ",
                        "ợ", "ø", "ō", "ő", "ŏ", "ο", "ὀ", "ὁ",
                        "ὂ", "ὃ", "ὄ", "ὅ", "ὸ", "ό", "о", "و",
                        "θ", "ို", "ǒ", "ǿ", "º", "ო", "ओ",
                    },
                },
                {
                    "p",
                    new String[]{
                        "п", "π", "ပ", "პ", "پ",
                    },
                },
                {
                    "q",
                    new String[]{
                        "ყ",
                    },
                },
                {
                    "r",
                    new String[]{
                        "ŕ", "ř", "ŗ", "р", "ρ", "ر", "რ",
                    },
                },
                {
                    "s",
                    new String[]{
                        "ś", "š", "ş", "с", "σ", "ș", "ς", "س",
                        "ص", "စ", "ſ", "ს",
                    },
                },
                {
                    "t",
                    new String[]{
                        "ť", "ţ", "т", "τ", "ț", "ت", "ط", "ဋ",
                        "တ", "ŧ", "თ", "ტ",
                    },
                },
                {
                    "u",
                    new String[]{
                        "ú", "ù", "ủ", "ũ", "ụ", "ư", "ứ", "ừ",
                        "ử", "ữ", "ự", "û", "ū", "ů", "ű", "ŭ",
                        "ų", "µ", "у", "ဉ", "ု", "ူ", "ǔ", "ǖ",
                        "ǘ", "ǚ", "ǜ", "უ", "उ",
                    },
                },
                {
                    "v",
                    new String[]{
                        "в", "ვ", "ϐ",
                    },
                },
                {
                    "w",
                    new String[]{
                        "ŵ", "ω", "ώ", "ဝ", "ွ",
                    },
                },
                {
                    "x",
                    new String[]{
                        "χ", "ξ",
                    },
                },
                {
                    "y",
                    new String[]{
                        "ý", "ỳ", "ỷ", "ỹ", "ỵ", "ÿ", "ŷ", "й",
                        "ы", "υ", "ϋ", "ύ", "ΰ", "ي", "ယ",
                    },
                },
                {
                    "z",
                    new String[]{
                        "ź", "ž", "ż", "з", "ζ", "ز", "ဇ", "ზ",
                    },
                },
            }
        ).collect(
            Collectors.toMap(
                kv -> (String) kv[0],
                kv -> (String[]) kv[1]
            )
        );

    /**
     * Uppercase alphabet.
     */
    private static final Map<String, String[]> UPPERCASE =
        Arrays.stream(
            new Object[][]{
                {
                    "A",
                    new String[]{
                        "Á", "À", "Ả", "Ã", "Ạ", "Ă", "Ắ", "Ằ",
                        "Ẳ", "Ẵ", "Ặ", "Â", "Ấ", "Ầ", "Ẩ", "Ẫ",
                        "Ậ", "Å", "Ā", "Ą", "Α", "Ά", "Ἀ", "Ἁ",
                        "Ἂ", "Ἃ", "Ἄ", "Ἅ", "Ἆ", "Ἇ", "ᾈ", "ᾉ",
                        "ᾊ", "ᾋ", "ᾌ", "ᾍ", "ᾎ", "ᾏ", "Ᾰ", "Ᾱ",
                        "Ὰ", "Ά", "ᾼ", "А", "Ǻ", "Ǎ",
                    },
                },
                {
                    "B",
                    new String[]{
                        "Б", "Β", "ब",
                    },
                },
                {
                    "C",
                    new String[]{
                        "Ç", "Ć", "Č", "Ĉ", "Ċ",
                    },
                },
                {
                    "D",
                    new String[]{
                        "Ď", "Ð", "Đ", "Ɖ", "Ɗ", "Ƌ", "ᴅ", "ᴆ",
                        "Д", "Δ",
                    },
                },
                {
                    "E",
                    new String[]{
                        "É", "È", "Ẻ", "Ẽ", "Ẹ", "Ê", "Ế", "Ề",
                        "Ể", "Ễ", "Ệ", "Ë", "Ē", "Ę", "Ě", "Ĕ",
                        "Ė", "Ε", "Έ", "Ἐ", "Ἑ", "Ἒ", "Ἓ", "Ἔ",
                        "Ἕ", "Έ", "Ὲ", "Е", "Ё", "Э", "Є", "Ə",
                    },
                },
                {
                    "F",
                    new String[]{
                        "Ф", "Φ",
                    },
                },
                {
                    "G",
                    new String[]{
                        "Ğ", "Ġ", "Ģ", "Г", "Ґ", "Γ",
                    },
                },
                {
                    "H",
                    new String[]{
                        "Η", "Ή", "Ħ",
                    },
                },
                {
                    "I",
                    new String[]{
                        "Í", "Ì", "Ỉ", "Ĩ", "Ị", "Î", "Ï", "Ī",
                        "Ĭ", "Į", "İ", "Ι", "Ί", "Ϊ", "Ἰ", "Ἱ",
                        "Ἳ", "Ἴ", "Ἵ", "Ἶ", "Ἷ", "Ῐ", "Ῑ", "Ὶ",
                        "Ί", "И", "І", "Ї", "Ǐ", "ϒ",
                    },
                },
                {
                    "K",
                    new String[]{
                        "К", "Κ",
                    },
                },
                {
                    "L",
                    new String[]{
                        "Ĺ", "Ł", "Л", "Λ", "Ļ", "Ľ", "Ŀ", "ल",
                    },
                },
                {
                    "M",
                    new String[]{
                        "М", "Μ",
                    },
                },
                {
                    "N",
                    new String[]{
                        "Ń", "Ñ", "Ň", "Ņ", "Ŋ", "Н", "Ν",
                    },
                },
                {
                    "O",
                    new String[]{
                        "Ó", "Ò", "Ỏ", "Õ", "Ọ", "Ô", "Ố", "Ồ",
                        "Ổ", "Ỗ", "Ộ", "Ơ", "Ớ", "Ờ", "Ở", "Ỡ",
                        "Ợ", "Ø", "Ō", "Ő", "Ŏ", "Ο", "Ό", "Ὀ",
                        "Ὁ", "Ὂ", "Ὃ", "Ὄ", "Ὅ", "Ὸ", "Ό", "О",
                        "Θ", "Ө", "Ǒ", "Ǿ",
                    },
                },
                {
                    "P",
                    new String[]{
                        "П", "Π",
                    },
                },
                {
                    "R",
                    new String[]{
                        "Ř", "Ŕ", "Р", "Ρ", "Ŗ",
                    },
                },
                {
                    "S",
                    new String[]{
                        "Ş", "Ŝ", "Ș", "Š", "Ś", "С", "Σ",
                    },
                },
                {
                    "T",
                    new String[]{
                        "Ť", "Ţ", "Ŧ", "Ț", "Т", "Τ",
                    },
                },
                {
                    "U",
                    new String[]{
                        "Ú", "Ù", "Ủ", "Ũ", "Ụ", "Ư", "Ứ", "Ừ",
                        "Ử", "Ữ", "Ự", "Û", "Ū", "Ů", "Ű", "Ŭ",
                        "Ų", "У", "Ǔ", "Ǖ", "Ǘ", "Ǚ", "Ǜ",
                    },
                },
                {
                    "V",
                    new String[]{
                        "В",
                    },
                },
                {
                    "W",
                    new String[]{
                        "Ω", "Ώ", "Ŵ",
                    },
                },
                {
                    "X",
                    new String[]{
                        "Χ", "Ξ",
                    },
                },
                {
                    "Y",
                    new String[]{
                        "Ý", "Ỳ", "Ỷ", "Ỹ", "Ỵ", "Ÿ", "Ῠ", "Ῡ",
                        "Ὺ", "Ύ", "Ы", "Й", "Υ", "Ϋ", "Ŷ",
                    },
                },
                {
                    "Z",
                    new String[]{
                        "Ź", "Ž", "Ż", "З", "Ζ",
                    },
                },
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
    public AsciiLatin(final String string) {
        this(new StringAsText(string));
    }

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
                map.putAll(AsciiLatin.LOWERCASE);
                map.putAll(AsciiLatin.UPPERCASE);
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
