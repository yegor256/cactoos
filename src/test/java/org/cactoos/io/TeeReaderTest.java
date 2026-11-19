/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Arrays;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.HasContent;

/**
 * Test case for {@link TeeReader}.
 *
 * @since 0.13
 */
@SuppressWarnings("PMD.CloseInlineResourceRule")
final class TeeReaderTest {

    @Test
    void copiesViaTeeReader() throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final String content = "Hello, товарищ!";
        final Reader reader = new TeeReader(
            new ReaderOf(content),
            new WriterTo(baos)
        );
        int done = 0;
        while (done >= 0) {
            done = reader.read();
        }
        reader.close();
        MatcherAssert.assertThat(
            "Must read content",
            new InputOf(new ReaderOf(baos.toByteArray())),
            new HasContent(content)
        );
    }

    @Test
    void copiesOnlyCharactersReadAtOffset() throws IOException {
        final char[] buffer = "----".toCharArray();
        final StringWriter copy = new StringWriter();
        try (Reader reader = new TeeReader(new ReaderOf("x"), copy)) {
            MatcherAssert.assertThat(
                "Must preserve the buffer outside the read range and copy only the characters read",
                Arrays.asList(
                    Integer.toString(reader.read(buffer, 1, 2)),
                    String.valueOf(buffer), copy.toString()
                ),
                new IsEqual<>(Arrays.asList("1", "-x--", "x"))
            );
        }
    }
}
