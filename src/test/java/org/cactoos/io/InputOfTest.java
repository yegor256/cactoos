/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;
import org.cactoos.bytes.BytesOf;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.llorllale.cactoos.matchers.EndsWith;
import org.llorllale.cactoos.matchers.HasContent;
import org.llorllale.cactoos.matchers.HasString;
import org.llorllale.cactoos.matchers.IsText;
import org.llorllale.cactoos.matchers.IsTrue;
import org.llorllale.cactoos.matchers.MatchesRegex;
import org.llorllale.cactoos.matchers.Satisfies;
import org.llorllale.cactoos.matchers.StartsWith;
import org.takes.http.FtRemote;
import org.takes.tk.TkHtml;

/**
 * Test case for {@link InputOf}.
 *
 * @since 0.1
 */
@SuppressWarnings({"unchecked", "PMD.TooManyMethods"})
final class InputOfTest {
    @Test
    void readsAlternativeInputForFileCase() {
        MatcherAssert.assertThat(
            "must read alternative source from file not found",
            new TextOf(
                new InputWithFallback(
                    new InputOf(
                        new File("/this-file-does-not-exist.txt")
                    ),
                    new InputOf(new TextOf("Alternative text!"))
                )
            ),
            new EndsWith("text!")
        );
    }

    @Test
    void readsSimpleFileContent(final @TempDir Path folder) throws IOException {
        final Path temp = folder.resolve("cactoos-1.txt-1");
        final String content = "Hello, товарищ!";
        Files.write(temp, content.getBytes(StandardCharsets.UTF_8));
        MatcherAssert.assertThat(
            "must read file content",
            new InputOf(temp),
            new HasContent(content)
        );
    }

    @Test
    void closesInputStream() throws Exception {
        final AtomicBoolean closed = new AtomicBoolean();
        final InputStream input = new ByteArrayInputStream(
            "how are you?".getBytes(StandardCharsets.UTF_8)
        );
        new TextOf(
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
        ).asString();
        MatcherAssert.assertThat(
            "must close InputStream correctly",
            closed.get(),
            new IsTrue()
        );
    }

    @Test
    void readsFileContent() throws Exception {
        MatcherAssert.assertThat(
            "must read bytes from a file-system URL",
            new BytesOf(
                new InputOf(
                    this.getClass().getResource(
                        "/org/cactoos/io/InputOf.class"
                    )
                )
            ).asBytes(),
            new Satisfies<>(arr -> arr.length > 0)
        );
    }

    @Test
    void readsRealUrl() throws Exception {
        final AtomicBoolean done = new AtomicBoolean(false);
        new FtRemote(new TkHtml("<html>How are you?</html>")).exec(
            home -> {
                MatcherAssert.assertThat(
                    "must fetch bytes from the URL",
                    new TextOf(new InputOf(home)),
                    new MatchesRegex("<html.*html>")
                );
                done.set(true);
            }
        );
        MatcherAssert.assertThat(
            "Callback must be executed",
            done.get(),
            new IsEqual<>(true)
        );
    }

    @Test
    void readsRealUrlWithProxy() throws Exception {
        final AtomicBoolean done = new AtomicBoolean(false);
        new FtRemote(new TkHtml("<html>Proxy works!</html>")).exec(
            home -> {
                MatcherAssert.assertThat(
                    "must fetch bytes from the URL via a proxy",
                    new TextOf(new InputOf(home.toURL(), Proxy.NO_PROXY)),
                    new MatchesRegex("<html.*html>")
                );
                done.set(true);
            }
        );
        MatcherAssert.assertThat(
            "Callback must be executed for proxy",
            done.get(),
            new IsEqual<>(true)
        );
    }

    @Test
    void readsStringUrl() throws IOException, URISyntaxException {
        MatcherAssert.assertThat(
            "must fetch bytes from the HTTPS URL",
            new TextOf(
                new BytesOf(
                    new InputOf(
                        new URI(
                            "file:src/test/resources/org/cactoos/large-text.txt"
                        ).toURL()
                    )
                )
            ),
            new HasString("Lorem ipsum")
        );
    }

    @Test
    void readsStringIntoBytes() {
        MatcherAssert.assertThat(
            "must read bytes from Input",
            new TextOf(
                new BytesOf(
                    new InputOf("Hello, друг!")
                ),
                StandardCharsets.UTF_8
            ),
            new AllOf<>(
                new StartsWith("Hello, "),
                new EndsWith("друг!")
            )
        );
    }

    @Test
    void readsStringBuilder() {
        final String starts = "Name it, ";
        final String ends = "then it exists!";
        MatcherAssert.assertThat(
            "must receive a string builder",
            new TextOf(
                new BytesOf(
                    new InputOf(
                        new StringBuilder(starts)
                            .append(ends)
                    )
                )
            ),
            new AllOf<>(
                new StartsWith(starts),
                new EndsWith(ends)
            )
        );
    }

    @Test
    void readsStringBuffer() {
        final String starts = "The future ";
        final String ends = "is now!";
        MatcherAssert.assertThat(
            "must receive a string buffer",
            new TextOf(
                new BytesOf(
                    new InputOf(
                        new StringBuffer(starts)
                            .append(ends)
                    )
                )
            ),
            new AllOf<>(
                new StartsWith(starts),
                new EndsWith(ends)
            )
        );
    }

    @Test
    void readsArrayOfChars() {
        MatcherAssert.assertThat(
            "must read array of chars.",
            new TextOf(
                new BytesOf(
                    new InputOf(
                        'H', 'o', 'l', 'd', ' ',
                        'i', 'n', 'f', 'i', 'n', 'i', 't', 'y'
                    )
                )
            ),
            new AllOf<>(
                new StartsWith("Hold "),
                new EndsWith("infinity")
            )
        );
    }

    @Test
    void readsEncodedArrayOfChars() {
        MatcherAssert.assertThat(
            "must read array of encoded chars.",
            new TextOf(
                new BytesOf(
                    new InputOf(
                        new char[]{
                            'O', ' ', 'q', 'u', 'e', ' ', 's', 'e', 'r', 'a',
                            ' ', 'q', 'u', 'e', ' ', 's', 'e', 'r', 'a',
                        },
                        StandardCharsets.UTF_8
                    )
                ),
                StandardCharsets.UTF_8
            ),
            new AllOf<>(
                new StartsWith("O que sera"),
                new EndsWith(" que sera")
            )
        );
    }

    @Test
    void readsStringFromReader() {
        final String source = "hello, source!";
        MatcherAssert.assertThat(
            "must read string through a reader",
            new TextOf(
                new InputOf(
                    new StringReader(source)
                )
            ),
            new IsText(source)
        );
    }

    @Test
    void readsEncodedStringFromReader() {
        final String source = "hello, друг!";
        MatcherAssert.assertThat(
            "must read encoded string through a reader",
            new TextOf(
                new BytesOf(
                    new InputOf(
                        new StringReader(source),
                        StandardCharsets.UTF_8
                    )
                )
            ),
            new IsText(source)
        );
    }

    @Test
    void readsAnArrayOfBytes() throws Exception {
        final byte[] bytes = {(byte) 0xCA, (byte) 0xFE};
        MatcherAssert.assertThat(
            "must read array of bytes",
            new BytesOf(
                new SyncInput(new InputOf(bytes))
            ).asBytes(),
            new IsEqual<>(bytes)
        );
    }

    @Test
    void makesDataAvailable() throws Exception {
        MatcherAssert.assertThat(
            "must show that data is available",
            new InputOf("Hello,חבר!").stream(),
            new Satisfies<>(s -> s.available() > 0)
        );
    }
}
