/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2024 Yegor Bugayenko
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
import org.cactoos.Text;
import org.cactoos.text.FormattedText;
import org.cactoos.text.TextOf;
import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.EndsWith;
import org.llorllale.cactoos.matchers.Satisfies;
import org.llorllale.cactoos.matchers.StartsWith;

/**
 * Unit tests for {@link TempFile}.
 *
 * @since 1.0
 */
final class TempFileTest {

    @Test
    void createFile() throws Exception {
        try (TempFile file = new TempFile()) {
            new Assertion<>(
                "Must create a temp file",
                Files.exists(file.value()),
                new IsEqual<>(true)
            ).affirm();
        }
    }

    @Test
    void createFileInCustomPath() throws Exception {
        final Path custom = Paths.get(System.getProperty("user.home"));
        try (TempFile file = new TempFile(() -> custom, "", "")) {
            new Assertion<>(
                "Must create a temp file at a custom path",
                file,
                new AllOf<TempFile>(
                    new Satisfies<>(
                        tmp -> Files.exists(tmp.value())
                    ),
                    new Satisfies<>(
                        tmp -> tmp.value().getParent().equals(custom)
                    )
                )
            ).affirm();
        }
    }

    @Test
    void deleteFile() throws Exception {
        final TempFile file = new TempFile();
        file.close();
        new Assertion<>(
            "Must delete file on close",
            Files.exists(file.value()),
            new IsEqual<>(false)
        ).affirm();
    }

    @Test
    void createFileWithPrefix() throws Exception {
        final Text prefix = new FormattedText(
            "randomPrefix%s",
            System.currentTimeMillis()
        );
        try (TempFile file = new TempFile(prefix, new TextOf(""))) {
            new Assertion<>(
                "File must be created with the given prefix",
                new TextOf(file.value().getFileName().toString()),
                new StartsWith(prefix)
            ).affirm();
        }
    }

    @Test
    void createFileWithSuffix() throws Exception {
        final Text suffix = new FormattedText(
            "randomSuffix%s", System.currentTimeMillis()
        );
        try (TempFile file = new TempFile(new TextOf(""), suffix)) {
            new Assertion<>(
                "File must be created with the given suffix",
                new TextOf(file.value().getFileName().toString()),
                new EndsWith(suffix)
            ).affirm();
        }
    }
}
