/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
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
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import org.cactoos.io.BytesOf;
import org.cactoos.io.InputOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsBlank;
import org.llorllale.cactoos.matchers.TextHasString;
import org.llorllale.cactoos.matchers.TextIs;

/**
 * Test case for {@link TextOf}.
 *
 * @since 0.12
 * @checkstyle JavadocMethodCheck (1000 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (1000 lines)
 * @checkstyle MagicNumberCheck (1000 lines)
 * @checkstyle StringLiteralsConcatenationCheck (1000 lines)
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.AvoidDuplicateLiterals"})
public final class TextOfTest {

    @Test
    public void readsInputIntoText() throws Exception {
        MatcherAssert.assertThat(
            "Can't read text from Input",
            new Synced(
                new TextOf(
                    new InputOf("привет, друг!"),
                    StandardCharsets.UTF_8
                )
            ).asString(),
            Matchers.allOf(
                Matchers.startsWith("привет, "),
                Matchers.endsWith("друг!")
            )
        );
    }

    @Test
    public void readsInputIntoTextWithDefaultCharset() throws Exception {
        MatcherAssert.assertThat(
            "Can't read text from Input with default charset",
            new TextOf(
                new InputOf("Hello, друг! with default charset")
            ).asString(),
            Matchers.allOf(
                Matchers.startsWith("Hello, "),
                Matchers.endsWith("друг! with default charset")
            )
        );
    }

    @Test
    public void readsInputIntoTextWithSmallBuffer() throws Exception {
        MatcherAssert.assertThat(
            "Can't read text with a small reading buffer",
            new TextOf(
                new InputOf("Hi, товарищ! with small buffer"),
                2,
                StandardCharsets.UTF_8
            ).asString(),
            Matchers.allOf(
                Matchers.startsWith("Hi,"),
                Matchers.endsWith("товарищ! with small buffer")
            )
        );
    }

    @Test
    public void readsReaderIntoTextWithSmallBuffer() {
        final String text = "Hi there! with small buffer";
        new Assertion<>(
            "Can't read text from Reader with a small reading buffer",
            new TextOf(text),
            new TextIs(
                new TextOf(
                    new StringReader(text), 2, StandardCharsets.UTF_8
                )
            )
        ).affirm();
    }

    @Test
    public void readsInputIntoTextWithSmallBufferAndDefaultCharset()
        throws Exception {
        MatcherAssert.assertThat(
            "Can't read text with a small reading buffer and default charset",
            new TextOf(
                new InputOf("Hello, товарищ! with default charset"),
                2
            ).asString(),
            Matchers.allOf(
                Matchers.startsWith("Hello,"),
                Matchers.endsWith("товарищ! with default charset")
            )
        );
    }

    @Test
    public void readsFromReader() throws Exception {
        final String source = "hello, друг!";
        MatcherAssert.assertThat(
            "Can't read string through a reader",
            new TextOf(
                new StringReader(source),
                StandardCharsets.UTF_8
            ).asString(),
            Matchers.equalTo(
                new String(
                    new BytesOf(source).asBytes(),
                    StandardCharsets.UTF_8
                )
            )
        );
    }

    @Test
    public void readsFromReaderWithDefaultEncoding() throws Exception {
        final String source = "hello, друг! with default encoding";
        MatcherAssert.assertThat(
            "Can't read string with default encoding through a reader",
            new TextOf(new StringReader(source)).asString(),
            Matchers.equalTo(
                new String(
                    new BytesOf(source).asBytes(),
                    StandardCharsets.UTF_8
                )
            )
        );
    }

    @Test
    public void readsEncodedArrayOfCharsIntoText() throws Exception {
        MatcherAssert.assertThat(
            "Can't read array of encoded chars into text.",
            new TextOf(
                'O', ' ', 'q', 'u', 'e', ' ', 's', 'e', 'r', 'a',
                ' ', 'q', 'u', 'e', ' ', 's', 'e', 'r', 'a'
            ).asString(),
            Matchers.allOf(
                Matchers.startsWith("O que sera"),
                Matchers.endsWith(" que sera")
            )
        );
    }

    @Test
    public void readsAnArrayOfBytes() throws Exception {
        final byte[] bytes = new byte[] {(byte) 0xCA, (byte) 0xFE};
        MatcherAssert.assertThat(
            "Can't read array of bytes",
            new TextOf(
                bytes
            ).asString(),
            Matchers.equalTo(new String(bytes, StandardCharsets.UTF_8))
        );
    }

    @Test
    public void readsStringBuilder() throws Exception {
        final String starts = "Name it, ";
        final String ends = "then it exists!";
        MatcherAssert.assertThat(
            "Can't process a string builder",
            new TextOf(
                new StringBuilder(starts).append(ends)
            ).asString(),
            Matchers.allOf(
                Matchers.startsWith(starts),
                Matchers.endsWith(ends)
            )
        );
    }

    @Test
    public void readsStringBuffer() throws Exception {
        final String starts = "In our daily life, ";
        final String ends = "we can smile!";
        MatcherAssert.assertThat(
            "Can't process a string builder hahahaha",
            new TextOf(
                new StringBuffer(starts).append(ends)
            ).asString(),
            Matchers.allOf(
                Matchers.startsWith(starts),
                Matchers.endsWith(ends)
            )
        );
    }

    @Test
    public void printsStackTrace() {
        MatcherAssert.assertThat(
            "Can't print exception stacktrace",
            new TextOf(
                new IOException(
                    "It doesn't work at all"
                )
            ),
            new TextHasString(
                new Joined(
                    System.lineSeparator(),
                    "java.io.IOException: It doesn't work at all",
                    "\tat org.cactoos.text.TextOfTest"
                )
            )
        );
    }

    @Test
    public void readsFromInputStream() throws Exception {
        final String content = "line1";
        final InputStream stream = new ByteArrayInputStream(
            content.getBytes(StandardCharsets.UTF_8.name())
        );
        MatcherAssert.assertThat(
            "Can't read inputStream",
            new TextOf(stream).asString(),
            Matchers.equalTo(
                new String(content.getBytes(), StandardCharsets.UTF_8)
            )
        );
    }

    @Test
    public void readsMultilineInputStream() throws Exception {
        final String content = "line1-\nline2";
        final InputStream stream = new ByteArrayInputStream(
            content.getBytes(StandardCharsets.UTF_8.name())
        );
        MatcherAssert.assertThat(
            "Can't read multiline inputStream",
            new TextOf(stream).asString(),
            Matchers.equalTo(content)
        );
    }

    @Test
    public void readsMultilineInputStreamWithCarriageReturn() throws Exception {
        final String content = "line1-\rline2";
        final InputStream stream = new ByteArrayInputStream(
            content.getBytes(StandardCharsets.UTF_8.name())
        );
        MatcherAssert.assertThat(
            "Can't read multiline inputStream with carriage return",
            new TextOf(stream).asString(),
            Matchers.equalTo(content)
        );
    }

    @Test
    public void readsClosedInputStream() throws Exception {
        final String content = "content";
        final InputStream stream = new ByteArrayInputStream(
            content.getBytes(StandardCharsets.UTF_8.name())
        );
        stream.close();
        MatcherAssert.assertThat(
            "Can't read closed input stream",
            new TextOf(stream).asString(),
            Matchers.equalTo(content)
        );
    }

    @Test
    public void readsEmptyInputStream() throws Exception {
        final String content = "";
        final InputStream stream = new ByteArrayInputStream(
            content.getBytes(StandardCharsets.UTF_8.name())
        );
        MatcherAssert.assertThat(
            "Can't read empty input stream",
            new TextOf(stream).asString(),
            Matchers.equalTo(content)
        );
    }

    @Test
    public void printsStackTraceFromArray() {
        MatcherAssert.assertThat(
            "Can't print exception stacktrace from array",
            new TextOf(
                new IOException("").getStackTrace()
            ),
            new TextHasString("org.cactoos.text.TextOfTest")
        );
    }

    @Test
    public void readsLocalDateFormattedWithFormatString() {
        final LocalDate date = LocalDate.of(2017, 12, 13);
        new Assertion<>(
            "Can't format a LocalDate with format.",
            new TextOf(date, "yyyy-MM-dd HH:mm:ss"),
            new TextIs("2017-12-13 00:00:00")
        ).affirm();
    }

    @Test
    public void readsLocalDateFormattedWithFormatStringWithLocale() {
        final LocalDate date = LocalDate.of(2017, 12, 13);
        new Assertion<>(
            "Can't format a LocalDate with format using locale.",
            new TextOf(
                date, "yyyy MMM dd. HH.mm.ss", Locale.FRENCH
            ),
            new TextIs("2017 déc. 13. 00.00.00")
        ).affirm();
    }

    @Test
    public void readsLocalDateFormattedAsIsoDateTime() throws IOException {
        final LocalDate date = LocalDate.of(2017, 12, 13);
        new Assertion<>(
            "Can't format a LocalDate with default/ISO format.",
            new TextOf(date),
            new TextIs(
                MessageFormat.format(
                "2017-12-13T00:00:00{0}",
                date.atTime(LocalTime.MIN).atZone(ZoneId.systemDefault())
                    .getOffset().toString()
                )
            )
        ).affirm();
    }

    @Test
    public void readsCurrentLocalDateAsText() throws IOException {
        new Assertion<>(
            "Can't format a LocalDate with ISO format.",
            new TextOf(LocalDate.now()).asString(),
            new IsNot<>(new IsBlank())
        );
    }

    @Test
    public void localDateTimeFormattedAsIsoDateTime() {
        final LocalDateTime date = LocalDateTime.of(
            2017, 12, 13, 14, 15, 16, 17
        );
        new Assertion<>(
            "Can't format a LocalDateTime with default/ISO format.",
            new TextOf(date),
            new TextIs(
                MessageFormat.format(
                    "2017-12-13T14:15:16.000000017{0}",
                    date.atZone(ZoneId.systemDefault()).getOffset().toString()
                )
            )
        ).affirm();
    }

    @Test
    public void localDateTimeFormattedWithFormatString() {
        final LocalDateTime date = LocalDateTime.of(
            2017, 12, 13, 14, 15, 16, 17
        );
        new Assertion<>(
            "Can't format a LocalDateTime with format.",
            new TextOf(date, "yyyy-MM-dd HH:mm:ss"),
            new TextIs("2017-12-13 14:15:16")
        ).affirm();
    }

    @Test
    public void localDateTimeFormattedWithFormatStringWithLocale() {
        final LocalDateTime date = LocalDateTime.of(
            2017, 12, 13, 14, 15, 16, 17
        );
        new Assertion<>(
            "Can't format a LocalDateTime with format using locale.",
            new TextOf(
                date, "yyyy MMM dd. HH.mm.ss", Locale.FRENCH
            ),
            new TextIs("2017 déc. 13. 14.15.16")
        ).affirm();
    }

    @Test
    public void currentLocalDateTimeAsText() throws IOException {
        new Assertion<>(
            "Can't format a LocalDateTime with ISO format.",
            new TextOf(LocalDateTime.now()).asString(),
            new IsNot<>(new IsNull<>())
        ).affirm();
    }

    @Test
    public void dateFormattedUsingIsoFormatter() {
        final Calendar calendar =
            Calendar.getInstance(TimeZone.getDefault());
        calendar.set(2017, Calendar.DECEMBER, 13, 14, 15, 16);
        calendar.set(Calendar.MILLISECOND, 17);
        final ZoneOffset offset = calendar.getTimeZone().toZoneId()
            .getRules().getOffset(calendar.toInstant());
        new Assertion<>(
            "Can't format a java.util.Date with ISO format.",
            new TextOf(calendar.getTime()),
            new TextIs("2017-12-13T14:15:16.017" + offset)
        ).affirm();
    }

    @Test
    public void dateFormattedUsingCustomFormat()  {
        final Calendar calendar =
            Calendar.getInstance(TimeZone.getDefault());
        calendar.set(2017, Calendar.DECEMBER, 13, 14, 15, 16);
        new Assertion<>(
            "Can't format a java.util.Date with custom format.",
            new TextOf(
                calendar.getTime(), "yyyy MM dd hh:mm:ss"
            ),
            new TextIs("2017 12 13 02:15:16")
        ).affirm();
    }

    @Test
    public void dateFormattedUsingCustomFormatDifferentLocale() {
        final Calendar calendar =
            Calendar.getInstance(TimeZone.getDefault());
        calendar.set(2017, Calendar.DECEMBER, 13, 14, 15, 16);
        new Assertion<>(
            "Can't format a java.util.Date with custom format.",
            new TextOf(
                calendar.getTime(), "yyyy MMM dd hh:mm:ss", Locale.ITALIAN
            ),
            new TextIs("2017 dic 13 02:15:16")
        ).affirm();
    }

    @Test
    public void offsetDateTimeFormattedAsIsoDateTime() {
        final OffsetDateTime date = OffsetDateTime.of(
            2017, 12, 13, 14, 15, 16, 17, ZoneOffset.ofHours(1)
        );
        new Assertion<>(
            "Can't format a OffsetDateTime with default/ISO format.",
            new TextOf(date),
            new TextIs("2017-12-13T14:15:16.000000017+01:00")
        ).affirm();
    }

    @Test
    public void offsetDateTimeFormattedWithFormatString() {
        final OffsetDateTime date = OffsetDateTime.of(
            2017, 12, 13, 14, 15, 16, 17, ZoneOffset.ofHours(1)
        );
        new Assertion<>(
            "Can't format a OffsetDateTime with format.",
            new TextOf(date, "yyyy-MM-dd HH:mm:ss"),
            new TextIs("2017-12-13 14:15:16")
        ).affirm();
    }

    @Test
    public void offsetDateTimeFormattedWithFormatStringWithLocale() {
        final OffsetDateTime date = OffsetDateTime.of(
            2017, 12, 13, 14, 15, 16, 17, ZoneOffset.ofHours(1)
        );
        new Assertion<>(
            "Can't format a OffsetDateTime with format using locale.",
            new TextOf(
                date, "yyyy MMM dd. HH.mm.ss", Locale.FRENCH
            ),
            new TextIs("2017 déc. 13. 14.15.16")
        ).affirm();
    }

    @Test
    public void currentOffsetDateTimeAsText() throws IOException {
        new Assertion<>(
            "Can't format a OffsetDateTime with ISO format.",
            new TextOf(OffsetDateTime.now()).asString(),
            new IsNot<>(new IsNull<>())
        ).affirm();
    }

    @Test
    public void zonedDateTimeFormattedAsIsoDateTime() {
        final ZonedDateTime date = ZonedDateTime.of(
            2017, 12, 13, 14, 15, 16, 17, ZoneId.of("Europe/Berlin")
        );
        new Assertion<>(
            "Can't format a ZonedDateTime with default/ISO format.",
            new TextOf(date),
            new TextIs("2017-12-13T14:15:16.000000017+01:00")
        ).affirm();
    }

    @Test
    public void zonedDateTimeFormattedWithFormatString() {
        final ZonedDateTime date = ZonedDateTime.of(
            2017, 12, 13, 14, 15, 16, 17, ZoneId.of("Europe/Berlin")
        );
        new Assertion<>(
            "Can't format a ZonedDateTime with format.",
            new TextOf(date, "yyyy-MM-dd HH:mm:ss"),
            new TextIs("2017-12-13 14:15:16")
        ).affirm();
    }

    @Test
    public void zonedDateTimeFormattedWithFormatStringWithLocale() {
        final ZonedDateTime date = ZonedDateTime.of(
            2017, 12, 13, 14, 15, 16, 17, ZoneId.of("Europe/Berlin")
        );
        new Assertion<>(
            "Can't format a ZonedDateTime with format using locale.",
            new TextOf(
                date, "yyyy MMM dd. HH.mm.ss", Locale.FRENCH
            ),
            new TextIs("2017 déc. 13. 14.15.16")
        ).affirm();
    }

    @Test
    public void currentZonedDateTimeAsText() throws IOException {
        new Assertion<>(
            "Can't format a ZonedDateTime with ISO format.",
            new TextOf(ZonedDateTime.now()).asString(),
            new IsNot<>(new IsNull<>())
        ).affirm();
    }
}
