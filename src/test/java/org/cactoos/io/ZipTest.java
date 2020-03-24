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
import java.util.zip.ZipFile;
import org.cactoos.iterable.IterableOf;
import org.cactoos.scalar.Sticky;
import org.cactoos.text.Joined;
import org.cactoos.text.TextOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.ScalarHasValue;

/**
 * Test case for {@link Zip}.
 *
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class ZipTest {

    /**
     * Folder to zip
     */
    private static final String FOLDER = "abc";

    /**
     * Final zip file name
     */
    private static final String ZIPNAME = FOLDER + ".zip";

    /**
     * First file name
     */
    private static final String AFILE = "A.txt";

    /**
     * Second file name
     */
    private static final String BFILE = "B.txt";

    /**
     * Folder A
     */
    private static final String AFOLDER = "A";

    /**
     * Folder B
     */
    private static final String BFOLDER = "B";

    /**
     * Folder C
     */
    private static final String CFOLDER = "C";

    /**
     * Temporary folder.
     */
    @Rule
    public final TemporaryFolder temporal = new TemporaryFolder();

    @Test
    public void mustZipDirectory() throws Exception {
        final File folder = this.temporal.newFolder(FOLDER);
        try (OutputStream out = new OutputStreamTo(new File(folder, AFILE))) {
            out.write("ABC".getBytes());
        }
        new File(folder, BFOLDER).mkdirs();
        try (OutputStream out = new OutputStreamTo(
            new File(folder, new Joined(File.separator, BFOLDER, BFILE).asString()))
        ) {
            out.write("EFG".getBytes());
        }
        new File(folder, CFOLDER).mkdirs();
        new Assertion<>(
            "Must zip directory with the same directory structure",
            new Sticky<>(
                () -> {
                    try (OutputStream out = new OutputStreamTo(
                        this.temporal.newFile(ZIPNAME)
                    )) {
                        out.write(
                            new BytesOf(
                                new Zip(new Directory(folder))
                            ).asBytes()
                        );
                    }
                    try (ZipFile zipped = new ZipFile(
                        new File(folder.getParentFile(), ZIPNAME)
                    )
                    ) {
                        return new IterableOf<>(
                            zipped.stream().map(
                                e -> new TextOf(e.getName())
                            ).collect(Collectors.toList())
                        );
                    }
                }
            ),
            new ScalarHasValue<>(
                new IterableOf<>(
                    new Joined(File.separator, FOLDER, AFILE),
                    new Joined(File.separator, FOLDER, BFOLDER, BFILE),
                    new Joined(
                        File.separator,
                        FOLDER,
                        new Joined("/", CFOLDER, "").asString()
                    )
                )
            )
        ).affirm();
    }
}
