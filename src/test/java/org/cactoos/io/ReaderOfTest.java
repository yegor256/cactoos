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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.cactoos.matchers.InputHasContent;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Test case for {@link ReaderOf}.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.13
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.AvoidDuplicateLiterals"})
public final class ReaderOfTest {

    @Test
    public void readsCharVarArg() throws IOException {
        MatcherAssert.assertThat(
            "Can't read content",
            new InputOf(new ReaderOf('a', 'b', 'c')),
            new InputHasContent("abc")
        );
    }

    @Test
    public void readsCharArrayWithCharset() throws IOException {
        final char[] input = new char[]{'a', 'b', 'c', };
        MatcherAssert.assertThat(
            "Can't read content",
            new InputOf(new ReaderOf(input, StandardCharsets.UTF_8)),
            new InputHasContent("abc")
        );
    }

    @Test
    public void readsCharArrayWithCharsetByName() throws IOException {
        final char[] input = new char[]{'a', 'b', 'c'};
        MatcherAssert.assertThat(
            "Can't read content",
            new InputOf(new ReaderOf(input, StandardCharsets.UTF_8.name())),
            new InputHasContent("abc")
        );
    }

    @Test
    public void readsByteArray() throws IOException {
        MatcherAssert.assertThat(
            "Can't read content",
            new InputOf(new ReaderOf("abc".getBytes(StandardCharsets.UTF_8))),
            new InputHasContent("abc")
        );
    }

    @Test
    public void readsByteArrayWithCharset() throws IOException {
        MatcherAssert.assertThat(
            "Can't read content",
            new InputOf(
                new ReaderOf(
                    "abc".getBytes(StandardCharsets.UTF_8),
                    StandardCharsets.UTF_8
                )
            ),
            new InputHasContent("abc")
        );
    }

    @Test
    public void readsByteArrayWithCharsetByName() throws IOException {
        MatcherAssert.assertThat(
            "Can't read content",
            new InputOf(
                new ReaderOf(
                    "abc".getBytes(StandardCharsets.UTF_8),
                    StandardCharsets.UTF_8.name()
                )
            ),
            new InputHasContent("abc")
        );
    }

    @Test
    public void readsPath() throws IOException {
        final Path temp = Files.createTempFile("cactoos-1", "txt-1");
        final String content = "Hello, товарищ!";
        Files.write(temp, content.getBytes(StandardCharsets.UTF_8));
        MatcherAssert.assertThat(
            "Can't read file content",
            new InputOf(new ReaderOf(temp)),
            new InputHasContent(content)
        );
    }

    @Test
    public void readsFile() throws IOException {
        final Path temp = Files.createTempFile("cactoos-1", "txt-1");
        final String content = "Hello, товарищ!";
        Files.write(temp, content.getBytes(StandardCharsets.UTF_8));
        MatcherAssert.assertThat(
            "Can't read file content",
            new InputOf(new ReaderOf(temp.toFile())),
            new InputHasContent(content)
        );
    }

    @Test
    public void readsUrl() throws IOException {
        final Path temp = Files.createTempFile("cactoos-1", "txt-1");
        final String content = "Hello, товарищ!";
        Files.write(temp, content.getBytes(StandardCharsets.UTF_8));
        MatcherAssert.assertThat(
            "Can't read file content",
            new InputOf(new ReaderOf(temp.toUri().toURL())),
            new InputHasContent(content)
        );
    }

    @Test
    public void readsUri() throws IOException {
        final Path temp = Files.createTempFile("cactoos-1", "txt-1");
        final String content = "Hello, товарищ!";
        Files.write(temp, content.getBytes(StandardCharsets.UTF_8));
        MatcherAssert.assertThat(
            "Can't read file content",
            new InputOf(new ReaderOf(temp.toUri())),
            new InputHasContent(content)
        );
    }

    @Test
    public void readsBytes() throws IOException {
        MatcherAssert.assertThat(
            "Can't read content",
            new InputOf(new ReaderOf(new BytesOf("Hello, товарищ!"))),
            new InputHasContent("Hello, товарищ!")
        );
    }

    @Test
    public void readsText() throws IOException {
        MatcherAssert.assertThat(
            "Can't read content",
            new InputOf(new ReaderOf(new TextOf("Hello, товарищ!"))),
            new InputHasContent("Hello, товарищ!")
        );
    }

