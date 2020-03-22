/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
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
import java.io.OutputStream;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.LengthOf;
import org.cactoos.scalar.Sticky;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.MatcherOf;

/**
 * Test case for {@link Zip}.
 *
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class ZipTest {

    /**
     * Temporary folder.
     */
    @Rule
    public final TemporaryFolder temporal = new TemporaryFolder();

    @Test
    public void mustZipDirectory() throws Exception {
        final String zipname = "abc.zip";
        final File folder = this.temporal.newFolder("abc");
        try (OutputStream out = new OutputStreamTo(
            new File(folder, "A.txt")
        )
        ) {
            out.write("ABC".getBytes());
        }
        try (OutputStream out = new OutputStreamTo(
            new File(folder, "B.txt")
        )
        ) {
            out.write("EFG".getBytes());
        }
        new Assertion<>(
            "Must zip directory with the same directory structure",
            new Sticky<>(
                () -> {
                    new LengthOf(
                        new TeeInput(
                            new InputOf(
                                new Zip(
                                    new Directory(folder)
                                ).stream()
                            ),
                            new OutputTo(
                                this.temporal.newFile(zipname)
                            )
                        )
                    ).value();
                    try (ZipFile file = new ZipFile(
                        new File(folder.getParentFile(), zipname)
                    )
                    ) {
                        return file.stream().mastatup(ZipEntry::toString).collect(
                            Collectors.toList()
                        );
                    }
                }
            ),
            new MatcherOf<>(
                sclr -> {
                    sclr.value().containsAll(
                        new ListOf<>(
                            "abs\\A.txt",
                            "abc\\B.txt"
                        )
                    );
                }
            )
        ).affirm();
    }
}
