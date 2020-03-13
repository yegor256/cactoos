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
import java.nio.charset.StandardCharsets;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.InputHasContent;

/**
 * Test case for {@link TeeInput}. Cases for ctors which use byte array as an
 * input.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (100 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (100 lines)
 */
public final class TeeInputFromByteArrayTest {

    /**
     * Temporary files generator.
     */
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void copiesFromByteArrayToPath() throws Exception {
        final String message =
            "Hello, товарищ path äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new Assertion<>(
            "Can't copy bytes",
            new TeeInput(
                message.getBytes(StandardCharsets.UTF_8),
                output.toPath()
            ),
            new InputHasContent(message)
        ).affirm();
    }

    @Test
    public void copiesFromByteArrayToFile() throws Exception {
        final String message =
            "Hello, товарищ file äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new Assertion<>(
            "Can't copy bytes",
            new TeeInput(
                message.getBytes(StandardCharsets.UTF_8),
                output
            ),
            new InputHasContent(message)
        ).affirm();
    }

    @Test
    public void copiesFromByteArrayToOutput() throws Exception {
        final String message =
            "Hello, товарищ output äÄ üÜ öÖ and ß";
        final File output = this.folder.newFile();
        new Assertion<>(
            "Can't copy bytes",
            new TeeInput(
                message.getBytes(StandardCharsets.UTF_8),
                new OutputTo(output)
            ),
            new InputHasContent(message)
        ).affirm();
    }
}
