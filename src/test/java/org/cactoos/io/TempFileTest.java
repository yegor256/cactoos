/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
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
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link TempFile}.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @author Fabricio Cabral (fabriciofx@gmail.com)
 * @version $Id$
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class TempFileTest {

    @Test
    public void createATempFileWithoutPrefixAndSuffix() throws Exception {
        try (final TempFile temp = new TempFile()) {
            MatcherAssert.assertThat(
                "Can't create a temporary file without prefix and suffix",
                Files.exists(temp.value()),
                Matchers.equalTo(true)
            );
        }
    }

    @Test
    public void createATempFileWithPrefix() throws Exception {
        try (final TempFile temp = new TempFile("cactoos-123")) {
            MatcherAssert.assertThat(
                "Can't create a temporary file with prefix",
                Files.exists(temp.value()),
                Matchers.equalTo(true)
            );
        }
    }

    @Test
    public void createATempFileWithPrefixAndSuffix() throws Exception {
        try (final TempFile temp = new TempFile("cactoos-456", ".xyz")) {
            MatcherAssert.assertThat(
                "Can't create a temporary file with prefix and suffix",
                Files.exists(temp.value()),
                Matchers.equalTo(true)
            );
        }
    }

    @Test
    public void createATempFileWithPrefixInName() throws Exception {
        final String prefix = "cactoos-789";
        final String suffix = ".tmp";
        try (final TempFile temp = new TempFile(prefix)) {
            MatcherAssert.assertThat(
                "Can't create a temporary file with prefix in name",
                temp.value().getFileName().toString(),
                Matchers.allOf(
                    Matchers.startsWith(prefix),
                    Matchers.endsWith(suffix)
                )
            );
        }
    }

    @Test
    public void createATempFileWithPrefixAndSuffixInName() throws Exception {
        final String prefix = "cactoos-012";
        final String suffix = ".abc";
        try (final TempFile temp = new TempFile(prefix, suffix)) {
            MatcherAssert.assertThat(
                "Can't create a temporary file with prefix and suffix in name",
                temp.value().getFileName().toString(),
                Matchers.allOf(
                    Matchers.startsWith(prefix),
                    Matchers.endsWith(suffix)
                )
            );
        }
    }
}

