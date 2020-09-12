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

import java.util.concurrent.atomic.AtomicBoolean;
import org.cactoos.Scalar;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsTrue;

/**
 * Test case for {@link BiFuncOf}.
 *
 * @since 0.20
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class BiFuncOfTest {

    @Test
    void convertsFuncIntoBiFunc() throws Exception {
        new Assertion<>(
            "Must convert function into bi-function",
            new BiFuncOf<>(
                input -> 1
            ).apply(1, 2),
            new IsEqual<>(1)
        ).affirm();
    }

    @Test
    void convertsProcIntoBiFunc() throws Exception {
        final AtomicBoolean done = new AtomicBoolean(false);
        new Assertion<>(
            "Must convert procedure into bi-function",
            new BiFuncOf<String, Integer, Boolean>(
                input -> done.set(true),
                true
            ).apply("hello world", 1),
            new IsEqual<>(done.get())
        ).affirm();
    }

    @Test
    void convertsRunnableIntoBiFunc() throws Exception {
        final AtomicBoolean done = new AtomicBoolean(false);
        new Assertion<>(
            "Must convert runnable into bi-function",
            new BiFuncOf<String, Integer, Boolean>(
                () -> done.set(true),
                true
            ).apply("hello, world", 1),
            new IsTrue()
        ).affirm();
    }

    @Test
    void convertsValueIntoBiFunc() throws Exception {
        new Assertion<>(
            "Must convert value into bi-function",
            new BiFuncOf<String, Integer, Boolean>(
                true
            ).apply("hello, dude!", 1),
            new IsTrue()
        ).affirm();
    }

    @Test
    void convertsScalarIntoBiFunc() throws Exception {
        final Scalar<Boolean> scalar = () -> true;
        new Assertion<>(
            "Must convert scalar into bi-function",
            new BiFuncOf<Boolean, Boolean, Boolean>(scalar).apply(false, false),
            new IsTrue()
        ).affirm();
    }
}
