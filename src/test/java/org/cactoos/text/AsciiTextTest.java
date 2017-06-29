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
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link AsciiText}.
 *
 * @author Ix (ixmanuel@yahoo.com)
 * @version $Id$
 * @since 0.9
 * @checkstyle LineLengthCheck (500 lines)
 */
public final class AsciiTextTest {
    /**
     * An unicode text with latin, non-latin, numbers and spaces.
     */
    private static final String TXT_UNICODE =
        "°áéíóúñäëïöüج°¹ắβდفλж©Ǎẞª!|·$%&?¿*^Ç¨€ƋᴅᴆД";
    /**
     * An ascii text representation from TEX_UNICODE.
     */
    private static final String TXT_ASCII =
        "0aeiounaeeioeuej01abdflzh(c)ASSa!|·$%&?¿*^C¨€DDDD";

    /**
     * It is almost unfeasible to test the full set. Thus, it is a sample.
     * @throws IOException If fails
     */
    @Test
    public void asciiMappingTest() throws IOException {
        MatcherAssert.assertThat(
            "Can't transliterate an UTF-8 value to ASCII.",
            new AsciiText(AsciiTextTest.TXT_UNICODE).asString(),
            Matchers.equalTo(AsciiTextTest.TXT_ASCII)
        );
    }

    /**
     * It tests valid ascii symbols.
     * @throws IOException If fails
     */
    @Test
    public void validAsciiRangeTest() throws IOException {
        final String ascii = "!|·$%&?¿*^¨€";
        MatcherAssert.assertThat(
            "Valid ascii characters.",
            new AsciiText(ascii).asString(),
            Matchers.equalTo(ascii)
        );
    }

    /**
     * It tests the semantics of the compareTo.
     * @throws IOException If fails
     */
    @Test
    public void compareToTest() throws IOException {
        MatcherAssert.assertThat(
            "This text in unicode and ascii must be equal.",
            new AsciiText(AsciiTextTest.TXT_UNICODE)
            .compareTo(
                new StringAsText(AsciiTextTest.TXT_ASCII)
            ),
            Matchers.equalTo(0)
        );
    }

    /**
     * It translitarete unicode numbers to ascii.
     * @throws IOException If fails
     */
    @Test
    public void ascciNumberTest() throws IOException {
        MatcherAssert.assertThat(
            "Can't transliterate unicode numbers to Ascii.",
            new AsciiNumber("°₀۰").asString(),
            Matchers.equalTo("000")
        );
    }

    /**
     * It translitarete unicode numbers to ascii.
     * @throws IOException If fails
     */
    @Test
    public void asciiSpaceTest() throws IOException {
        MatcherAssert.assertThat(
            "Can't transliterate unicode spaces to Ascii.",
            new AsciiSpace(
                "\\xC2\\xA0\\xE2\\x80\\x80\\xE2\\x80\\x81"
            ).asString(),
            Matchers.equalTo("   ")
        );
    }

    /**
     * It translitarete unicode numbers to ascii.
     * @throws IOException If fails
     */
    @Test
    public void asciiLatinTest() throws IOException {
        MatcherAssert.assertThat(
            "Can't transliterate unicode latin alphabet to Ascii.",
            new AsciiLatin(
                "àáảãạăắằẳẵặâấầẩẫậāąåαάἀἁἂἃἄἅἆἇᾀᾁᾂᾃᾄᾅᾆᾇὰάᾰᾱᾲᾳᾴᾶᾷаأအာါǻǎªაअا"
            ).asString(),
            Matchers.equalTo(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            )
        );
    }

    /**
     * It translitarete unicode numbers to ascii.
     * @throws IOException If fails
     */
    @Test
    public void asciiNonLatinTest() throws IOException {
        MatcherAssert.assertThat(
            "Can't transliterate unicode non-latin alphabet to Ascii.",
            new AsciiNonLatin("عчჩჭچ").asString(),
            Matchers.equalTo("aachchchch")
        );
    }
}
