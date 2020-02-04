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
package org.cactoos.iterator;

import java.util.NoSuchElementException;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasSize;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Joined}.
 * @since 0.14
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class JoinedTest {

    @Test
    public void joinsIterators() {
        new Assertion<>(
            "Must concatenate mapped iterators together",
            new IterableOf<>(
                new Joined<>(
                    new Mapped<>(
                        IteratorOf::new,
                        new IterableOf<>("x", "y")
                    )
                )
            ),
            new HasSize(2)
        ).affirm();
    }

    @Test
    public void callsNextDirectlyOnNonEmptyIterator() {
        new Assertion<>(
            "Must call next method directly on non-empty iterator",
            new Joined<Integer>(
                new IteratorOf<>(1),
                new IteratorOf<>(2)
            ).next(),
            new IsEqual<>(1)
        ).affirm();
    }

    @Test
    public void throwsExceptionWhenCallNextOnEmptyIterator() {
        new Assertion<>(
            "Must throw an exception",
            () -> new Joined<Integer>(new IteratorOf<>()).next(),
            new Throws<>(NoSuchElementException.class)
        ).affirm();
    }
}
