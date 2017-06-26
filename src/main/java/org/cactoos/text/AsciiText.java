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
public final class AsciiText implements Text {
    /**
     * Source text.
     */
    private final Text text;

    /**
     * Unicode transliteration to ascii.
     */
    private Map<String, String[]> map;   

    /**
     * Ctor.
     *
     * @param string source.
     */
    public AsciiText(final String string) {
        this(new StringAsText(string));
    }

    /**
     * Ctor.
     *
     * @param text origin.
     */
    public AsciiText(final Text text) {
        this.text = text;
        this.map = new HashMap<>(0);
    }

    @Override
    public String asString() throws IOException {
        String value = this.text.asString();
        for (final Map.Entry<String, String[]> entry : this.replacements().entrySet()) {
            for (final String unicode : entry.getValue()) {
                value = value.replace(unicode, entry.getKey());
            }            
        }
        return this.removedNonAscii(value); 
    }

    @Override
    public int compareTo(final Text text) {
        return new UncheckedText(this).compareTo(text);
    }    

    /**
     * @see https://github.com/danielstjules/Stringy/blob/2.3.1/LICENSE.txt
     * @return a map of replacements in ascii from unicode.
     */
    private Map<String, String[]> replacements() {
        if (this.map.size() == 0) {
            this.map.put("0", new String[]{"°", "₀", "۰"});
            this.map.put("1", new String[]{"¹", "₁", "۱"});
            this.map.put("2", new String[]{"²", "₂", "۲"});
            this.map.put("3", new String[]{"³", "₃", "۳"});
            this.map.put("4", new String[]{"⁴", "₄", "۴", "٤"});
            this.map.put("5", new String[]{"⁵", "₅", "۵", "٥"});
            this.map.put("6", new String[]{"⁶", "₆", "۶", "٦"});
            this.map.put("7", new String[]{"⁷", "₇", "۷"});
            this.map.put("8", new String[]{"⁸", "₈", "۸"});
            this.map.put("9", new String[]{"⁹", "₉", "۹"});
            this.map.put("a", new String[]{ "à", "á", "ả", "ã", "ạ", "ă", "ắ", 
                                            "ằ", "ẳ", "ẵ", "ặ", "â", "ấ", "ầ", 
                                            "ẩ", "ẫ", "ậ", "ā", "ą", "å", "α", 
                                            "ά", "ἀ", "ἁ", "ἂ", "ἃ", "ἄ", "ἅ", 
                                            "ἆ", "ἇ", "ᾀ", "ᾁ", "ᾂ", "ᾃ", "ᾄ", 
                                            "ᾅ", "ᾆ", "ᾇ", "ὰ", "ά", "ᾰ", "ᾱ", 
                                            "ᾲ", "ᾳ", "ᾴ", "ᾶ", "ᾷ", "а", "أ", 
                                            "အ", "ာ", "ါ", "ǻ", "ǎ", "ª", "ა", 
                                            "अ", "ا"});
            this.map.put("b", new String[]{"б", "β", "Ъ", "Ь", "ب", "ဗ", "ბ"});
            this.map.put("c", new String[]{"ç", "ć", "č", "ĉ", "ċ"});
            this.map.put("d", new String[]{ "ď", "ð", "đ", "ƌ", "ȡ", "ɖ", "ɗ",
                                            "ᵭ", "ᶁ", "ᶑ", "д", "δ", "د", "ض", 
                                            "ဍ", "ဒ", "დ"});
            this.map.put("e", new String[]{ "é", "è", "ẻ", "ẽ", "ẹ", "ê", "ế", 
                                            "ề", "ể", "ễ", "ệ", "ë", "ē", "ę", 
                                            "ě", "ĕ", "ė", "ε", "έ", "ἐ", "ἑ", 
                                            "ἒ", "ἓ", "ἔ", "ἕ", "ὲ", "έ", "е", 
                                            "ё", "э", "є", "ə", "ဧ", "ေ", "ဲ", 
                                            "ე", "ए", "إ", "ئ"});
            this.map.put("f", new String[]{"ф", "φ", "ف", "ƒ", "ფ"});
            this.map.put("g", new String[]{ "ĝ", "ğ", "ġ", "ģ", "г", "ґ", "γ",
                                             "ဂ", "გ", "گ"});
            this.map.put("h", new String[]{ "ĥ", "ħ", "η", "ή", "ح", "ه", "ဟ", 
                                            "ှ", "ჰ"});
            this.map.put("i", new String[]{ "í", "ì", "ỉ", "ĩ", "ị", "î", "ï", 
                                            "ī", "ĭ", "į", "ı", "ι", "ί", "ϊ", 
                                            "ΐ", "ἰ", "ἱ", "ἲ", "ἳ", "ἴ", "ἵ", 
                                            "ἶ", "ἷ", "ὶ", "ί", "ῐ", "ῑ", "ῒ", 
                                            "ΐ", "ῖ", "ῗ", "і", "ї", "и", "ဣ", 
                                            "ိ", "ီ", "ည်", "ǐ", "ი", "इ"});
            this.map.put("j", new String[]{"ĵ", "ј", "Ј", "ჯ", "ج"});
            this.map.put("k", new String[]{ "ķ", "ĸ", "к", "κ", "Ķ", "ق", "ك", 
                                            "က", "კ", "ქ", "ک"});
            this.map.put("l", new String[]{ "ł", "ľ", "ĺ", "ļ", "ŀ", "л", "λ", 
                                            "ل", "လ", "ლ"});
            this.map.put("m", new String[]{"м", "μ", "م", "မ", "მ"});
            this.map.put("n", new String[]{ "ñ", "ń", "ň", "ņ", "ŉ", "ŋ", "ν", 
                                            "н", "ن", "န", "ნ"});
            this.map.put("o", new String[]{ "ó", "ò", "ỏ", "õ", "ọ", "ô", "ố", 
                                            "ồ", "ổ", "ỗ", "ộ", "ơ", "ớ", "ờ", 
                                            "ở", "ỡ", "ợ", "ø", "ō", "ő", "ŏ", 
                                            "ο", "ὀ", "ὁ", "ὂ", "ὃ", "ὄ", "ὅ", 
                                            "ὸ", "ό", "о", "و", "θ", "ို", "ǒ", 
                                            "ǿ", "º", "ო", "ओ"});
            this.map.put("p", new String[]{"п", "π", "ပ", "პ", "پ"});
            this.map.put("q", new String[]{"ყ"});
            this.map.put("r", new String[]{"ŕ", "ř", "ŗ", "р", "ρ", "ر", "რ"});
            this.map.put("s", new String[]{ "ś", "š", "ş", "с", "σ", "ș", "ς", 
                                            "س", "ص", "စ", "ſ", "ს"});
            this.map.put("t", new String[]{ "ť", "ţ", "т", "τ", "ț", "ت", "ط", 
                                            "ဋ", "တ", "ŧ", "თ", "ტ"});
            this.map.put("u", new String[]{ "ú", "ù", "ủ", "ũ", "ụ", "ư", "ứ", 
                                            "ừ", "ử", "ữ", "ự", "û", "ū", "ů", 
                                            "ű", "ŭ", "ų", "µ", "у", "ဉ", "ု", 
                                            "ူ", "ǔ", "ǖ", "ǘ", "ǚ", "ǜ", "უ", 
                                            "उ"});
            this.map.put("v", new String[]{"в", "ვ", "ϐ"});
            this.map.put("w", new String[]{"ŵ", "ω", "ώ", "ဝ", "ွ"});
            this.map.put("x", new String[]{"χ", "ξ"});
            this.map.put("y", new String[]{ "ý", "ỳ", "ỷ", "ỹ", "ỵ", "ÿ", "ŷ", 
                                            "й", "ы", "υ", "ϋ", "ύ", "ΰ", "ي", 
                                            "ယ"});
            this.map.put("z", new String[]{ "ź", "ž", "ż", "з", "ζ", "ز", "ဇ", 
                                            "ზ"});
            this.map.put("aa", new String[]{"ع", "आ", "آ"});
            this.map.put("ae", new String[]{"ä", "æ", "ǽ"});
            this.map.put("ai", new String[]{"ऐ"});
            this.map.put("at", new String[]{"@"});
            this.map.put("ch", new String[]{"ч", "ჩ", "ჭ", "چ"});
            this.map.put("dj", new String[]{"ђ", "đ"});
            this.map.put("dz", new String[]{"џ", "ძ"});
            this.map.put("ei", new String[]{"ऍ"});
            this.map.put("gh", new String[]{"غ", "ღ"});
            this.map.put("ii", new String[]{"ई"});
            this.map.put("ij", new String[]{"ĳ"});
            this.map.put("kh", new String[]{"х", "خ", "ხ"});
            this.map.put("lj", new String[]{"љ"});
            this.map.put("nj", new String[]{"њ"});
            this.map.put("oe", new String[]{"ö", "œ", "ؤ"});
            this.map.put("oi", new String[]{"ऑ"});
            this.map.put("oii", new String[]{"ऒ"});
            this.map.put("ps", new String[]{"ψ"});
            this.map.put("sh", new String[]{"ш", "შ", "ش"});
            this.map.put("shch", new String[]{"щ"});
            this.map.put("ss", new String[]{"ß"});
            this.map.put("sx", new String[]{"ŝ"});
            this.map.put("th", new String[]{"þ", "ϑ", "ث", "ذ", "ظ"});
            this.map.put("ts", new String[]{"ц", "ც", "წ"});
            this.map.put("ue", new String[]{"ü"});
            this.map.put("uu", new String[]{"ऊ"});
            this.map.put("ya", new String[]{"я"});
            this.map.put("yu", new String[]{"ю"});
            this.map.put("zh", new String[]{"ж", "ჟ", "ژ"});
            this.map.put("(c)", new String[]{"©"});
            this.map.put("A", new String[]{ "Á", "À", "Ả", "Ã", "Ạ", "Ă", "Ắ",
                                             "Ằ", "Ẳ", "Ẵ", "Ặ", "Â", "Ấ", "Ầ", 
                                             "Ẩ", "Ẫ", "Ậ", "Å", "Ā", "Ą", "Α", 
                                             "Ά", "Ἀ", "Ἁ", "Ἂ", "Ἃ", "Ἄ", "Ἅ", 
                                             "Ἆ", "Ἇ", "ᾈ", "ᾉ", "ᾊ", "ᾋ", "ᾌ", 
                                             "ᾍ", "ᾎ", "ᾏ", "Ᾰ", "Ᾱ", "Ὰ", "Ά", 
                                             "ᾼ", "А", "Ǻ", "Ǎ"});
            this.map.put("B", new String[]{"Б", "Β", "ब"});
            this.map.put("C", new String[]{"Ç", "Ć", "Č", "Ĉ", "Ċ"});
            this.map.put("D", new String[]{ "Ď", "Ð", "Đ", "Ɖ", "Ɗ", "Ƌ", "ᴅ", 
                                            "ᴆ", "Д", "Δ"});
            this.map.put("E", new String[]{ "É", "È", "Ẻ", "Ẽ", "Ẹ", "Ê", "Ế",
                                             "Ề", "Ể", "Ễ", "Ệ", "Ë", "Ē", "Ę", 
                                             "Ě", "Ĕ", "Ė", "Ε", "Έ", "Ἐ", "Ἑ", 
                                             "Ἒ", "Ἓ", "Ἔ", "Ἕ", "Έ", "Ὲ", "Е", 
                                             "Ё", "Э", "Є", "Ə"});
            this.map.put("F", new String[]{"Ф", "Φ"});
            this.map.put("G", new String[]{"Ğ", "Ġ", "Ģ", "Г", "Ґ", "Γ"});
            this.map.put("H", new String[]{"Η", "Ή", "Ħ"});
            this.map.put("I", new String[]{ "Í", "Ì", "Ỉ", "Ĩ", "Ị", "Î", "Ï",
                                             "Ī", "Ĭ", "Į", "İ", "Ι", "Ί", "Ϊ", 
                                             "Ἰ", "Ἱ", "Ἳ", "Ἴ", "Ἵ", "Ἶ", "Ἷ", 
                                             "Ῐ", "Ῑ", "Ὶ", "Ί", "И", "І", "Ї", 
                                             "Ǐ", "ϒ"});
            this.map.put("K", new String[]{"К", "Κ"});
            this.map.put("L", new String[]{ "Ĺ", "Ł", "Л", "Λ", "Ļ", "Ľ", "Ŀ", 
                                            "ल"});
            this.map.put("M", new String[]{"М", "Μ"});
            this.map.put("N", new String[]{"Ń", "Ñ", "Ň", "Ņ", "Ŋ", "Н", "Ν"});
            this.map.put("O", new String[]{ "Ó", "Ò", "Ỏ", "Õ", "Ọ", "Ô", "Ố", 
                                            "Ồ", "Ổ", "Ỗ", "Ộ", "Ơ", "Ớ", "Ờ", 
                                            "Ở", "Ỡ", "Ợ", "Ø", "Ō", "Ő", "Ŏ", 
                                            "Ο", "Ό", "Ὀ", "Ὁ", "Ὂ", "Ὃ", "Ὄ", 
                                            "Ὅ", "Ὸ", "Ό", "О", "Θ", "Ө", "Ǒ", 
                                            "Ǿ"});
            this.map.put("P", new String[]{"П", "Π"});
            this.map.put("R", new String[]{"Ř", "Ŕ", "Р", "Ρ", "Ŗ"});
            this.map.put("S", new String[]{"Ş", "Ŝ", "Ș", "Š", "Ś", "С", "Σ"});
            this.map.put("T", new String[]{"Ť", "Ţ", "Ŧ", "Ț", "Т", "Τ"});
            this.map.put("U", new String[]{ "Ú", "Ù", "Ủ", "Ũ", "Ụ", "Ư", "Ứ", 
                                            "Ừ", "Ử", "Ữ", "Ự", "Û", "Ū", "Ů", 
                                            "Ű", "Ŭ", "Ų", "У", "Ǔ", "Ǖ", "Ǘ", 
                                            "Ǚ", "Ǜ"});
            this.map.put("V", new String[]{"В"});
            this.map.put("W", new String[]{"Ω", "Ώ", "Ŵ"});
            this.map.put("X", new String[]{"Χ", "Ξ"});
            this.map.put("Y", new String[]{ "Ý", "Ỳ", "Ỷ", "Ỹ", "Ỵ", "Ÿ", "Ῠ", 
                                            "Ῡ", "Ὺ", "Ύ", "Ы", "Й", "Υ", "Ϋ", 
                                            "Ŷ"});
            this.map.put("Z", new String[]{"Ź", "Ž", "Ż", "З", "Ζ"});
            this.map.put("AE", new String[]{"Ä", "Æ", "Ǽ"});
            this.map.put("CH", new String[]{"Ч"});
            this.map.put("DJ", new String[]{"Ђ"});
            this.map.put("DZ", new String[]{"Џ"});
            this.map.put("GX", new String[]{"Ĝ"});
            this.map.put("HX", new String[]{"Ĥ"});
            this.map.put("IJ", new String[]{"Ĳ"});
            this.map.put("JX", new String[]{"Ĵ"});
            this.map.put("KH", new String[]{"Х"});
            this.map.put("LJ", new String[]{"Љ"});
            this.map.put("NJ", new String[]{"Њ"});
            this.map.put("OE", new String[]{"Ö", "Œ"});
            this.map.put("PS", new String[]{"Ψ"});
            this.map.put("SH", new String[]{"Ш"});
            this.map.put("SHCH", new String[]{"Щ"});
            this.map.put("SS", new String[]{"ẞ"});
            this.map.put("TH", new String[]{"Þ"});
            this.map.put("TS", new String[]{"Ц"});
            this.map.put("UE", new String[]{"Ü"});
            this.map.put("YA", new String[]{"Я"});
            this.map.put("YU", new String[]{"Ю"});
            this.map.put("ZH", new String[]{"Ж"});
            this.map.put(" ", new String[]{ "\\xC2\\xA0", 
                                            "\\xE2\\x80\\x80", 
                                            "\\xE2\\x80\\x81", 
                                            "\\xE2\\x80\\x82", 
                                            "\\xE2\\x80\\x83", 
                                            "\\xE2\\x80\\x84", 
                                            "\\xE2\\x80\\x85", 
                                            "\\xE2\\x80\\x86", 
                                            "\\xE2\\x80\\x87", 
                                            "\\xE2\\x80\\x88", 
                                            "\\xE2\\x80\\x89", 
                                            "\\xE2\\x80\\x8A", 
                                            "\\xE2\\x80\\xAF", 
                                            "\\xE2\\x81\\x9F", 
                                            "\\xE3\\x80\\x80"});
        }
        return this.map;
    }  

    /**
     * @see http://www.asciitable.com
     * @return String without non-ascii characters.
     */
    private String removedNonAscii(String value) {
        return value.replaceAll("/(?u)[^\\x20-\\x7e]/", "");
    }
}
