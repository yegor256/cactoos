/**
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
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import org.cactoos.matchers.InputHasContent;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Test case for {@link InputStreamOf}.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.13
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class InputStreamOfTest {

    /**
     * Temporary files generator.
     */
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void readsSimpleFileContent() throws IOException {
        final File file = this.folder.newFile();
        final String content = "Hello, товарищ!";
        Files.write(file.toPath(), content.getBytes(StandardCharsets.UTF_8));
        MatcherAssert.assertThat(
            "Can't read file content",
            new InputOf(new InputStreamOf(file)),
            new InputHasContent(content)
        );
    }

    @Test
    public void readsFromReader() {
        final String content = "Hello, дорогой товарищ!";
        MatcherAssert.assertThat(
            "Can't read from reader",
            new InputOf(new InputStreamOf(new StringReader(content))),
            new InputHasContent(content)
        );
    }

    @Test
    public void readsFromReaderThroughSmallBuffer() {
        final String content = "Hello, صديق!";
        MatcherAssert.assertThat(
            "Can't read from reader through small buffer",
            new InputOf(new InputStreamOf(new StringReader(content), 1)),
            new InputHasContent(content)
        );
    }

    @Test
    public void makesDataAvailable() throws IOException {
        final String content = "Hello,חבר!";
        MatcherAssert.assertThat(
            "Can't show that data is available",
            new InputStreamOf(content).available(),
            Matchers.greaterThan(0)
        );
    }

}
