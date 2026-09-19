/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.io;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.llorllale.cactoos.matchers.HasSize;

/**
 * Test case for {@link Walked}.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class WalkedTest {

    @Test
    void walksFileTreeUpToGivenDepth(@TempDir final Path dir) throws IOException {
        dir.resolve("a/b/c").toFile().mkdirs();
        Files.write(
            dir.resolve("a/b/c/leaf"),
            "".getBytes(StandardCharsets.UTF_8)
        );
        MatcherAssert.assertThat(
            "must visit start and direct children only when depth is 1",
            new Walked(dir, 1),
            new HasSize(2)
        );
    }

    @Test
    void walksFileTreeWhenStartedFromFile(@TempDir final Path dir) throws IOException {
        dir.resolve("x/y").toFile().mkdirs();
        Files.write(
            dir.resolve("x/y/file"),
            "".getBytes(StandardCharsets.UTF_8)
        );
        MatcherAssert.assertThat(
            "must visit every path under the start when depth is unbounded",
            new Walked(dir.toFile(), Integer.MAX_VALUE),
            new HasSize(4)
        );
    }
}
