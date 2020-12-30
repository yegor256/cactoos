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

import org.cactoos.func.FuncOf;
import org.cactoos.proc.ProcOf;
import org.cactoos.proc.RunnableOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.ScalarHasValue;

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
            "must hold the same value as given by callable",
            new ScalarOf<>(new CallableOf<>(new Constant<>(obj))),
            new ScalarHasValue<>(obj)
        ).affirm();
    }

    @Test
    void worksWithRunnable() {
        final Object obj = new Object();
        new Assertion<>(
            "must hold the same value as given",
            new ScalarOf<>(new RunnableOf(ignored -> { }, "ignored"), obj),
            new ScalarHasValue<>(obj)
        ).affirm();
    }

    @Test
    void worksWithFunc() {
        final Object obj = new Object();
        new Assertion<>(
            "must hold the same value as given by func",
            new ScalarOf<>(new FuncOf<>(new Constant<>(obj)), "ignored"),
            new ScalarHasValue<>(obj)
        ).affirm();
    }

    @Test
    void worksWithProc() {
        final Object obj = new Object();
        new Assertion<>(
            "must hold the expected value",
            new ScalarOf<>(new ProcOf<>(ignored -> { }), "ignored", obj),
            new ScalarHasValue<>(obj)
        ).affirm();
    }
}
