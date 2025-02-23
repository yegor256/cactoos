/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import org.cactoos.text.TextOf;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link TeeOutputStream}.
 * @since 0.16
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class TeeOutputStreamTest {

    @Test
    @SuppressWarnings("unchecked")
    void copiesContentByteByByte() throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ByteArrayOutputStream copy = new ByteArrayOutputStream();
        final String content = "Hello, товарищ!";
        new Assertion<>(
            "Must copy OutputStream to OutputStream byte by byte",
            new TextOf(
                new ReaderOf(
                    new TeeInputStream(
                        new ByteArrayInputStream(
                            content.getBytes(StandardCharsets.UTF_8)
                        ),
                        new TeeOutputStream(baos, copy)
                    )
                )
            ).asString(),
            new AllOf<>(
                new IsEqual<>(content),
                new IsEqual<>(
                    new String(baos.toByteArray(), StandardCharsets.UTF_8)
                ),
                new IsEqual<>(
                    new String(copy.toByteArray(), StandardCharsets.UTF_8)
                )
            )
        ).affirm();
    }

}
