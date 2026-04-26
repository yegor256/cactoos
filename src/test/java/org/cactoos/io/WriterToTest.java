/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.nio.file.Path;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.llorllale.cactoos.matchers.HasContent;

/**
 * Test case for {@link WriterTo}.
 *
 * @since 0.13
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class WriterToTest {

    @Test
    void writesLargeContentToFile(@TempDir final Path wdir) {
        final Path temp = wdir.resolve("cactoos-1.txt-1");
        MatcherAssert.assertThat(
            "Can't copy Input to Output and return Input",
            new TeeInput(
                new ResourceOf("org/cactoos/large-text.txt"),
                new WriterAsOutput(new WriterTo(temp))
            ),
            new HasContent(
                new TextOf(temp)
            )
        );
    }
}
