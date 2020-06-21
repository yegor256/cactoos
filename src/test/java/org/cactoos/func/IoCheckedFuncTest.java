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
package org.cactoos.func;

import java.io.IOException;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link IoCheckedFunc}.
 *
 * @since 0.4
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class IoCheckedFuncTest {

    @Test
    public void rethrowsIoException() {
        final IOException exception = new IOException("intended");
        new Assertion<>(
            "Must rethrow original IOException",
            () -> new IoCheckedFunc<>(
                i -> {
                    throw exception;
                }
            ).apply(1),
            new Throws<>(
                exception.getMessage(),
                exception.getClass()
            )
        ).affirm();
    }

    @Test
    public void rethrowsCheckedToIoException() {
        new Assertion<>(
            "Must rethrow as IOException",
            () -> new IoCheckedFunc<>(
                i -> {
                    throw new InterruptedException("intended to fail");
                }
            ).apply(1),
            new Throws<>(
                IOException.class
            )
        ).affirm();
    }

    @Test
    public void runtimeExceptionGoesOut() {
        new Assertion<>(
            "Must throw runtime exception as is",
            () -> new IoCheckedFunc<>(
                i -> {
                    throw new IllegalStateException("intended to fail here");
                }
            ).apply(1),
            new Throws<>(
                IllegalStateException.class
            )
        ).affirm();
    }

}
