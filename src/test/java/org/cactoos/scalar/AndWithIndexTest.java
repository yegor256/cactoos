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
package org.cactoos.scalar;

import java.util.LinkedList;
import java.util.List;
import org.cactoos.func.BiFuncOf;
import org.cactoos.iterable.IterableOf;
import org.cactoos.proc.BiProcOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasSize;
import org.llorllale.cactoos.matchers.HasValue;

/**
 * Test case for {@link AndWithIndex}.
 *
 * @since 0.8
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
final class AndWithIndexTest {

    @Test
    void iteratesListWithIndexFromBiFunc() {
        final List<String> list = new LinkedList<>();
        new Assertion<>(
            "Must iterate a list with a function",
            new AndWithIndex(
                new BiFuncOf<>(
                    (text, index) -> list.add(index, text),
                    true
                ),
                "hello", "world"
            ),
            new HasValue<>(true)
        ).affirm();
        new Assertion<>(
            "Must have populated the list",
            list,
            new HasSize(2)
        ).affirm();
    }

    @Test
    void iteratesListWithIndexFromBiProc() {
        final List<String> list = new LinkedList<>();
        new Assertion<>(
            "Must iterate a list with a procedure",
            new AndWithIndex(
                new BiProcOf<>(
                    (text, index) -> {
                        list.add(index, text);
                    }
                ),
                new IterableOf<>("hello", "world")
            ),
            new HasValue<>(true)
        ).affirm();
        new Assertion<>(
            "Must have populated the list",
            list,
            new HasSize(2)
        ).affirm();
    }
}
