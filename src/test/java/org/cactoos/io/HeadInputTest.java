/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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
package org.cactoos.io;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.cactoos.matchers.InputHasContent;
import org.cactoos.matchers.TextHasString;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.takes.http.FtRemote;
import org.takes.tk.TkHtml;

/**
 * Test case for {@link HeadInput}.
 *
 * @author Mehmet Yildirim (memoyil@gmail.com)
 * @version $Id$
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class HeadInputTest {

    @Test
    public void readsRealUrl() throws IOException {
        final int length = 5;
        new FtRemote(new TkHtml("<html>How are you?</html>")).exec(
            home -> MatcherAssert.assertThat(
                "Can't fetch bytes from the URL",
                new TextOf(
                    new HeadInput(home, length)
                ),
                new TextHasString(
                    "<html"
                )
            )
        );
    }

    @Test
    public void readsStringUrl() throws IOException {
        final int length = 5;
        MatcherAssert.assertThat(
            "Can't fetch bytes from the HTTPS URL",
            new TextOf(
                new HeadInput(
                    new URL(
                        // @checkstyle LineLength (1 line)
                        "file:src/test/resources/org/cactoos/large-text.txt"
                    ), length
                )
            ),
            new TextHasString(Matchers.containsString("Lorem"))
        );
    }

    @Test
    public void readsStringFromReader() throws Exception {
        final String source = "hello, source!";
        final int length = 6;
        MatcherAssert.assertThat(
            "Can't read string through a reader",
            new TextOf(
                new HeadInput(
                    new StringReader(source),
                    length
                )
            ).asString(),
            Matchers.equalTo(source.substring(0, length))
        );
    }

    @Test
    public void readsEncodedStringFromReader() throws Exception {
        final String source = "hello, друг!";
        final int length = 6;
        MatcherAssert.assertThat(
            "Can't read encoded string through a reader",
            new TextOf(
                new HeadInput(
                    new StringReader(source),
                    StandardCharsets.UTF_8,
                    length
                )
            ).asString(),
            Matchers.equalTo(source.substring(0, length))
        );
    }

    @Test
    public void readsEncodedStringWithCharsetFromReader() throws Exception {
        final String source = "hello, друг!";
        final int length = 6;
        MatcherAssert.assertThat(
            "Can't read encoded string through a reader",
            new TextOf(
                new HeadInput(
                    new StringReader(source),
                    "UTF-8",
                    length
                )
            ).asString(),
            Matchers.equalTo(source.substring(0, length))
        );
    }

    @Test
    public void readsMaxBufferFromReader() throws Exception {
        final String source = "hello, друг!";
        final int length = 6;
        final int max = 20;
        MatcherAssert.assertThat(
            "Can't read encoded string through a reader",
            new TextOf(
                new HeadInput(
                    new StringReader(source),
                    max,
                    length
                )
            ).asString(),
            Matchers.equalTo(source.substring(0, length))
        );
    }

    @Test
    public void readsMaxBufferEncodedFromReader() throws Exception {
        final String source = "hello, друг!";
        final int length = 6;
        final int max = 20;
        MatcherAssert.assertThat(
            "Can't read encoded string through a reader",
            new TextOf(
                new HeadInput(
                    new StringReader(source),
                    StandardCharsets.UTF_8,
                    max,
                    length
                )
            ).asString(),
            Matchers.equalTo(source.substring(0, length))
        );
    }

    @Test
    public void readsMaxBufferEncodedStringFromReader() throws Exception {
        final String source = "hello, друг!";
        final int length = 6;
        final int max = 20;
        MatcherAssert.assertThat(
            "Can't read encoded string through a reader",
            new TextOf(
                new HeadInput(
                    new StringReader(source),
                    "UTF-8",
                    max,
                    length
                )
            ).asString(),
            Matchers.equalTo(source.substring(0, length))
        );
    }

    @Test
    public void readsArrayOfChars() throws IOException {
        final int length = 6;
        MatcherAssert.assertThat(
            "Can't read array of chars.",
            new String(
                new BytesOf(
                    new HeadInput(
                        length,
                        'H', 'o', 'l', 'd', ' ',
                        'i', 'n', 'f', 'i', 'n', 'i', 't', 'y'
                    )
                ).asBytes()
            ),
            Matchers.equalTo("Hold i")
        );
    }

    @Test
    public void readsEncodedArrayOfChars() throws IOException {
        final int length = 2;
        MatcherAssert.assertThat(
            "Can't read array of chars.",
            new String(
                new BytesOf(
                    new HeadInput(
                        new char[]{'H', 'o', 'l', 'd'},
                        StandardCharsets.UTF_8,
                        length
                    )
                ).asBytes()
            ),
            Matchers.equalTo("Ho")
        );
    }

    @Test
    public void readsEncodedStringArrayOfChars() throws IOException {
        final int length = 2;
        MatcherAssert.assertThat(
            "Can't read array of chars.",
            new String(
                new BytesOf(
                    new HeadInput(
                        new char[]{'H', 'o', 'l', 'd'},
                        "UTF-8",
                        length
                    )
                ).asBytes()
            ),
            Matchers.equalTo("Ho")
        );
    }

    @Test
    public void readsCharSequence() throws Exception {
        final int length = 3;
        final CharSequence charsequence = "Cactoos";
        MatcherAssert.assertThat(
            "Can't read charsequence of bytes",
            new String(
                new BytesOf(
                    new HeadInput(charsequence, length)
                ).asBytes()
            ),
            Matchers.equalTo("Cac")
        );
    }

    @Test
    public void readsEncodedCharSequence() throws Exception {
        final int length = 3;
        final CharSequence charsequence = "Cactoos";
        MatcherAssert.assertThat(
            "Can't read charsequence of bytes",
            new String(
                new BytesOf(
                    new HeadInput(
                        charsequence,
                        StandardCharsets.UTF_8,
                        length
                    )
                ).asBytes()
            ),
            Matchers.equalTo("Cac")
        );
    }

    @Test
    public void readsEncodedStringCharSequence() throws Exception {
        final int length = 3;
        final CharSequence charsequence = "Cactoos";
        MatcherAssert.assertThat(
            "Can't read charsequence of bytes",
            new String(
                new BytesOf(
                    new HeadInput(
                        charsequence,
                        "UTF-8",
                        length
                    )
                ).asBytes()
            ),
            Matchers.equalTo("Cac")
        );
    }

    @Test
    public void readsText() {
        final int length = 4;
        MatcherAssert.assertThat(
            "Can't read string from text",
            new TextOf(
                new HeadInput(
                    new TextOf("Cactoos"), length
                )
            ),
            new TextHasString(Matchers.containsString("Cact"))
        );
    }

    @Test
    public void readsEncodedText() {
        final int length = 5;
        MatcherAssert.assertThat(
            "Can't read string from text",
            new TextOf(
                new HeadInput(
                    new TextOf("Cactoos"), StandardCharsets.UTF_8, length
                )
            ),
            new TextHasString(Matchers.containsString("Cact"))
        );
    }

    @Test
    public void readsEncodedStringText() {
        final int length = 4;
        MatcherAssert.assertThat(
            "Can't read string from text",
            new TextOf(
                new HeadInput(
                    new TextOf("Cactoos"), "UTF-8", length
                )
            ),
            new TextHasString(Matchers.containsString("Cact"))
        );
    }

    @Test
    public void readsAnArrayOfBytes() throws Exception {
        final int length = 1;
        final byte[] bytes = new byte[]{(byte) 0xCA, (byte) 0xFE};
        MatcherAssert.assertThat(
            "Can't read array of bytes",
            new InputAsBytes(
                new SyncInput(new HeadInput(bytes, length))
            ).asBytes(),
            Matchers.equalTo(new byte[]{bytes[0]})
        );
    }

    @Test
    public void readsSimpleFileContent() throws IOException {
        final int length = 5;
        final Path temp = Files.createTempFile("cactoos-1", "txt-1");
        final String content = "Hello, товарищ!";
        Files.write(temp, content.getBytes(StandardCharsets.UTF_8));
        MatcherAssert.assertThat(
            "Can't read file content",
            new HeadInput(temp, length),
            new InputHasContent(content.split(",")[0])
        );
    }

    @Test
    public void readsFromInputStream() throws IOException {
        final int length = 3;
        final Path temp = Files.createTempFile("cactoos-1", "txt-1");
        final String content = "Hello, товарищ!";
        Files.write(temp, content.getBytes(StandardCharsets.UTF_8));
        MatcherAssert.assertThat(
            "Can't read file content",
            new HeadInput(new InputStreamOf(temp), length),
            new InputHasContent(content.substring(0, length))
        );
    }

}
