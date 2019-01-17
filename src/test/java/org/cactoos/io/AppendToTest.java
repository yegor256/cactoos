/*
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
import org.cactoos.text.FormattedText;
import org.cactoos.text.JoinedText;
import org.cactoos.text.RandomText;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.llorllale.cactoos.matchers.TextIs;

/**
 * Test case for {@link AppendTo}.
 *
 * @since 1.0
 * @checkstyle MagicNumber (500 line)
 * @checkstyle ClassDataAbstractionCouplingCheck (100 lines)
 */
public final class AppendToTest {

    /**
     * Temporary files and folders generator.
     */
    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    /**
     * A rule for handling an exception.
     */
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    /**
     * Ensures that AppendTo is failing on a negative predicate result.
     * @throws Exception if fails
     */
    @Test
    public void failsIfFileDoesNotExist() throws Exception {
        final File source = new File(new RandomText(5).asString());
        this.exception.expect(IOException.class);
        this.exception.expectMessage(
            new FormattedText(
                "Can not append to %s file. It does not exist",
                source.getAbsolutePath()
            ).asString()
        );
        new AppendTo(source).stream();
    }

    /**
     * Ensures that AppendTo is appending to a given file.
     * @throws Exception if fails
     */
    @Test
    public void appendsToFile() throws Exception {
        final File source = this.folder.newFile();
        final String first = "abdcd";
        new OutputTo(source).stream().write(first.getBytes());
        final String second = "efgh";
        new AppendTo(source).stream().write(second.getBytes());
        MatcherAssert.assertThat(
            "Does not contain expected text",
            new TextOf(source),
            new TextIs(new JoinedText("", first, second))
        );
    }
}
