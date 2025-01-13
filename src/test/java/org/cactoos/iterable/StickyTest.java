/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2025 Yegor Bugayenko
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
package org.cactoos.iterable;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.LengthOf;
import org.hamcrest.collection.IsEmptyIterable;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link Sticky}.
 *
 * @since 0.8
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class StickyTest {

    @Test
    void ignoresChangesInIterable() throws Exception {
        final AtomicInteger size = new AtomicInteger(2);
        final Iterable<Integer> list = new Sticky<>(
            new ListOf<>(
                () -> Collections.nCopies(size.incrementAndGet(), 0).iterator()
            )
        );
        new Assertion<>(
            "Must ignore the changes in the underlying iterable",
            new LengthOf(list).value(),
            new IsEqual<>(new LengthOf(list).value())
        ).affirm();
    }

    @Test
    void testEmpty() {
        new Assertion<>(
            "Must be empty",
            new Sticky<>(),
            new IsEmptyIterable<>()
        ).affirm();
    }

    @Test
    void testEqualsIterable() {
        new Assertion<>(
            "Must be equals to equivalent iterable",
            new Sticky<>(1, 2),
            new IsEqual<>(new IterableOf<>(1, 2))
        ).affirm();
    }
}
