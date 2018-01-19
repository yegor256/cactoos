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
import java.nio.file.Files;
import org.cactoos.text.JoinedText;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
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
public final class InterruptedAtomicFileTest {

    @Test
    public void determineInterruptAfterWrite() throws IOException {
        final File original = Files.createTempFile("cactoos-1", "txt-1")
                    .toFile();
        original.delete();
        final JoinedText tempAbsPath = new JoinedText(
                    "",
                System.getProperty("java.io.tmpdir"),
                original.getName(),
                "_tmp"
        );
        final File temp = new File(tempAbsPath.asString());
        temp.createNewFile();
        MatcherAssert.assertThat(
                        "Could not determine post-write interruption status",
                new InterruptedAtomicFile(
                                new AtomicFile(
                                        original.getAbsolutePath()
                        )
                ).interruptedAfterWrite(),
                Matchers.equalTo(Boolean.TRUE)
        );
    }

    @Test
    public void determineInterruptDuringWrite() throws IOException {
        final File original = Files.createTempFile("cactoos-1", "txt-1")
                    .toFile();
        original.createNewFile();
        final JoinedText tempAbsPath = new JoinedText(
                        "",
                System.getProperty("java.io.tmpdir"),
                original.getName(),
                "_tmp"
        );
        final File temp = new File(tempAbsPath.asString());
        temp.createNewFile();
        MatcherAssert.assertThat(
                            "Could not determine mid-write interruption status",
                new InterruptedAtomicFile(
                                    new AtomicFile(
                                            original.getAbsolutePath()
                        )
                ).interruptedDuringWrite(),
                Matchers.equalTo(Boolean.TRUE)
        );
    }

}
