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
package org.cactoos.func;

import java.io.IOException;
import java.io.UncheckedIOException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link UncheckedBiFunc}.
 * @since 0.13
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class UncheckedBiFuncTest {

    @Test(expected = UncheckedIOException.class)
    public void rethrowsCheckedToUncheckedException() {
        new UncheckedBiFunc<>(
            (fst, scd) -> {
                throw new IOException("intended");
            }
        ).apply(1, 2);
    }

    @Test
    public void testUncheckedBiFunc() {
        MatcherAssert.assertThat(
            new UncheckedBiFunc<>(
                (fst, scd) -> true
            ).apply(1, 2),
            Matchers.equalTo(true)
        );
    }

    @Test(expected = IllegalStateException.class)
    public void runtimeExceptionGoesOut() {
        new UncheckedBiFunc<>(
            (fst, scd) -> {
                throw new IllegalStateException("intended to fail");
            }
        ).apply(1, 2);
    }

}
