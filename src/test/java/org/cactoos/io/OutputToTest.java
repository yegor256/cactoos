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

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import org.cactoos.scalar.LengthOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.InputHasContent;

/**
 * Test case for {@link OutputTo}.
 *
 * @since 0.15
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class OutputToTest {

    /**
     * Temporary folder.
     */
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void writesIntoPath() throws Exception {
        final Path temp = this.folder.newFolder("cactoos-1").toPath();
        final Path path = temp.resolve("one/two/three/file.txt");
        final String content = "Hello, товарищ!";
        new LengthOf(new TeeInput(content, new OutputTo(path))).value();
        new Assertion<>(
            "Must write into path",
            new InputOf(path),
            new InputHasContent(content)
        ).affirm();
    }

    @Test
    public void writesIntoFile() throws Exception {
        final Path temp = this.folder.newFolder("cactoos-2").toPath();
        final Path path = temp.resolve("a/b/c/file.txt");
        final String txt = "Hello, друг!";
        new LengthOf(
            new TeeInput(txt, new SyncOutput(new OutputTo(path.toFile())))
        ).value();
        new Assertion<>(
            "Must write into file",
            new InputOf(path.toFile()),
            new InputHasContent(txt)
        ).affirm();
    }

    @Test
    public void writesIntoWriter() throws Exception {
        final String txt = "Hello, writer!";
        final StringWriter output = new StringWriter();
        new LengthOf(new TeeInput(txt, new OutputTo(output))).value();
        new Assertion<>(
            "Must write into writer",
            new InputOf(output.getBuffer()),
            new InputHasContent(txt)
        ).affirm();
    }

    @Test
    public void writesIntoWriterWithCharset() throws Exception {
        final String txt = "Hello, writer with charset!";
        final StringWriter output = new StringWriter();
        new LengthOf(
            new TeeInput(txt, new OutputTo(output, StandardCharsets.UTF_8))
        ).value();
        new Assertion<>(
            "Must write into writer with charset",
            new InputOf(output.getBuffer()),
            new InputHasContent(txt)
        ).affirm();
    }

    @Test
    public void writesIntoWriterWithCharsetByName() throws Exception {
        final String txt = "Hello, writer with charset by name!";
        final StringWriter output = new StringWriter();
        new LengthOf(
            new TeeInput(txt, new OutputTo(output, StandardCharsets.UTF_8))
        ).value();
        new Assertion<>(
            "Must write into writer with charset by name",
            new InputOf(output.getBuffer()),
            new InputHasContent(txt)
        ).affirm();
    }

    @Test
    public void writesIntoWriterWithCharsetAndSize() throws Exception {
        final String txt = "Hello, writer with charset and size!";
        final StringWriter output = new StringWriter();
        new LengthOf(
            new TeeInput(
                txt,
                new OutputTo(output, StandardCharsets.UTF_8, 1)
            )
        ).value();
        new Assertion<>(
            "Must write into writer with charset and size",
            new InputOf(output.getBuffer()),
            new InputHasContent(txt)
        ).affirm();
    }

    @Test
    public void writesIntoWriterWithSize() throws Exception {
        final String txt = "Hello, writer with size!";
        final StringWriter output = new StringWriter();
        new LengthOf(
            new TeeInput(
                txt,
                new OutputTo(output, 1)
            )
        ).value();
        new Assertion<>(
            "Must write into writer with size",
            new InputOf(output.getBuffer()),
            new InputHasContent(txt)
        ).affirm();
    }

    @Test
    public void writesIntoWriterWithCharsetByNameAndSize() throws Exception {
        final String txt = "Hello, writer with charset by name and size!";
        final StringWriter output = new StringWriter();
        new LengthOf(
            new TeeInput(
                txt,
                new OutputTo(output, StandardCharsets.UTF_8.name(), 1)
            )
        ).value();
        new Assertion<>(
            "Must write into writer with charset by name and size",
            new InputOf(output.getBuffer()),
            new InputHasContent(txt)
        ).affirm();
    }

    @Test
    public void writesIntoWriterWithDecoderAndSize() throws Exception {
        final String txt = "Hello, writer with decoder and size!";
        final StringWriter output = new StringWriter();
        new LengthOf(
            new TeeInput(
                txt,
                new OutputTo(output, StandardCharsets.UTF_8.newDecoder(), 1)
            )
        ).value();
        new Assertion<>(
            "Must write into writer with decoder and size",
            new InputOf(output.getBuffer()),
            new InputHasContent(txt)
        ).affirm();
    }
}
