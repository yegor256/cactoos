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

import java.util.List;
import org.cactoos.collection.BehavesAsCollection;
import org.hamcrest.Description;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matcher for collection.
 * @param <E> Type of source item
 * @since 0.23
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class BehavesAsList<E> extends TypeSafeMatcher<List<E>>  {

    /**
     * Sample item.
     */
    private final E sample;

    /**
     * Ctor.
     * @param item Sample item
     */
    public BehavesAsList(final E item) {
        super();
        this.sample = item;
    }

    @Override
    public boolean matchesSafely(final List<E> list) {
        MatcherAssert.assertThat(list.get(0), Matchers.notNullValue());
        MatcherAssert.assertThat(
            list.indexOf(this.sample),
            Matchers.greaterThanOrEqualTo(0)
        );
        MatcherAssert.assertThat(
            list.lastIndexOf(this.sample),
            Matchers.greaterThanOrEqualTo(0)
        );
        MatcherAssert.assertThat(
            list.listIterator().hasNext(),
            Matchers.is(true)
        );
        MatcherAssert.assertThat(
            list.subList(0, 1).iterator().hasNext(),
            Matchers.is(true)
        );
        return new BehavesAsCollection<E>(this.sample).matchesSafely(list);
    }

    @Override
    public void describeTo(final Description desc) {
        desc.appendText("not a valid list");
    }
}
