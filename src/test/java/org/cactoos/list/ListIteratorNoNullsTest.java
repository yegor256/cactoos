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
package org.cactoos.list;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test cases for {@link ListIteratorNoNulls}.
 *
 * @since 0.35
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class ListIteratorNoNullsTest {

    @Test
    void mustThrowsErrorIfListIteratorNextValueIsNull() {
        new Assertion<>(
            "must throw error next item is null",
            () -> {
                new ListIteratorNoNulls<>(
                    new ListOf<>(null, 2, 3).listIterator()
                ).next();
                return 0;
            },
            new Throws<>(
                "Next item is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void mustThrowsErrorIfListIteratorPreviousValueIsNull() {
        new Assertion<>(
            "must throw error if previous value is null",
            () -> {
                new ListIteratorNoNulls<>(
                    new ListOf<>(
                        null, 2, 3
                    ).listIterator(1)
                ).previous();
                return 0;
            },
            new Throws<>(
                "Previous item is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    void mustAddToListIterator() {
        new Assertion<>(
            "must add to list iterator",
            () -> {
                final List<Integer> list = new ArrayList<>(2);
                list.add(1);
                list.add(2);
                final ListIterator<Integer> iterator = new ListIteratorNoNulls<>(
                    list.listIterator()
                );
                iterator.next();
                iterator.add(4);
                return iterator.previous();
            },
            new HasValue<>(4)
        ).affirm();
    }

    @Test
    void mustRemoveFromListIterator() {
        new Assertion<>(
            "must remove element from list iterator",
            () -> {
                final List<Integer> list = new ArrayList<>(2);
                list.add(1);
                list.add(2);
                final ListIterator<Integer> iterator = new ListIteratorNoNulls<>(
                    list.listIterator()
                );
                iterator.next();
                iterator.remove();
                return iterator.previous();
            },
            new Throws<>(
                NoSuchElementException.class
            )
        ).affirm();
    }

    @Test
    void mustSetValueListIterator() {
        new Assertion<>(
            "must set element into list iterator",
            () -> {
                final List<Integer> list = new ArrayList<>(2);
                list.add(1);
                list.add(2);
                final ListIterator<Integer> iterator = new ListIteratorNoNulls<>(
                    list.listIterator()
                );
                iterator.next();
                iterator.set(4);
                return iterator.previous();
            },
            new HasValue<>(4)
        ).affirm();
    }

    @Test
    void mustThrowsErrorIfAddANullItem() {
        new Assertion<>(
            "must throw error if add a null item",
            () -> {
                new ListIteratorNoNulls<>(
                    new ListOf<>(1, 2, 3).listIterator()
                ).add(null);
                return 0;
            },
            new Throws<>(
                "Item can't be NULL in #add(T)",
                IllegalArgumentException.class
            )
        ).affirm();
    }

    @Test
    void mustThrowsErrorIfSetANullItem() {
        new Assertion<>(
            "must throw error if set a null item",
            () -> {
                new ListIteratorNoNulls<>(
                    new ListOf<>(1, 2, 3).listIterator()
                ).set(null);
                return 0;
            },
            new Throws<>(
                "Item can't be NULL in #set(T)",
                IllegalArgumentException.class
            )
        ).affirm();
    }
}
