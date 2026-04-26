/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import org.cactoos.io.InputOf;
import org.cactoos.iterable.IterableOfChars;
import org.cactoos.iterator.IteratorOfChars;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.EndsWith;
import org.llorllale.cactoos.matchers.HasString;
import org.llorllale.cactoos.matchers.IsText;
import org.llorllale.cactoos.matchers.StartsWith;

/**
 * Test case for {@link TextOf}.
 * @since 0.12
 * @checkstyle JavadocMethodCheck (1000 lines)
 * @checkstyle StringLiteralsConcatenationCheck (1000 lines)
 */
@SuppressWarnings({"unchecked", "PMD.TooManyMethods"})
final class TextOfTest {

    @Test
    void readsInputIntoText() {
        MatcherAssert.assertThat(
            "Can't read text from Input",
            new Synced(
                new TextOf(
                    new InputOf("привет, друг!"),
                    StandardCharsets.UTF_8
                )
            ),
            new AllOf<>(
                new StartsWith("привет, "),
                new EndsWith("друг!")
            )
        );
    }

    @Test
    void readsInputIntoTextWithDefaultCharset() {
        MatcherAssert.assertThat(
            "Can't read text from Input with default charset",
            new TextOf(
                new InputOf("Hello, друг! with default charset")
            ),
            new AllOf<>(
                new StartsWith("Hello, "),
                new EndsWith("друг! with default charset")
            )
        );
    }

    @Test
    void readsInputIntoTextWithSmallBuffer() {
        MatcherAssert.assertThat(
            "Can't read text with a small reading buffer",
            new TextOf(
                new InputOf("Hi, товарищ! with small buffer"),
                2,
                StandardCharsets.UTF_8
            ),
            new AllOf<>(
                new StartsWith("Hi,"),
                new EndsWith("товарищ! with small buffer")
            )
        );
    }

    @Test
    void readsReaderIntoTextWithSmallBuffer() {
        MatcherAssert.assertThat(
            "Can't read text from Reader with a small reading buffer",
            new TextOf(
                new StringReader("Hi there! with small buffer"), 2, StandardCharsets.UTF_8
            ),
            new IsText("Hi there! with small buffer")
        );
    }

    @Test
    void readsInputIntoTextWithSmallBufferAndDefaultCharset() {
        MatcherAssert.assertThat(
            "Can't read text with a small reading buffer and default charset",
            new TextOf(
                new InputOf("Hello, товарищ! with default charset"),
                2
            ),
            new AllOf<>(
                new StartsWith("Hello,"),
                new EndsWith("товарищ! with default charset")
            )
        );
    }

    @Test
    void readsFromReader() {
        MatcherAssert.assertThat(
            "Can't read string through a reader",
            new TextOf(
                new StringReader("hello, друг!"),
                StandardCharsets.UTF_8
            ),
            new IsText("hello, друг!")
        );
    }

    @Test
    void readsFromReaderWithDefaultEncoding() {
        MatcherAssert.assertThat(
            "Can't read string with default encoding through a reader",
            new TextOf(new StringReader("hello, друг! with default encoding")),
            new IsText("hello, друг! with default encoding")
        );
    }

    @Test
    void readsEncodedArrayOfCharsIntoText() {
        MatcherAssert.assertThat(
            "Can't read array of encoded chars into text.",
            new TextOf(
                'O', ' ', 'q', 'u', 'e', ' ', 's', 'e', 'r', 'a',
                ' ', 'q', 'u', 'e', ' ', 's', 'e', 'r', 'a'
            ),
            new AllOf<>(
                new StartsWith("O que sera"),
                new EndsWith(" que sera")
            )
        );
    }

    @Test
    void readsAnArrayOfBytes() {
        final byte[] bytes = {(byte) 0xCA, (byte) 0xFE};
        MatcherAssert.assertThat(
            "Can't read array of bytes",
            new TextOf(
                bytes
            ),
            new IsText(new String(bytes, StandardCharsets.UTF_8))
        );
    }

    @Test
    void readsStringBuilder() {
        final String starts = "Name it, ";
        final String ends = "then it exists!";
        MatcherAssert.assertThat(
            "Can't process a string builder",
            new TextOf(
                new StringBuilder(starts).append(ends)
            ),
            new AllOf<>(
                new StartsWith(starts),
                new EndsWith(ends)
            )
        );
    }

    @Test
    void readsStringBuffer() {
        final String starts = "In our daily life, ";
        final String ends = "we can smile!";
        MatcherAssert.assertThat(
            "Can't process a string builder hahahaha",
            new TextOf(
                new StringBuffer(starts).append(ends)
            ),
            new AllOf<>(
                new StartsWith(starts),
                new EndsWith(ends)
            )
        );
    }

