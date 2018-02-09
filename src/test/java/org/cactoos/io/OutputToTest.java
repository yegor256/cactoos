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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.cactoos.matchers.InputHasContent;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Test case for {@link OutputTo}.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.15
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class OutputToTest {

    @Test
    public void writesSimplePathContent() throws IOException {
        final Path temp = Files.createTempDirectory("cactoos-1");
        final Path path = temp.resolve("one/two/three/file.txt");
        final String content = "Hello, товарищ!";
        new LengthOf(new TeeInput(content, new OutputTo(path))).intValue();
        MatcherAssert.assertThat(
            "Can't write path content",
            new InputOf(path),
            new InputHasContent(content)
        );
    }

    @Test
    public void writesSimpleFileContent() throws IOException {
        final Path temp = Files.createTempDirectory("cactoos-2");
        final Path path = temp.resolve("a/b/c/file.txt");
        final String txt = "Hello, друг!";
        new LengthOf(
            new TeeInput(txt, new SyncOutput(new OutputTo(path.toFile())))
        ).intValue();
        MatcherAssert.assertThat(
            "Can't write file content",
            new InputOf(path.toFile()),
            new InputHasContent(txt)
        );
    }

}
