/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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
package org.cactoos.io;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.llorllale.cactoos.matchers.MatcherOf;

/**
 * Unit tests for {@link TempFile}.
 *
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class TempFileTest {

    @Test
    public void createFile() throws Exception {
        try (final TempFile file = new TempFile()) {
            MatcherAssert.assertThat(
                "Cannot create a temp file",
                Files.exists(file.value()),
                Matchers.is(true)
            );
        }
    }

    @Test
    public void createFileInCustomPath() throws Exception {
        final Path custom = Paths.get(System.getProperty("user.home"));
        try (final TempFile file = new TempFile(() -> custom, "", "")) {
            MatcherAssert.assertThat(
                "Cannot create a temp file at a custom path",
                file,
                Matchers.allOf(
                    new MatcherOf<>(
                        tmp -> {
                            return Files.exists(tmp.value());
                        }
                    ),
                    new MatcherOf<>(
                        tmp -> {
                            return tmp.value().getParent().equals(custom);
                        }
                    )
                )
            );
        }
    }

    @Test
    public void deleteFile() throws Exception {
        final TempFile file = new TempFile();
        file.close();
        MatcherAssert.assertThat(
            "Cannot delete file on close",
            Files.exists(file.value()),
            Matchers.is(false)
        );
    }

    @Test
    public void createFileWithPrefix() throws Exception {
        final String prefix =
            String.format("randomPrefix%s", System.currentTimeMillis());
        try (final TempFile file = new TempFile(prefix, "")) {
            MatcherAssert.assertThat(
                "File not created with the given prefix",
                file.value().getFileName().toString(),
                Matchers.startsWith(prefix)
            );
        }
    }

    @Test
    public void createFileWithSuffix() throws Exception {
        final String suffix = String.format(
            "randomSuffix%s", System.currentTimeMillis()
        );
        try (final TempFile file = new TempFile("", suffix)) {
            MatcherAssert.assertThat(
                "File not created with the given suffix",
                file.value().getFileName().toString(),
                Matchers.endsWith(suffix)
            );
        }
    }
}
