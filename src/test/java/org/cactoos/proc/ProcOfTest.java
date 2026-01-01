/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import java.util.concurrent.atomic.AtomicReference;
import org.cactoos.func.FuncOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Satisfies;

/**
 * Test case for {@link ProcOf}.
 *
 * @since 0.3
 */
final class ProcOfTest {
    @Test
    void worksWithFunc() {
        final AtomicReference<Object> done = new AtomicReference<>();
        new Assertion<>(
            "Must execute Proc with Func",
            new ProcOf<>(
                new FuncOf<>(
                    input -> {
                        done.set(input);
                        return true;
                    }
                )
            ),
            new Satisfies<>(
                proc -> {
                    final Object input = new Object();
                    proc.exec(input);
                    return done.get().equals(input);
                }
            )
        ).affirm();
    }

    @Test
    void worksWithLambda() {
        final AtomicReference<Object> done = new AtomicReference<>();
        new Assertion<>(
            "Must execute Proc with Lambda",
            new ProcOf<>(
                done::set
            ),
            new Satisfies<>(
                proc -> {
                    final Object input = new Object();
                    proc.exec(input);
                    return done.get().equals(input);
                }
            )
        ).affirm();
    }
}
