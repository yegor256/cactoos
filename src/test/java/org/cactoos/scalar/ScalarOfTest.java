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
package org.cactoos.scalar;

import java.util.concurrent.atomic.AtomicBoolean;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link ScalarOf}.
 *
 * @since 0.2
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class ScalarOfTest {

    @Test
    void convertsRunnableIntoCallable() throws Exception {
        final AtomicBoolean flag = new AtomicBoolean(false);
        new ScalarOf<>(
            () -> flag.set(true),
            true
        ).value();
        new Assertion<>(
            "must have been set by callable",
            flag.get(),
            new IsEqual<>(true)
        ).affirm();
    }

    @Test
    void convertsProcIntoCallable() throws Exception {
        final AtomicBoolean flag = new AtomicBoolean(false);
        new Assertion<>(
            "must return predefined result",
            new ScalarOf<>(
                bool -> {
                    flag.set(bool);
                },
                true,
                false
            ).value(),
            new IsEqual<>(false)
        ).affirm();
        new Assertion<>(
            "must have been set by callable",
            flag.get(),
            new IsEqual<>(true)
        ).affirm();
    }

    @Test
    void convertsFuncIntoCallable() throws Exception {
        new Assertion<>(
            "must return the application of func",
            new ScalarOf<>(
                num -> num + 1,
                1
            ).value(),
            new IsEqual<>(2)
        ).affirm();
    }

}
