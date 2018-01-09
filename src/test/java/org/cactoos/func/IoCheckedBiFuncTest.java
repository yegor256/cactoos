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
package org.cactoos.func;

import java.io.IOException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link IoCheckedBiFunc}.
 * @author Mehmet Yildirim (memoyil@gmail.com)
 * @version $Id$
 * @since 0.13
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class IoCheckedBiFuncTest {

    @Test
    public void rethrowsIoException() {
        final IOException exception = new IOException("intended");
        try {
            new IoCheckedBiFunc<>(
                (fst, scd) -> {
                    throw exception;
                }
            ).apply(1, 2);
        } catch (final IOException ex) {
            MatcherAssert.assertThat(
                ex, Matchers.is(exception)
            );
        }
    }

    @Test(expected = IOException.class)
    public void rethrowsCheckedToIoException() throws Exception {
        new IoCheckedBiFunc<>(
            (fst, scd) -> {
                throw new Exception("intended to fail");
            }
        ).apply(1, 2);
    }

    @Test(expected = IllegalStateException.class)
    public void runtimeExceptionGoesOut() throws IOException {
        new IoCheckedBiFunc<>(
            (fst, scd) -> {
                throw new IllegalStateException("intended to fail here");
            }
        ).apply(1, 2);
    }
}
