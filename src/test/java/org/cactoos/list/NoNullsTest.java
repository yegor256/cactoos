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

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test cases for {@link NoNulls}.
 * @since 0.35
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
public final class NoNullsTest {

    @Test
    public void getThrowsErrorIfNull() {
        new Assertion<>(
            "must throw error if contains null",
            () -> new NoNulls<>(
                new ListOf<>(1, null, 3)
            ).get(1),
            new Throws<>(
                "Item #1 of [1, null, 3] is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    public void setThrowsErrorIfArgumentNull() {
        new Assertion<>(
            "must throw error if set null",
            () -> new NoNulls<>(
                new ListOf<>(1, null, 3)
            ).set(2, null),
            new Throws<>(
                "Item can't be NULL in #set(2,T)",
                IllegalArgumentException.class
            )
        ).affirm();
    }

    @Test
    public void setThrowsErrorIfPreviousValueNull() {
        final ArrayList<Integer> list = new ArrayList<>(1);
        list.add(null);
        new Assertion<>(
            "must throw error if previous value is null",
            () -> new NoNulls<>(list).set(0, 2),
            new Throws<>(
                "Result of #set(0,T) is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    public void addThrowsErrorIfArgumentNull() {
        new Assertion<>(
            "must throw error if add null",
            () -> {
                new NoNulls<>(new ArrayList<>(1)).add(0, null);
                return 0;
            },
            new Throws<>(
                "Item can't be NULL in #add(0,T)",
                IllegalArgumentException.class
            )
        ).affirm();
    }

    @Test
    public void removeThrowsErrorIfValueNull() {
        final ArrayList<Integer> list = new ArrayList<>(1);
        list.add(null);
        new Assertion<>(
            "must throw error if removed value is null",
            () -> new NoNulls<>(list).remove(0),
            new Throws<>(
                "Result of #remove(0) is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }
    @Test
    public void getThrowsErrorIfListIteratorNextValueIsNullValue() {
        new Assertion<>(
            "must throw error if removed value in iterator is null",
            () -> new NoNulls<>(
                new ListOf<>(null, 2, 3)
            ).listIterator().next(),
            new Throws<>(
                "Next item is NULL",
                IllegalStateException.class
            )
        ).affirm();
    }

    @Test
    public void getThrowsErrorIfListIteratorPreviousValueIsNullValue() {
        final List<Integer> list = new ArrayList<>(2);
        list.add(1);
        list.add(2);
        final ListIterator<Integer> listiterator = new NoNulls<>(
            list
        ).listIterator();
        listiterator.next();
        list.set(0, null);
        new Assertion<>(
            "must throw error if previous value in iterator is null",
            () -> listiterator.previous(),
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
                new NoNulls<>(new ListOf<>(1, 2, 3)).listIterator().add(4);
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
                new NoNulls<>(new ListOf<>(1, 2, 3)).listIterator().remove();
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
            "must throw error if modified if set",
            () -> {
                new NoNulls<>(new ListOf<>(1, 2, 3)).listIterator().set(4);
                return 0;
            },
            new Throws<>(
                "Iterator is read-only and doesn't allow rewriting items",
                UnsupportedOperationException.class
            )
        ).affirm();
    }
}
