/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
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
package org.cactoos.list;

import java.util.ListIterator;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test cases for {@link ListIteratorNoNulls}.
 *
 * @since 0.35
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
public final class ListIteratorNoNullsTest {

    @Test
    public void getThrowsErrorIfListIteratorNextValueIsNullValue() {
        new Assertion<>(
            "must throw error if removed value is null",
            () -> new ListIteratorNoNulls<>(
                new ListOf<>(null, 2, 3).listIterator()
            ).next(),
            new Throws<>(
                "Next item is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    public void getThrowsErrorIfListIteratorPreviousValueIsNullValue() {
        final ListIterator<Integer> listiterator =
            new ListOf<>(null, 2, 3).listIterator();
        listiterator.next();
        new Assertion<>(
            "must throw error if previous value is null",
            () -> new ListIteratorNoNulls<>(listiterator).previous(),
            new Throws<>(
                "Previous item is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    public void addThrowsErrorForImmutableListIterator() {
        new Assertion<>(
            "must throw error if modified with add",
            () -> {
                new ListIteratorNoNulls<>(
                    new ListOf<>(1, 2, 3).listIterator()
                ).add(4);
                return 0;
            },
            new Throws<>(
                "Iterator is read-only and doesn't allow adding items",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    public void removeThrowsErrorForImmutableListIterator() {
        new Assertion<>(
            "must throw error if modified with remove",
            () -> {
                new ListIteratorNoNulls<>(
                    new ListOf<>(1, 2, 3).listIterator()
                ).remove();
                return 0;
            },
            new Throws<>(
                "Iterator is read-only and doesn't allow removing items",
                UnsupportedOperationException.class
            )
        ).affirm();
    }

    @Test
    public void setThrowsErrorForImmutableListIterator() {
        new Assertion<>(
            "must throw error if modified with set",
            () -> {
                new ListIteratorNoNulls<>(
                    new ListOf<>(1, 2, 3).listIterator()
                ).set(4);
                return 0;
            },
            new Throws<>(
                "Iterator is read-only and doesn't allow rewriting items",
                UnsupportedOperationException.class
            )
        ).affirm();
    }
}