    @Test
    void printsStackTrace() {
        MatcherAssert.assertThat(
            "Can't print exception stacktrace",
            new TextOf(
                new IOException(
                    "It doesn't work at all"
                )
            ),
            new HasString(
                new Joined(
                    System.lineSeparator(),
                    "java.io.IOException: It doesn't work at all",
                    "\tat org.cactoos.text.TextOfTest"
                )
            )
        );
    }

    @Test
    void readsFromInputStream() throws Exception {
        MatcherAssert.assertThat(
            "Can't read inputStream",
            new TextOf(
                new ByteArrayInputStream(
                    "line1".getBytes(StandardCharsets.UTF_8.name())
                )
            ),
            new IsText(
                new String("line1".getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8)
            )
        );
    }

    @Test
    void readsMultilineInputStream() throws Exception {
        MatcherAssert.assertThat(
            "Can't read multiline inputStream",
            new TextOf(
                new ByteArrayInputStream(
                    String.format("line1-%cline2", (char) 10)
                        .getBytes(StandardCharsets.UTF_8.name())
                )
            ),
            new IsText(String.format("line1-%cline2", (char) 10))
        );
    }

    @Test
    void readsMultilineInputStreamWithCarriageReturn() throws Exception {
        MatcherAssert.assertThat(
            "Can't read multiline inputStream with carriage return",
            new TextOf(
                new ByteArrayInputStream(
                    String.format("line1-%cline2", (char) 13)
                        .getBytes(StandardCharsets.UTF_8.name())
                )
            ),
            new IsText(String.format("line1-%cline2", (char) 13))
        );
    }

    @Test
    void readsClosedInputStream() throws Exception {
        final String content = "content";
        final InputStream stream = new ByteArrayInputStream(
            content.getBytes(StandardCharsets.UTF_8.name())
        );
        stream.close();
        MatcherAssert.assertThat(
            "Can't read closed input stream",
            new TextOf(stream),
            new IsText(content)
        );
    }

    @Test
    void readsEmptyInputStream() throws Exception {
        MatcherAssert.assertThat(
            "Can't read empty input stream",
            new TextOf(
                new ByteArrayInputStream(
                    "".getBytes(StandardCharsets.UTF_8.name())
                )
            ),
            new IsText("")
        );
    }

    @Test
    void printsStackTraceFromArray() {
        MatcherAssert.assertThat(
            "Can't print exception stacktrace from array",
            new TextOf(
                new IOException("").getStackTrace()
            ),
            new HasString("org.cactoos.text.TextOfTest")
        );
    }

    @Test
    void readsIterableToText() {
        MatcherAssert.assertThat(
            "Must read Iterable to Text",
            new TextOf(
                new IterableOfChars("hello")
            ),
            new IsText("hello")
        );
    }

    @Test
    void readsIteratorToText() {
        MatcherAssert.assertThat(
            "Must read Iterator to Text",
            new TextOf(
                new IteratorOfChars("qwer")
            ),
            new IsText("qwer")
        );
    }

    /**
     * Test for {@link TextEnvelope#equals(Object)} method. Must assert
     * that the envelope value is equal another text representing the same
     * value.
     */
    @Test
    void equalsIfSameValue() {
        final String text = "equals";
        MatcherAssert.assertThat(
            "Must match text representing the same value",
            new TextOf(text),
            new IsEqual<>(new TextOf(text))
        );
    }

    /**
     * Test for {@link TextEnvelope#equals(Object)} method. Must assert
     * that the envelope value is equal another text representing the same
     * value (in this case a {@link Joined}).
     */
    @Test
    void equalsToOtherText() {
        MatcherAssert.assertThat(
            "Must match another text representing the same value",
            new TextOf("isequaltoanothertext"),
            new IsEqual<>(
                new Concatenated("is", "equal", "to", "another", "text")
            )
        );
    }

    /**
     * Test for {@link TextEnvelope#equals(Object)} method. Must assert
     * that the envelope value is not equal another object not being a
     * instance of Text without failing
     */
    @Test
    void doesntEqualNonTextObject() {
        MatcherAssert.assertThat(
            "Must match another object which is not a string",
            new TextOf("is not equals to null"),
            new IsNot<>(
                new IsEqual<>(new Object())
            )
        );
    }

    /**
     * Test for {@link TextEnvelope#equals(Object)} method. Must assert
     * that the envelope value is not equal to null without failing
     */
    @Test
    void doesntEqualNull() {
        MatcherAssert.assertThat(
            "Must not equals null",
            new TextOf("is not equals to not Text object"),
            new IsNot<>(new IsNull<>())
        );
    }

    /**
     * Test for {@link TextEnvelope#hashCode()} method. Must assert that
     * the {@link TextEnvelope} hashCode is equals to the hashCode of
     * the String it represents.
     */
    @Test
    void hashesAsString() {
        final String hash = "hashCode";
        MatcherAssert.assertThat(
            "Must match its represented String hashcode",
            new TextOf(hash).hashCode(),
            new IsEqual<>(hash.hashCode())
        );
    }
}
