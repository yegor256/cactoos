/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.util.concurrent.atomic.AtomicReference;
import org.cactoos.proc.ProcOf;
import org.cactoos.scalar.Constant;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Satisfies;

/**
 * Test case for {@link FuncOf}.
 *
 * @since 0.20
 */
final class FuncOfTest {
    @Test
    void convertsProcIntoFunc() {
        final AtomicReference<Object> done = new AtomicReference<>();
        final Object result = new Object();
        MatcherAssert.assertThat(
            "Must convert Proc into Func",
            new FuncOf<>(
                new ProcOf<>(
                    done::set
                ),
                result
            ),
            new Satisfies<>(
                func -> {
                    final Object input = new Object();
                    return func.apply(input).equals(result)
                        && done.get().equals(input);
                }
            )
        );
    }

    @Test
    void convertsScalarIntoFunc() {
        final Object result = new Object();
        MatcherAssert.assertThat(
            "Must convert Scalar into Func",
            new FuncOf<>(new Constant<>(result)),
            new Satisfies<>(
                func -> func.apply("discarded").equals(result)
            )
        );
    }

    @Test
    void convertsLambdaIntoFunc() {
        MatcherAssert.assertThat(
            "Must convert Lambda into Func",
            new FuncOf<>(input -> input),
            new Satisfies<>(
                func -> {
                    final Object input = new Object();
                    return func.apply(input).equals(input);
                }
            )
        );
    }
}
