/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2022 Yegor Bugayenko
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
package org.cactoos.proc;

import java.util.concurrent.atomic.AtomicReference;
import org.cactoos.scalar.ScalarOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Satisfies;

/**
 * Test case for {@link RunnableOf}.
 *
 * @since 0.2
 */
final class RunnableOfTest {

    @Test
    void convertsProcIntoRunnable() {
        final AtomicReference<Object> done = new AtomicReference<>();
        final Object obj = new Object();
        new Assertion<>(
            "Must execute Runnable with Proc",
            new RunnableOf(
                new ProcOf<>(
                    done::set
                ),
                obj
            ),
            new Satisfies<>(
                runnable -> {
                    runnable.run();
                    return done.get().equals(obj);
                }
            )
        ).affirm();
    }

    @Test
    void convertsScalarIntoRunnable() {
        final AtomicReference<Object> done = new AtomicReference<>();
        final Object obj = new Object();
        new Assertion<>(
            "Must execute Runnable with Scalar",
            new RunnableOf(
                new ScalarOf<>(
                    () -> {
                        done.set(obj);
                        return "discarded";
                    }
                )
            ),
            new Satisfies<>(
                runnable -> {
                    runnable.run();
                    return done.get().equals(obj);
                }
            )
        ).affirm();
    }

    @Test
    void convertsLambdaIntoRunnable() {
        final AtomicReference<Object> done = new AtomicReference<>();
        final Object obj = new Object();
        new Assertion<>(
            "Must execute Runnable with Lambda",
            new RunnableOf(
                () -> {
                    done.set(obj);
                }
            ),
            new Satisfies<>(
                runnable -> {
                    runnable.run();
                    return done.get().equals(obj);
                }
            )
        ).affirm();
    }

}
