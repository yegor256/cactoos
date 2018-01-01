/**
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
package org.cactoos.collection;

import java.util.Collection;
import org.cactoos.list.ListOf;
import org.hamcrest.Description;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matcher for collection.
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @param <E> Type of source item
 * @since 0.23
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class BehavesAsCollection<E> extends
    TypeSafeMatcher<Collection<E>>  {

    /**
     * Sample item.
     */
    private final E sample;

    /**
     * Ctor.
     * @param item Sample item
     */
    public BehavesAsCollection(final E item) {
        super();
        this.sample = item;
    }

    @Override
    @SuppressWarnings({ "unchecked", "PMD.ClassCastExceptionWithToArray" })
    public boolean matchesSafely(final Collection<E> col) {
        MatcherAssert.assertThat(col, Matchers.hasItem(this.sample));
        MatcherAssert.assertThat(col, Matchers.not(Matchers.emptyIterable()));
        MatcherAssert.assertThat(
            col, Matchers.hasSize(Matchers.greaterThan(0))
        );
        MatcherAssert.assertThat(
            new ListOf<>((E[]) col.toArray()),
            Matchers.hasItem(this.sample)
        );
        final E[] array = (E[]) new Object[col.size()];
        col.toArray(array);
        MatcherAssert.assertThat(
            new ListOf<>(array), Matchers.hasItem(this.sample)
        );
        MatcherAssert.assertThat(
            col.containsAll(new ListOf<>(this.sample)), Matchers.is(true)
        );
        return true;
    }

    @Override
    public void describeTo(final Description desc) {
        desc.appendText("not a valid collection");
    }
}
