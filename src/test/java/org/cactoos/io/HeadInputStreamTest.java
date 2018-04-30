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
import java.io.InputStream;
import org.cactoos.matchers.TextHasString;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link HeadInputStream}.
 *
 * @author Mehmet Yildirim (memoyil@gmail.com)
 * @version $Id$
 * @since 0.29.3
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class HeadInputStreamTest {

    @Test
    public void readsAmountOfSize() throws IOException {
        final int size = 5;
        final int length = 5;
        final CharSequence charsequence = "CactoosAmountSize";
        final byte[] bytes = new byte[size];
        MatcherAssert.assertThat(
            new HeadInput(charsequence, size).stream().read(bytes, 0, length),
            Matchers.equalTo(size)
        );
    }

    @Test
    public void readsAmountOfLength() throws IOException {
        final int size = 5;
        final CharSequence charsequence = "CactoosAmount";
        final byte[] bytes = new byte[size];
        MatcherAssert.assertThat(
            new HeadInput(charsequence, size).stream().read(bytes, 0, 2),
            Matchers.equalTo(2)
        );
    }

    @Test
    public void readsToBuffer() throws IOException {
        final int size = 5;
        final CharSequence charsequence = "CactoosBuffer";
        final byte[] bytes = new byte[size];
        MatcherAssert.assertThat(
            new HeadInput(charsequence, size).stream().read(bytes),
            Matchers.equalTo(size)
        );
    }

    @Test
    public void readsOnlyOneData() throws IOException {
        final int size = 5;
        final int cbyte = 67;
        final CharSequence charsequence = "CactoosOne";
        MatcherAssert.assertThat(
            new HeadInput(charsequence, size).stream().read(),
            Matchers.equalTo(cbyte)
        );
    }

    @Test
    @SuppressWarnings("PMD.CheckSkipResult")
    public void skipsDataAndReadsRest() throws IOException {
        final int size = 5;
        final CharSequence charsequence = "CactoosSkip";
        final InputStream stream = new HeadInput(charsequence, size).stream();
        stream.skip(1);
        MatcherAssert.assertThat(
            new TextOf(
                new BytesOf(stream)
            ),
            new TextHasString(Matchers.containsString("actoo"))
        );
    }

    @Test
    @SuppressWarnings("PMD.CheckSkipResult")
    public void readsOneResetAndReads() throws IOException {
        final int size = 5;
        final int cbyte = 67;
        final CharSequence charsequence = "CactoosReset";
        final InputStream stream = new HeadInput(charsequence, size).stream();
        stream.read();
        stream.reset();
        MatcherAssert.assertThat(
            stream.read(),
            Matchers.equalTo(cbyte)
        );
    }
}
