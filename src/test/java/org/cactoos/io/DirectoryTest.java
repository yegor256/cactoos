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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Test case for {@link Directory}.
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class DirectoryTest {

    /**
     * Temporary folder.
     */
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void listsFilesAndFoldersInDirectory() throws IOException {
        final Path dir = this.folder.newFolder().toPath();
        dir.resolve("x/y").toFile().mkdirs();
        Files.write(dir.resolve("x/y/test"), "".getBytes());
        MatcherAssert.assertThat(
            "Can't list files in a directory represented by a path",
            new Directory(dir),
            // @checkstyle MagicNumber (1 line)
            Matchers.iterableWithSize(4)
        );
    }

    @Test
    public void listsFilesInDirectoryByFile() throws Exception {
        final File file = this.folder.newFolder();
        final Path dir = file.toPath();
        dir.resolve("parent/child").toFile().mkdirs();
        Files.write(dir.resolve("parent/child/file"), "".getBytes());
        MatcherAssert.assertThat(
            "Can't list files in a directory represented by a file",
            new Directory(file),
            // @checkstyle MagicNumber (1 line)
            Matchers.iterableWithSize(4)
        );
    }
}
