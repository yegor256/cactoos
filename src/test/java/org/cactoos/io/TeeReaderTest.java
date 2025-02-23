/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Reader;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasContent;

/**
 * Test case for {@link TeeReader}.
 *
 * @since 0.13
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class TeeReaderTest {

    @Test
    void testTeeReader() throws IOException {
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
        new Assertion<>(
            "Must read content",
            new InputOf(new ReaderOf(baos.toByteArray())),
            new HasContent(content)
        ).affirm();
    }

}
