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
import java.nio.charset.Charset;
import java.nio.file.Files;
import org.cactoos.TextHasString;
import org.cactoos.func.MatcherOf;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Test case for {@link AtomicFile}.
 *
 * @author Ashton Hogan (info@ashtonhogan.com)
 * @version $Id$
 * @since 0.49.2
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class AtomicFileTest {

    @Test
    public void writesStringToFileAtomically() throws IOException, 
            InterruptedException {
        final File original = Files.createTempFile("cactoos-1", "tmp-1")
                .toFile();
        final AtomicFile atomicFile = new AtomicFile(
                        original.getAbsolutePath()
        );
        atomicFile.overwrite("abc", Charset.forName("UTF-8"));
        MatcherAssert.assertThat(
                        "Can't write to an atomic file",
                new TextOf(
                                new AtomicFile(
                                        original.getAbsolutePath()
                        )
                ),
                new TextHasString(
                                new MatcherOf<>(
                                        str -> {
                            return new TextOf(atomicFile)
                                            .asString()
                                        .equals(str);
                                }
                        )
                )
        );
    }

}
