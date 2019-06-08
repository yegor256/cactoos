/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
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
import java.util.concurrent.atomic.AtomicInteger;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link IoCheckedBiFunc}.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class IoCheckedBiProcTest {
    @Test
    public void executesWrappedProc() throws Exception {
        final AtomicInteger counter = new AtomicInteger();
        new IoCheckedBiProc<>(
            (first, second) -> counter.incrementAndGet()
        ).exec(true, true);
        new Assertion<>(
            "Must execute wrapped proc",
            counter.get(),
            new IsEqual<>(1)
        ).affirm();
    }

    @Test
    public void wrapsExceptions() {
        final IoCheckedBiProc<Object, Object> proc = new IoCheckedBiProc<>(
            (first, second) -> {
                throw new Exception();
            }
        );
        new Assertion<>(
            "Must wrap with IOException",
            () -> {
                proc.exec(true, true);
                return true;
            },
            new Throws<>(
                "java.lang.Exception",
                IOException.class
            )
        ).affirm();
    }

    @Test
    public void rethrowsIoException() {
        final IOException exception = new IOException("intended");
        try {
            new IoCheckedBiProc<>(
                (fst, scd) -> {
                    throw exception;
                }
            ).exec(1, 2);
        } catch (final IOException ex) {
            new Assertion<>(
                "Must re-throw IOException",
                ex,
                new IsEqual<>(exception)
            ).affirm();
        }
    }

    @Test
    public void runtimeExceptionGoesOut() {
        final String msg = "intended to fail here";
        final IoCheckedBiProc<Object, Object> proc = new IoCheckedBiProc<>(
            (fst, scd) -> {
                throw new IllegalStateException(msg);
            }
        );
        new Assertion<>(
            "Must re-throw runtime exceptions",
            () -> {
                proc.exec(true, true);
                return true;
            },
            new Throws<>(
                msg,
                IllegalStateException.class
            )
        ).affirm();
    }
}
