/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import org.cactoos.text.TextOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Test case for {@link CloseShieldOutputStream}.
 * @since 1.0.0
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class CloseShieldOutputStreamTest {

    @Test
    void writesContentToFile(@TempDir final Path tempdir) throws IOException {
        final File file = new File(tempdir.toFile(), "cactoos-1.txt-1");
        file.createNewFile();
        final Path temp = file.toPath();
        try (OutputStream out = new OutputStreamTo(temp)) {
            new Assertion<>(
                "Must copy Input to Output and return Input",
                new TextOf(
                    new Sticky(
                        new TeeInput(
                            new ResourceOf("org/cactoos/small-text.txt"),
                            new OutputTo(new CloseShieldOutputStream(out))
                        )
                    )
                ),
                new IsText(
                    new TextOf(temp)
                )
            ).affirm();
        }
    }

    @Test
    @SuppressWarnings("try")
    void preventsOriginalStreamToBeClosed() throws Exception {
        try (FakeOutputStream origin = new FakeOutputStream()) {
            // @checkstyle EmptyBlockCheck (2 lines)
            try (OutputStream ignored = new CloseShieldOutputStream(origin)) {
            }
            new Assertion<>(
                "Must not close origin stream",
                origin.isClosed(),
                new IsEqual<>(false)
            ).affirm();
        }
    }
}
