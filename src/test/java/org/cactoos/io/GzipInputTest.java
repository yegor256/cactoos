/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

package org.cactoos.io;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.zip.GZIPOutputStream;
import org.cactoos.scalar.LengthOf;
import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link GzipInput}.
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class GzipInputTest {

    @Test
    void readFromGzipInput() throws Exception {
        final String content = "Hello!";
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (
            Writer writer = new BufferedWriter(
                new OutputStreamWriter(
                    new GZIPOutputStream(out)
                )
            )
        ) {
            writer.write(content);
        }
        final byte[] bytes = out.toByteArray();
        new Assertion<>(
            "Can't read from a gzip input",
            new TextOf(
                new GzipInput(new InputOf(bytes))
            ),
            new IsText(content)
        ).affirm();
    }

    @Test
    void readFromDeadGzipInput() {
        new Assertion<>(
            "Can't read from empty input",
            () -> new LengthOf(
                new GzipInput(new DeadInput())
            ).value(),
            new Throws<>(EOFException.class)
        ).affirm();
    }
}
