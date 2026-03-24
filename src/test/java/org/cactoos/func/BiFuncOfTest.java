/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.util.concurrent.atomic.AtomicReference;
import org.cactoos.BiFunc;
import org.cactoos.proc.ProcOf;
import org.cactoos.scalar.Constant;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Satisfies;

/**
 * Test case for {@link BiFuncOf}.
 *
 * @since 0.20
 */
final class BiFuncOfTest {

    @Test
    void convertsFuncIntoBiFunc() {
        MatcherAssert.assertThat(
            "Must convert function into bi-function",
            new BiFuncOf<>(
                new FuncOf<>(input -> input)
            ),
            new Satisfies<>(
                func -> {
                    final Object first = new Object();
                    return func.apply(first, "discarded").equals(first);
                }
            )
        );
    }

    @Test
    void convertsProcIntoBiFunc() {
        final AtomicReference<Object> done = new AtomicReference<>();
        final Object result = new Object();
        MatcherAssert.assertThat(
            "Must convert procedure into bi-function",
            new BiFuncOf<>(
                new ProcOf<>(
                    done::set
                ),
                result
            ),
            new Satisfies<>(
                func -> {
                    final Object first = new Object();
                    return func.apply(first, "discarded").equals(result)
                        && done.get().equals(first);
                }
            )
        );
    }

    @Test
    void convertsScalarIntoBiFunc() {
        MatcherAssert.assertThat(
            "Must convert scalar into bi-function",
            new BiFuncOf<>(new Constant<>(new Object())),
            new Satisfies<>(
                func -> {
                    final Object result = func.apply("discarded", "discarded");
                    return func.apply("other", "other") == result;
                }
            )
        );
    }

    @Test
    void convertsLambdaIntoBiFunc() {
        MatcherAssert.assertThat(
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
        );
    }
}
