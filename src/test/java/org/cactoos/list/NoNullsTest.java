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
package org.cactoos.list;

import java.util.ArrayList;
import java.util.ListIterator;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test cases for {@link NoNulls}.
 * @since 0.35
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
@SuppressWarnings(
    {
        "PMD.AvoidDuplicateLiterals",
        "PMD.TooManyMethods"
    }
)
public final class NoNullsTest {

    /**
     * A rule for handling an exception.
     */
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void getThrowsErrorIfNull() {
        this.exception.expect(IllegalStateException.class);
        this.exception.expectMessage(
            "Item #1 of [1, null, 3] is NULL"
        );
        new NoNulls<>(
            new ListOf<>(1, null, 3)
        ).get(1);
    }

    @Test
    public void setThrowsErrorIfArgumentNull() {
        this.exception.expect(IllegalArgumentException.class);
        this.exception.expectMessage(
            "Item can't be NULL in #set(2,T)"
        );
        new NoNulls<>(
            new ListOf<>(1, null, 3)
        ).set(2, null);
    }

    @Test
    public void setThrowsErrorIfPreviousValueNull() {
        this.exception.expect(IllegalStateException.class);
        this.exception.expectMessage(
            "Result of #set(0,T) is NULL"
        );
        final ArrayList<Integer> list = new ArrayList<>(1);
        list.add(null);
        new NoNulls<>(list).set(0, 2);
    }

    @Test
    public void addThrowsErrorIfArgumentNull() {
        this.exception.expect(IllegalArgumentException.class);
        this.exception.expectMessage(
            "Item can't be NULL in #add(0,T)"
        );
        new NoNulls<>(
            new ArrayList<>(1)
        ).add(0, null);
    }

    @Test
    public void removeThrowsErrorIfValueNull() {
        this.exception.expect(IllegalStateException.class);
        this.exception.expectMessage(
            "Result of #remove(0) is NULL"
        );
        final ArrayList<Integer> list = new ArrayList<>(1);
        list.add(null);
        new NoNulls<>(
            list
        ).remove(0);
    }

    @Test
    public void nextTrowsErrorIfListIteratorValueNull() {
        this.exception.expect(IllegalStateException.class);
        this.exception.expectMessage(
            "Next item #1 of #listiterator() is NULL"
        );
        final ListIterator<Integer> listiterator = new NoNulls<>(
            new ListOf<Integer>(1, null, 3)
        ).listIterator();
        listiterator.next();
        listiterator.next();
    }

    @Test
    public void nextIndexThrowsErrorIfListIteratorValueNull() {
        this.exception.expect(IllegalStateException.class);
        this.exception.expectMessage(
            "Next item #0 of #listiterator() is NULL"
        );
        final ListIterator<Integer> listiterator = new NoNulls<>(
            new ListOf<Integer>(1, 2, null)
        ).listIterator(2);
        listiterator.next();
    }

    @Test
    public void getThrowsErrorIfSubListValueNull() {
        this.exception.expect(IllegalStateException.class);
        this.exception.expectMessage(
            "Item #0 of [null] is NULL"
        );
        new NoNulls<>(
            new ListOf<>(1, null, 3)
        ).subList(1, 2).get(0);
    }

    @Test
    public void getThrowsErrorIfLastIndexOfArgumentNull() {
        this.exception.expect(IllegalArgumentException.class);
        this.exception.expectMessage(
            "Item can't be NULL in #lastIndexOf(T)"
        );
        new NoNulls<>(
            new ListOf<>(1, null, 3)
        ).lastIndexOf(null);
    }

    @Test
    public void getThrowsErrorIfIndexOfArgumentNull() {
        this.exception.expect(IllegalArgumentException.class);
        this.exception.expectMessage(
            "Item can't be NULL in #indexOf(T)"
        );
        new NoNulls<>(
            new ListOf<>(1, null, 3)
        ).indexOf(null);
    }

    @Test
    public void containsNoNulls() {
        MatcherAssert.assertThat(
            "List iterator does not contains",
            new NoNulls<>(
                new ListOf<>(1)
            ).contains(1),
            new IsEqual<>(true)
        );
    }

    @Test
    public void addItemNoNulls() {
        this.exception.expect(IllegalStateException.class);
        this.exception.expectMessage(
            "Item of #add(T) is NULL"
        );
        new NoNulls<>(
            new ListOf<>(1, 2, 3)
        ).add(null);
    }

    @Test
    public void addAllItemNoNulls() {
        this.exception.expect(UnsupportedOperationException.class);
        this.exception.expectMessage(
            "#removeAll(): the collection is read-only"
        );
        new NoNulls<>(
            new ListOf<>(1, 2, 3)
        ).addAll(new ListOf<>());
    }

    @Test
    public void removeAllItemNoNulls() {
        this.exception.expect(UnsupportedOperationException.class);
        this.exception.expectMessage(
            "#removeAll(): the collection is read-only"
        );
        new NoNulls<>(
            new ListOf<>(1, 2, 3)
        ).removeAll(new ListOf<>(1, 2, 3));
    }

    @Test
    public void retainAllItemNoNulls() {
        this.exception.expect(UnsupportedOperationException.class);
        this.exception.expectMessage(
            "#retainAll(): the collection is read-only"
        );
        new NoNulls<>(
            new ListOf<>(1, 2, 3)
        ).retainAll(new ListOf<>(1));
    }

    @Test
    public void clearNoNulls() {
        this.exception.expect(UnsupportedOperationException.class);
        this.exception.expectMessage(
            "#clear(): the collection is read-only"
        );
        new NoNulls<>(
            new ListOf<>(1, 2, 3)
        ).clear();
    }

    @Test
    public void sizeNoNulls() {
        MatcherAssert.assertThat(
            "List iterator returns incorrect size",
            new NoNulls<>(
                new ListOf<>(1)
            ).size(),
            new IsEqual<>(1)
        );
    }

    @Test
    public void isEmptyNoNulls() {
        MatcherAssert.assertThat(
            "List iterator returns is empty",
            new NoNulls<>(
                new ListOf<>(1)
            ).isEmpty(),
            new IsEqual<>(false)
        );
    }

    @Test
    public void removeThrowsErrorForNoNulls() {
        this.exception.expect(UnsupportedOperationException.class);
        this.exception.expectMessage(
            "#remove()"
        );
        new NoNulls<>(
            new ListOf<>(1, 2, 3)
        ).remove(Integer.valueOf("1"));
    }

}
