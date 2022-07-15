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
package org.cactoos.func;

import java.util.concurrent.atomic.AtomicReference;
import org.cactoos.BiFunc;
import org.cactoos.proc.ProcOf;
import org.cactoos.scalar.Constant;
import org.hamcrest.core.IsSame;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Satisfies;

/**
 * Test case for {@link BiFuncOf}.
 *
 * @since 0.20
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class BiFuncOfTest {

    @Test
    void convertsFuncIntoBiFunc() throws Exception {
        new Assertion<>(
            "Must convert function into bi-function",
            new BiFuncOf<>(
                new FuncOf<>(input -> input)
            ),
            new Satisfies<>(
                func -> {
                    final Object first = new Object();
                    final Object res = func.apply(first, "discarded");
                    return res.equals(first);
                }
            )
        ).affirm();
    }

    @Test
    void convertsProcIntoBiFunc() throws Exception {
        final AtomicReference<Object> done = new AtomicReference<>();
        final Object result = new Object();
        new Assertion<>(
            "Must convert procedure into bi-function",
            new BiFuncOf<>(
                new ProcOf<>(
                    input -> {
                        done.set(input);
                    }
                ),
                result
            ),
            new Satisfies<>(
                func -> {
                    final Object first = new Object();
                    final Object res = func.apply(first, "discarded");
                    return res.equals(result) && done.get().equals(first);
                }
            )
        ).affirm();
    }

    @Test
    void convertsScalarIntoBiFunc() throws Exception {
        final Object obj = new Object();
        new Assertion<>(
            "Must convert scalar into bi-function",
            new BiFuncOf<>(new Constant<>(obj)).apply("discarded", "discarded"),
            new IsSame<>(obj)
        ).affirm();
    }

    @Test
    void convertsLambdaIntoBiFunc() throws Exception {
        new Assertion<>(
            "Must convert lambda into bi-function",
            new BiFuncOf<>((first, second) -> new Object[] {first, second}),
            new Satisfies<BiFunc<Object, Object, Object[]>>(
                func -> {
                    final Object first = new Object();
                    final Object second = new Object();
                    final Object[] res = func.apply(first, second);
                    return res.length == 2 && res[0].equals(first) && res[1].equals(second);
                }
            )
        ).affirm();
    }
}
