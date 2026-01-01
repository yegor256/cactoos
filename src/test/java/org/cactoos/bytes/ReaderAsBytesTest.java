/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.bytes;

import java.io.StringReader;
import org.cactoos.text.TextOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;
import org.llorllale.cactoos.matchers.IsTrue;

/**
 * Test case for {@link ReaderAsBytes}.
 *
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class ReaderAsBytesTest {

    @Test
    void readsString() {
        final String source = "hello, друг!";
        new Assertion<>(
            "Must read string through a reader",
            new TextOf(
                new ReaderAsBytes(
                    new StringReader(source)
                )
            ),
            new IsText(source)
        ).affirm();
    }

    @Test
    void readsAndClosesReader() throws Exception {
        final EmptyClosableReader reader = new EmptyClosableReader();
        new ReaderAsBytes(reader).asBytes();
        new Assertion<>(
            "Must close the reader after reading it",
            reader.isClosed(),
            new IsTrue()
        ).affirm();
    }
}
