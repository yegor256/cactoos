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

import java.util.concurrent.atomic.AtomicReference;
import org.cactoos.proc.ProcOf;
import org.cactoos.scalar.Constant;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.MatcherOf;

/**
 * Test case for {@link FuncOf}.
 *
 * @since 0.20
 */
final class FuncOfTest {
    @Test
    void convertsProcIntoFunc() throws Exception {
        final AtomicReference<Object> done = new AtomicReference<>();
        final Object result = new Object();
        new Assertion<>(
            "Must convert Proc into Func",
            new FuncOf<>(
                new ProcOf<>(
                    input -> {
                        done.set(input);
                    }
                ),
                result
            ),
            new MatcherOf<>(
                func -> {
                    final Object input = new Object();
                    final Object res = func.apply(input);
                    return res.equals(result) && done.get().equals(input);
                }
            )
        ).affirm();
    }

    @Test
    void convertsScalarIntoFunc() throws Exception {
        final Object result = new Object();
        new Assertion<>(
            "Must convert Scalar into Func",
            new FuncOf<>(new Constant<>(result)),
            new MatcherOf<>(
                func -> {
                    final Object res = func.apply("discarded");
                    return res.equals(result);
                }
            )
        ).affirm();
    }

    @Test
    void convertsLambdaIntoFunc() throws Exception {
        new Assertion<>(
            "Must convert Lambda into Func",
            new FuncOf<>(input -> input),
            new MatcherOf<>(
                func -> {
                    final Object input = new Object();
                    final Object res = func.apply(input);
                    return res.equals(input);
                }
            )
        ).affirm();
    }
}
