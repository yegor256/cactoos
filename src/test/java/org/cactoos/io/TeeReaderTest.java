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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.cactoos.InputHasContent;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Test case for {@link TeeReader}.
 *
 * @author Mehmet Yildirim (memoyil@gmail.com)
 * @version $Id$
 * @since 0.13
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class TeeReaderTest {

    @Test
    public void testTeeReader() throws IOException {
        final Path src = Files.createTempFile("cactoos-1", "txt-1");
        final Path dst = Files.createTempFile("cactoos-2", "txt-2");
        final String content = "Hello, товарищ!";
        Files.write(src, content.getBytes(StandardCharsets.UTF_8));
        final Reader reader = new ReaderOf(
            new FileInputStream(src.toFile()),
            new FileOutputStream(dst.toFile())
        );
        int done = 0;
        while (done >= 0) {
            done = reader.read();
        }
        reader.close();
        MatcherAssert.assertThat(
            "Can't read file content",
            new InputOf(new ReaderOf(dst)),
            new InputHasContent(content)
        );
    }

}
