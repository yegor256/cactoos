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
package org.cactoos.map;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;
import org.hamcrest.collection.IsMapContaining;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link Grouped}.
 *
 * @since 0.30
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class GroupedTest {

    @Test
    public void groupedByNumber() throws Exception {
        // @checkstyle MagicNumberCheck (1 line)
        new Assertion<>(
            "Can't behave as a map",
            new Grouped<Integer, String, Integer>(
                // @checkstyle MagicNumberCheck (1 line)
                new IterableOf<>(1, 1, 1, 4, 5, 6, 7, 8, 9),
                number -> number,
                Object::toString
            ),
            new BehavesAsMap<Integer, List<String>>(1, new ListOf<>("1", "1", "1"))
        ).affirm();
    }

    @Test
    public void emptyIterable() throws Exception {
        new Assertion<>(
            "Can't build grouped by empty iterable",
            new Grouped<>(
                new IterableOf<Integer>(),
                number -> number,
                Object::toString
            ).entrySet(),
            new IsEqual<Set<?>>(new HashSet<>())
        ).affirm();
    }

    @Test
    public void groupedByOneHasEntries() throws Exception {
        // @checkstyle MagicNumberCheck (1 line)
        new Assertion<>(
            "Can't group int values",
            new Grouped<Integer, String, Integer>(
                // @checkstyle MagicNumberCheck (1 line)
                new IterableOf<>(1, 1, 1, 4, 5, 6, 7, 8, 9),
                number -> number,
                Object::toString
            ),
            new IsMapContaining<Integer, List<String>>(
                new IsEqual<>(1),
                new IsEqual<>(new ListOf<>("1", "1", "1"))
            )
        ).affirm();
    }
}