    @Test
    public void readsTextWithCharset() throws IOException {
        MatcherAssert.assertThat(
            "Can't read content",
            new InputOf(
                new ReaderOf(
                    new TextOf("Hello, товарищ!"),
                    StandardCharsets.UTF_8
                )
            ),
            new InputHasContent("Hello, товарищ!")
        );
    }

    @Test
    public void readsTextWithCharsetByName() throws IOException {
        MatcherAssert.assertThat(
            "Can't read content",
            new InputOf(
                new ReaderOf(
                    new TextOf("Hello, товарищ!"),
                    StandardCharsets.UTF_8.name()
                )
            ),
            new InputHasContent("Hello, товарищ!")
        );
    }

    @Test
    public void readsCharSequence() throws IOException {
        MatcherAssert.assertThat(
            "Can't read content",
            new InputOf(new ReaderOf("Hello, товарищ!")),
            new InputHasContent("Hello, товарищ!")
        );
    }

    @Test
    public void readsCharSequenceWithCharset() throws IOException {
        MatcherAssert.assertThat(
            "Can't read content",
            new InputOf(
                new ReaderOf(
                    "Hello, товарищ!",
                    StandardCharsets.UTF_8
                )
            ),
            new InputHasContent("Hello, товарищ!")
        );
    }

    @Test
    public void readsCharSequenceWithCharsetByName() throws IOException {
        MatcherAssert.assertThat(
            "Can't read content",
            new InputOf(
                new ReaderOf(
                    "Hello, товарищ!",
                    StandardCharsets.UTF_8.name()
                )
            ),
            new InputHasContent("Hello, товарищ!")
        );
    }

    @Test
    public void readsInput() throws IOException {
        MatcherAssert.assertThat(
            "Can't read content",
            new InputOf(new ReaderOf(new InputOf("Hello, товарищ!"))),
            new InputHasContent("Hello, товарищ!")
        );
    }

    @Test
    public void readsInputWithCharset() throws IOException {
        MatcherAssert.assertThat(
            "Can't read content",
            new InputOf(
                new ReaderOf(
                    new InputOf("Hello, товарищ!"),
                    StandardCharsets.UTF_8
                )
            ),
            new InputHasContent("Hello, товарищ!")
        );
    }

    @Test
    public void readsInputWithCharsetByName() throws IOException {
        MatcherAssert.assertThat(
            "Can't read content",
            new InputOf(
                new ReaderOf(
                    new InputOf("Hello, товарищ!"),
                    StandardCharsets.UTF_8.name()
                )
            ),
            new InputHasContent("Hello, товарищ!")
        );
    }

    @Test
    public void readsInputWithCharsetDecoder() throws IOException {
        MatcherAssert.assertThat(
            "Can't read content",
            new InputOf(
                new ReaderOf(
                    new InputOf("Hello, товарищ!"),
                    StandardCharsets.UTF_8.newDecoder()
                )
            ),
            new InputHasContent("Hello, товарищ!")
        );
    }

    @Test
    public void readsInputStream() throws IOException {
        MatcherAssert.assertThat(
            "Can't read content",
            new InputOf(
                new ReaderOf(
                    new InputStreamOf("Hello, товарищ!")
                )
            ),
            new InputHasContent("Hello, товарищ!")
        );
    }

    @Test
    public void readsInputStreamWithCharset() throws IOException {
        MatcherAssert.assertThat(
            "Can't read content",
            new InputOf(
                new ReaderOf(
                    new InputStreamOf("Hello, товарищ!"),
                    StandardCharsets.UTF_8
                )
            ),
            new InputHasContent("Hello, товарищ!")
        );
    }

    @Test
    public void readsInputStreamWithCharsetByName() throws IOException {
        MatcherAssert.assertThat(
            "Can't read content",
            new InputOf(
                new ReaderOf(
                    new InputStreamOf("Hello, товарищ!"),
                    StandardCharsets.UTF_8.name()
                )
            ),
            new InputHasContent("Hello, товарищ!")
        );
    }

    @Test
    public void readsInputStreamWithCharsetDecoder() throws IOException {
        MatcherAssert.assertThat(
            "Can't read content",
            new InputOf(
                new ReaderOf(
                    new InputStreamOf("Hello, товарищ!"),
                    StandardCharsets.UTF_8.newDecoder()
                )
            ),
            new InputHasContent("Hello, товарищ!")
        );
    }
}
