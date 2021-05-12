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
package org.cactoos.proc;

import java.util.LinkedList;
import java.util.List;
import org.cactoos.Proc;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;

/**
 * Test case for {@link ForEach}.
 *
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
final class ForEachTest {

    @Test
    void testProcIterable() throws Exception {
        final List<Integer> list = new LinkedList<>();
        new ForEach<Integer>(
            list::add
        ).exec(
            new IterableOf<>(
                1, 1
            )
        );
        new Assertion<>(
            "List does not contain mapped Iterable elements",
            list,
            new IsEqual<>(
                new ListOf<>(
                    1, 1
                )
            )
        ).affirm();
    }

    @Test
    @SuppressWarnings("unchecked")
    void worksWithGenerics() throws Exception {
        final List<? super Number> list = new LinkedList<>();
        new ForEach<>(
            (Proc<? super Number>) list::add
        ).exec(
            new IterableOf<>(
                Integer.valueOf("1"),
                Double.valueOf("2"),
                Long.valueOf("3")
            )
        );
        new Assertion<>(
            "Must contain elements",
            list,
            new HasValues<>(1, 2.0d, 3L)
        ).affirm();
    }

}
