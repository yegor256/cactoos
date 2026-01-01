/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
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
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class BiFuncOfTest {

    @Test
    void convertsFuncIntoBiFunc() {
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
    void convertsProcIntoBiFunc() {
        final AtomicReference<Object> done = new AtomicReference<>();
        final Object result = new Object();
        new Assertion<>(
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
    void convertsLambdaIntoBiFunc() {
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
