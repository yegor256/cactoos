/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.util.concurrent.atomic.AtomicReference;
import org.cactoos.proc.ProcOf;
import org.cactoos.scalar.Constant;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Satisfies;

/**
 * Test case for {@link FuncOf}.
 *
 * @since 0.20
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class FuncOfTest {
    @Test
    void convertsProcIntoFunc() {
        final AtomicReference<Object> done = new AtomicReference<>();
        final Object result = new Object();
        new Assertion<>(
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
                    final Object res = func.apply(input);
                    return res.equals(result) && done.get().equals(input);
                }
            )
        ).affirm();
    }

    @Test
    void convertsScalarIntoFunc() {
        final Object result = new Object();
        new Assertion<>(
            "Must convert Scalar into Func",
            new FuncOf<>(new Constant<>(result)),
            new Satisfies<>(
                func -> {
                    final Object res = func.apply("discarded");
                    return res.equals(result);
                }
            )
        ).affirm();
    }

    @Test
    void convertsLambdaIntoFunc() {
        new Assertion<>(
            "Must convert Lambda into Func",
            new FuncOf<>(input -> input),
            new Satisfies<>(
                func -> {
                    final Object input = new Object();
                    final Object res = func.apply(input);
                    return res.equals(input);
                }
            )
        ).affirm();
    }
}
