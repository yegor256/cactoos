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

import java.util.concurrent.atomic.AtomicReference;
import org.cactoos.func.FuncOf;
import org.cactoos.proc.ProcOf;
import org.cactoos.proc.RunnableOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.Satisfies;

/**
 * Test case for {@link ScalarOf}.
 *
 * @since 0.48
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class ScalarOfTest {
    @Test
    void worksWithCallable() {
        final Object obj = new Object();
        new Assertion<>(
            "Must convert Callable into Scalar",
            new ScalarOf<>(new CallableOf<>(new Constant<>(obj))),
            new HasValue<>(obj)
        ).affirm();
    }

    @Test
    void worksWithRunnable() {
        final Object obj = new Object();
        final Object result = new Object();
        final AtomicReference<Object> done = new AtomicReference<>();
        new Assertion<>(
            "Must convert Runnable into Scalar",
            new ScalarOf<>(
                new RunnableOf(
                    () -> {
                        done.set(result);
                    }
                ),
                obj
            ),
            new Satisfies<>(
                scalar -> {
                    final Object res = scalar.value();
                    return res.equals(obj) && done.get().equals(result);
                }
            )
        ).affirm();
    }

    @Test
    void worksWithFunc() {
        final Object ipt = new Object();
        new Assertion<>(
            "Must convert Func into Scalar",
            new ScalarOf<>(new FuncOf<>(input -> input), ipt),
            new HasValue<>(ipt)
        ).affirm();
    }

    @Test
    void worksWithProc() {
        final Object ipt = new Object();
        final Object result = new Object();
        final AtomicReference<Object> done = new AtomicReference<>();
        new Assertion<>(
            "Must convert Proc into Scalar",
            new ScalarOf<>(
                new ProcOf<>(
                    input -> {
                        done.set(input);
                    }
                ),
                ipt,
                result
            ),
            new Satisfies<>(
                scalar -> {
                    final Object res = scalar.value();
                    return res.equals(result) && done.get().equals(ipt);
                }
            )
        ).affirm();
    }

    @Test
    void worksWithLambda() {
        final Object obj = new Object();
        new Assertion<>(
            "Must convert Lambda into Scalar",
            new ScalarOf<>(() -> obj),
            new HasValue<>(obj)
        ).affirm();
    }
}
