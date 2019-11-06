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
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test case for {@link ImmutableListIterator}.
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle JavadocTypeCheck (500 lines)
 * @checkstyle MagicNumberCheck (500 lines)
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.AvoidDuplicateLiterals"})
public final class ImmutableListIteratorTest {

    @Test
    public void getsPreviousIndex() {
        new Assertion<>(
            "List iterator returns incorrect previous index",
            new ImmutableListIterator<>(
                new ListOf<>(1).listIterator()
            ).previousIndex(),
            new IsEqual<>(-1)
        ).affirm();
    }

    @Test
    public void getsPrevious() {
        new Assertion<>(
            "List iterator returns incorrect previous item",
            new ImmutableListIterator<>(
                new ListOf<>(3, 7).listIterator(1)
            ).previous(),
            new IsEqual<>(3)
        ).affirm();
    }

    @Test
    public void getsNextIndex() {
        new Assertion<>(
            "List iterator returns incorrect next index",
            new ImmutableListIterator<>(
                new ListOf<>(1).listIterator()
            ).nextIndex(),
            new IsEqual<>(0)
        ).affirm();
    }

    @Test
    public void getsNext() {
        new Assertion<>(
            "List iterator returns incorrect next item",
            new ImmutableListIterator<>(
                new ListOf<>(5, 11, 13).listIterator(1)
            ).next(),
            new IsEqual<>(11)
        ).affirm();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void doesNotSupportRemove() {
        final ListIterator<Integer> iterator = new ImmutableListIterator<>(
            new ListOf<>(1, 2).listIterator()
        );
        iterator.remove();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void doesNotSupportAdd() {
        final ListIterator<Integer> iterator = new ImmutableListIterator<>(
            new ListOf<>(1, 2).listIterator()
        );
        iterator.add(1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void doesNotSupportSet() {
        final ListIterator<Integer> iterator = new ImmutableListIterator<>(
            new ListOf<>(1, 2).listIterator()
        );
        iterator.set(1);
    }
}
