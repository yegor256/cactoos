/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2024 Yegor Bugayenko
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
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link IoCheckedBiFunc}.
 * @since 0.13
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class IoCheckedBiFuncTest {

    @Test
    void rethrowsIoException() {
        final IOException exception = new IOException("intended");
        new Assertion<>(
            "Must rethrow IOException",
            () -> new IoCheckedBiFunc<>(
                (fst, scd) -> {
                    throw exception;
                }
            ).apply(1, 2),
            new Throws<>(
                exception.getMessage(),
                exception.getClass()
            )
        ).affirm();
    }

    @Test
    void rethrowsCheckedToIoException() {
        new Assertion<>(
            "IOException should be rethrown",
            () -> new IoCheckedBiFunc<>(
                (fst, scd) -> {
                    throw new IOException("intended to fail");
                }
            ).apply(1, 2),
            new Throws<>(IOException.class)
        ).affirm();
    }

    @Test
    void runtimeExceptionGoesOut() {
        new Assertion<>(
            "Runtime exception should be rethrown",
            () -> new IoCheckedBiFunc<>(
                (fst, scd) -> {
                    throw new IllegalStateException("intended to fail here");
                }
            ).apply(1, 2),
            new Throws<>(IllegalStateException.class)
        ).affirm();
    }
}
