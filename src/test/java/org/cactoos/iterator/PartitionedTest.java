/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link Partitioned}.
 *
 * @since 0.29
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumber (500 lines)
 */
public final class PartitionedTest {

    @Test
    public void emptyPartitioned() {
        MatcherAssert.assertThat(
            "Can't generate an empty Partitioned.",
            new LengthOf(
                new Partitioned<>(1, Collections.emptyIterator())
            ).intValue(),
            Matchers.equalTo(0)
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    public void partitionedOne() {
        MatcherAssert.assertThat(
            "Can't generate a Partitioned of partition size 1.",
            new ArrayList<>(
                new ListOf<>(
                    new Partitioned<>(1, new ListOf<>(1, 2, 3).iterator())
                )
            ),
            Matchers.equalTo(
                new ListOf<>(
                    Collections.singletonList(1), Collections.singletonList(2),
                    Collections.singletonList(3)
                )
            )
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    public void partitionedEqualSize() {
        MatcherAssert.assertThat(
            "Can't generate a Partitioned of partition size 2.",
            new ArrayList<>(
                new ListOf<>(
                    new Partitioned<>(2, new ListOf<>(1, 2, 3, 4).iterator())
                )
            ),
            Matchers.equalTo(
                new ListOf<>(new ListOf<>(1, 2), new ListOf<>(3, 4))
            )
        );
    }

    @Test
    @SuppressWarnings("unchecked")
    public void partitionedLastPartitionSmaller() {
        MatcherAssert.assertThat(
            "Can't generate a Partitioned of size 2 last partition smaller.",
            new ArrayList<>(
                new ListOf<>(
                    new Partitioned<>(2, new ListOf<>(1, 2, 3).iterator())
                )
            ),
            Matchers.equalTo(
                new ListOf<>(
                    new ListOf<>(1, 2),
                    Collections.singletonList(3)
                )
            )
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void partitionedWithPartitionSizeSmallerOne() {
        new Partitioned<>(0, new ListOf<>(1).iterator()).next();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void partitionedListsAreUnmodifiable() {
        new Partitioned<>(
            2, new ListOf<>(1, 2).iterator()
        ).next().clear();
    }

    @Test(expected = NoSuchElementException.class)
    public void emptyPartitionedNextThrowsException() {
        new Partitioned<>(
            2, Collections.emptyList().iterator()
        ).next();
    }

}
