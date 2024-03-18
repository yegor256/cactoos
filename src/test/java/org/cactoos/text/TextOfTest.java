/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2024 Yegor Bugayenko
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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import org.cactoos.io.InputOf;
import org.cactoos.iterable.IterableOfChars;
import org.cactoos.iterator.IteratorOfChars;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.EndsWith;
import org.llorllale.cactoos.matchers.HasString;
import org.llorllale.cactoos.matchers.IsText;
import org.llorllale.cactoos.matchers.StartsWith;

/**
 * Test case for {@link TextOf}.
 *
 * @since 0.12
 * @checkstyle JavadocMethodCheck (1000 lines)
 * @checkstyle StringLiteralsConcatenationCheck (1000 lines)
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.AvoidDuplicateLiterals", "unchecked"})
final class TextOfTest {

    @Test
    void readsInputIntoText() {
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void readsInputIntoTextWithDefaultCharset() {
        new Assertion<>(
            "Can't read text from Input with default charset",
            new TextOf(
                new InputOf("Hello, друг! with default charset")
            ),
            new AllOf<>(
                new StartsWith("Hello, "),
                new EndsWith("друг! with default charset")
            )
        ).affirm();
    }

    @Test
    void readsInputIntoTextWithSmallBuffer() {
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void readsReaderIntoTextWithSmallBuffer() {
        final String text = "Hi there! with small buffer";
        new Assertion<>(
            "Can't read text from Reader with a small reading buffer",
            new TextOf(
                new StringReader(text), 2, StandardCharsets.UTF_8
            ),
            new IsText(text)
        ).affirm();
    }

    @Test
    void readsInputIntoTextWithSmallBufferAndDefaultCharset() {
        new Assertion<>(
            "Can't read text with a small reading buffer and default charset",
            new TextOf(
                new InputOf("Hello, товарищ! with default charset"),
                2
            ),
            new AllOf<>(
                new StartsWith("Hello,"),
                new EndsWith("товарищ! with default charset")
            )
        ).affirm();
    }

    @Test
    void readsFromReader() {
        final String source = "hello, друг!";
        new Assertion<>(
            "Can't read string through a reader",
            new TextOf(
                new StringReader(source),
                StandardCharsets.UTF_8
            ),
            new IsText(source)
        ).affirm();
    }

    @Test
    void readsFromReaderWithDefaultEncoding() {
        final String source = "hello, друг! with default encoding";
        new Assertion<>(
            "Can't read string with default encoding through a reader",
            new TextOf(new StringReader(source)),
            new IsText(source)
        ).affirm();
    }

    @Test
    void readsEncodedArrayOfCharsIntoText() {
        new Assertion<>(
            "Can't read array of encoded chars into text.",
            new TextOf(
                'O', ' ', 'q', 'u', 'e', ' ', 's', 'e', 'r', 'a',
                ' ', 'q', 'u', 'e', ' ', 's', 'e', 'r', 'a'
            ),
            new AllOf<>(
                new StartsWith("O que sera"),
                new EndsWith(" que sera")
            )
        ).affirm();
    }

    @Test
    void readsAnArrayOfBytes() {
        final byte[] bytes = {(byte) 0xCA, (byte) 0xFE};
        new Assertion<>(
            "Can't read array of bytes",
            new TextOf(
                bytes
            ),
            new IsText(new String(bytes, StandardCharsets.UTF_8))
        ).affirm();
    }

    @Test
    void readsStringBuilder() {
        final String starts = "Name it, ";
        final String ends = "then it exists!";
        new Assertion<>(
            "Can't process a string builder",
            new TextOf(
                new StringBuilder(starts).append(ends)
            ),
            new AllOf<>(
                new StartsWith(starts),
                new EndsWith(ends)
            )
        ).affirm();
    }

    @Test
    void readsStringBuffer() {
        final String starts = "In our daily life, ";
        final String ends = "we can smile!";
        new Assertion<>(
            "Can't process a string builder hahahaha",
            new TextOf(
                new StringBuffer(starts).append(ends)
            ),
            new AllOf<>(
                new StartsWith(starts),
                new EndsWith(ends)
            )
        ).affirm();
    }

    @Test
    void printsStackTrace() {
        new Assertion<>(
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
        ).affirm();
    }

    @Test
    void readsFromInputStream() throws Exception {
        final String content = "line1";
        final InputStream stream = new ByteArrayInputStream(
            content.getBytes(StandardCharsets.UTF_8.name())
        );
        new Assertion<>(
            "Can't read inputStream",
            new TextOf(stream),
            new IsText(
                new String(content.getBytes(), StandardCharsets.UTF_8)
            )
        ).affirm();
    }

    @Test
    void readsMultilineInputStream() throws Exception {
        final String content = "line1-\nline2";
        final InputStream stream = new ByteArrayInputStream(
            content.getBytes(StandardCharsets.UTF_8.name())
        );
        new Assertion<>(
            "Can't read multiline inputStream",
            new TextOf(stream),
            new IsText(content)
        ).affirm();
    }

    @Test
    void readsMultilineInputStreamWithCarriageReturn() throws Exception {
        final String content = "line1-\rline2";
        final InputStream stream = new ByteArrayInputStream(
            content.getBytes(StandardCharsets.UTF_8.name())
        );
        new Assertion<>(
            "Can't read multiline inputStream with carriage return",
            new TextOf(stream),
            new IsText(content)
        ).affirm();
    }

    @Test
    void readsClosedInputStream() throws Exception {
        final String content = "content";
        final InputStream stream = new ByteArrayInputStream(
            content.getBytes(StandardCharsets.UTF_8.name())
        );
        stream.close();
        new Assertion<>(
            "Can't read closed input stream",
            new TextOf(stream),
            new IsText(content)
        ).affirm();
    }

    @Test
    void readsEmptyInputStream() throws Exception {
        final String content = "";
        final InputStream stream = new ByteArrayInputStream(
            content.getBytes(StandardCharsets.UTF_8.name())
        );
        new Assertion<>(
            "Can't read empty input stream",
            new TextOf(stream),
            new IsText(content)
        ).affirm();
    }

    @Test
    void printsStackTraceFromArray() {
        new Assertion<>(
            "Can't print exception stacktrace from array",
            new TextOf(
                new IOException("").getStackTrace()
            ),
            new HasString("org.cactoos.text.TextOfTest")
        ).affirm();
    }

    @Test
    void readsIterableToText() {
        new Assertion<>(
            "Must read Iterable to Text",
            new TextOf(
                new IterableOfChars("hello")
            ),
            new IsText("hello")
        ).affirm();
    }

    @Test
    void readsIteratorToText() {
        new Assertion<>(
            "Must read Iterator to Text",
            new TextOf(
                new IteratorOfChars("qwer")
            ),
            new IsText("qwer")
        ).affirm();
    }

    /**
     * Test for {@link TextEnvelope#equals(Object)} method. Must assert
     * that the envelope value is equal another text representing the same
     * value.
     */
    @Test
    void testEquals() {
        final String text = "equals";
        new Assertion<>(
            "Must match text representing the same value",
            new TextOf(text),
            new IsEqual<>(new TextOf(text))
        ).affirm();
    }

    /**
     * Test for {@link TextEnvelope#equals(Object)} method. Must assert
     * that the envelope value is equal another text representing the same
     * value (in this case a {@link Joined}).
     */
    @Test
    void testEqualsOtherText() {
        new Assertion<>(
            "Must match another text representing the same value",
            new TextOf("isequaltoanothertext"),
            new IsEqual<>(
                new Concatenated("is", "equal", "to", "another", "text")
            )
        ).affirm();
    }

    /**
     * Test for {@link TextEnvelope#equals(Object)} method. Must assert
     * that the envelope value is not equal another object not being a
     * instance of Text without failing
     */
    @Test
    void testDoesNotEqualsNonTextObject() {
        new Assertion<>(
            "Must match another object which is not a string",
            new TextOf("is not equals to null"),
            new IsNot<>(
                new IsEqual<>(new Object())
            )
        ).affirm();
    }

    /**
     * Test for {@link TextEnvelope#equals(Object)} method. Must assert
     * that the envelope value is not equal to null without failing
     */
    @Test
    @SuppressWarnings("PMD.EqualsNull")
    void testDoesNotEqualsFalse() {
        new Assertion<>(
            "Must not equals null",
            new TextOf("is not equals to not Text object"),
            new IsNot<>(new IsNull<>())
        ).affirm();
    }

    /**
     * Test for {@link TextEnvelope#hashCode()} method. Must assert that
     * the {@link TextEnvelope} hashCode is equals to the hashCode of
     * the String it represents.
     */
    @Test
    void testHashCode() {
        final String hash = "hashCode";
        new Assertion<>(
            "Must match its represented String hashcode",
            new TextOf(hash).hashCode(),
            new IsEqual<>(hash.hashCode())
        ).affirm();
    }
}
