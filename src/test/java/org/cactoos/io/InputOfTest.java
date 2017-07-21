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
package org.cactoos.io;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;
import org.cactoos.InputHasContent;
import org.cactoos.TextHasString;
import org.cactoos.func.MatcherOf;
import org.cactoos.func.UncheckedScalar;
import org.cactoos.text.BytesAsText;
import org.cactoos.text.StringAsText;
import org.cactoos.text.TextAsBytes;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.takes.http.FtRemote;
import org.takes.tk.TkHtml;

/**
 * Test case for {@link InputOf}.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @author Ix (ixmanuel@yahoo.com)
 * @version $Id$
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class InputOfTest {

    @Test
    public void readsAlternativeInputForFileCase() throws IOException {
        MatcherAssert.assertThat(
            "Can't read alternative source from file not found",
            new BytesAsText(
                new InputAsBytes(
                    new InputWithFallback(
                        new InputOf(
                            new File("/this-file-does-not-exist.txt")
                        ),
                        new InputOf(new StringAsText("Alternative text!"))
                    )
                )
            ),
            new TextHasString(Matchers.endsWith("text!"))
        );
    }

    @Test
    public void readsAlternativeInputForCheckedCase() {
        MatcherAssert.assertThat(
            "Can't read alternative source for checked case.",
            new BytesAsText(
                new InputAsBytes(
                    new InputWithFallback(
                        new InputOf(
                            () -> new File("/absent-file-for-checked-case.txt")
                        ),
                        new InputOf(new StringAsText("hello, checked!"))
                    )
                )
            ),
            new TextHasString(Matchers.endsWith("checked!"))
        );
    }

    @Test
    public void readsAlternativeInputForUncheckedCase() {
        MatcherAssert.assertThat(
            "Can't read alternative source for unchecked case.",
            new BytesAsText(
                new InputAsBytes(
                    new InputWithFallback(
                        new InputOf(
                            new UncheckedScalar<File>(
                                () -> new File(
                                    "/absent-file-for-unchecked-case.txt"
                                )
                            )
                        ),
                        new InputOf(new StringAsText("hello, unchecked!"))
                    )
                )
            ),
            new TextHasString(Matchers.endsWith("unchecked!"))
        );
    }

    @Test
    public void readsSimpleFileContent() throws IOException {
        final Path temp = Files.createTempFile("cactoos-1", "txt-1");
        final String content = "Hello, товарищ!";
        Files.write(temp, content.getBytes(StandardCharsets.UTF_8));
        MatcherAssert.assertThat(
            "Can't read file content",
            new InputOf(temp),
            new InputHasContent(content)
        );
    }

    @Test
    public void closesInputStream() throws IOException {
        final AtomicBoolean closed = new AtomicBoolean();
        final InputStream input = new ByteArrayInputStream(
            "how are you?".getBytes()
        );
        MatcherAssert.assertThat(
            "Can't close InputStream correctly",
            new BytesAsText(
                new InputAsBytes(
                    new InputOf(
                        new InputStream() {
                            @Override
                            public int read() throws IOException {
                                return input.read();
                            }
                            @Override
                            public void close() throws IOException {
                                input.close();
                                closed.set(true);
                            }
                        }
                    )
                ).asBytes(),
                StandardCharsets.UTF_8
            ).asString(),
            new MatcherOf<>(
                text -> {
                    return closed.get();
                }
            )
        );
    }

    @Test
    public void readsFileContent() throws IOException {
        MatcherAssert.assertThat(
            "Can't read bytes from a file-system URL",
            new InputAsBytes(
                new InputOf(
                    this.getClass().getResource(
                        "/org/cactoos/io/InputOf.class"
                    )
                )
            ).asBytes().length,
            Matchers.greaterThan(0)
        );
    }

    @Test
    public void readsRealUrl() throws IOException {
        new FtRemote(new TkHtml("<html>How are you?</html>")).exec(
            home -> MatcherAssert.assertThat(
                "Can't fetch bytes from the URL",
                new BytesAsText(
                    new InputAsBytes(
                        new InputOf(home)
                    )
                ),
                new TextHasString(
                    Matchers.allOf(
                        Matchers.startsWith("<html"),
                        Matchers.endsWith("html>")
                    )
                )
            )
        );
    }

    @Test
    public void readsStringUrl() throws IOException {
        MatcherAssert.assertThat(
            "Can't fetch bytes from the HTTPS URL",
            new BytesAsText(
                new InputAsBytes(
                    new InputOf(
                        new URL(
                            // @checkstyle LineLength (1 line)
                            "file:src/test/resources/org/cactoos/large-text.txt"
                        )
                    )
                )
            ),
            new TextHasString(Matchers.containsString("Lorem ipsum"))
        );
    }

    @Test
    public void readsInputIntoBytes() throws IOException {
        MatcherAssert.assertThat(
            "Can't read bytes from Input",
            new String(
                new InputAsBytes(
                    new InputOf(
                        new TextAsBytes(
                            new StringAsText("Hello, друг!")
                        )
                    )
                ).asBytes(),
                StandardCharsets.UTF_8
            ),
            Matchers.allOf(
                Matchers.startsWith("Hello, "),
                Matchers.endsWith("друг!")
            )
        );
    }

    @Test
    public void readsStringBuilder() throws IOException {
        MatcherAssert.assertThat(
            "Can't receive string builder",
            new String(
                new InputAsBytes(
                    new InputOf(
                        new StringBuilder("Name it, then it exists!")
                    )
                ).asBytes()
            ),
            Matchers.allOf(
                Matchers.startsWith("Name it, "),
                Matchers.endsWith("then it exists!")
            )
        );
    }

    @Test
    public void readsStringBuffer() throws IOException {
        MatcherAssert.assertThat(
            "Can't receive string buffer",
            new String(
                new InputAsBytes(
                    new InputOf(
                        new StringBuffer("The future is now!")
                    )
                ).asBytes()
            ),
            Matchers.allOf(
                Matchers.startsWith("The future "),
                Matchers.endsWith("is now!")
            )
        );
    }

    @Test
    public void readsArrayOfChars() throws IOException {
        MatcherAssert.assertThat(
            "Can't read array of chars.",
            new String(
                new InputAsBytes(
                    new InputOf(
                        new char[]{
                            'H', 'o', 'l', 'd', ' ',
                            'i', 'n', 'f', 'i', 'n', 'i', 't', 'y',
                        }
                    )
                ).asBytes()
            ),
            Matchers.allOf(
                Matchers.startsWith("Hold "),
                Matchers.endsWith("infinity")
            )
        );
    }

    @Test
    public void readsEncodedArrayOfChars() throws IOException {
        MatcherAssert.assertThat(
            "Can't read array of encoded chars.",
            new String(
                new InputAsBytes(
                    new InputOf(
                        new char[]{
                            'O', ' ', 'q', 'u', 'e', ' ', 's', 'e', 'r', 'a',
                            ' ', 'q', 'u', 'e', ' ', 's', 'e', 'r', 'a',
                        }
                    )
                ).asBytes(),
                StandardCharsets.UTF_8
            ),
            Matchers.allOf(
                Matchers.startsWith("O que sera"),
                Matchers.endsWith(" que sera")
            )
        );
    }

    @Test
    public void readsStringFromReader() throws Exception {
        final String source = "hello, друг!";
        MatcherAssert.assertThat(
            "Can't read string through a reader",
            new BytesAsText(
                new InputAsBytes(
                    new InputOf(
                        new StringReader(source)
                    )
                )
            ).asString(),
            Matchers.equalTo(source)
        );
    }

}
