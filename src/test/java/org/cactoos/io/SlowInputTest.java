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

import java.nio.charset.StandardCharsets;
import org.cactoos.text.TextOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link SlowInput}.
 *
 * @since 0.12
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class SlowInputTest {

    @Test
    public void calculatesLength() {
        final String text = "What's up, друг?";
        MatcherAssert.assertThat(
            "Can't calculate the length of Input",
            new LengthOf(
                new SlowInput(
                    new InputOf(
                        new TextOf(text)
                    )
                )
            ).intValue(),
            Matchers.equalTo(text.getBytes(StandardCharsets.UTF_8).length)
        );
    }

    @Test
    public void readsFileContentSlowly() {
        final long size = 100_000L;
        MatcherAssert.assertThat(
            "Can't calculate length if the input is slow",
            new LengthOf(
                new SlowInput(size)
            ).longValue(),
            Matchers.equalTo(size)
        );
    }

}

