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
package org.cactoos.set;

import java.util.Iterator;
import java.util.Set;
import org.cactoos.collection.BehavesAsCollection;
import org.hamcrest.Description;
import org.hamcrest.MatcherAssert;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsEqual;

/**
 * Matcher for set.
 *
 * @param <T> Type of source sample
 * @since 0.49.2
 */
public final class BehavesAsSet<T> extends TypeSafeMatcher<Set<T>> {

    /**
     * Sample sample.
     */
    private final T sample;

    /**
     * Ctor.
     * @param item Sample sample
     */
    public BehavesAsSet(final T item) {
        super();
        this.sample = item;
    }

    @Override
    public boolean matchesSafely(final Set<T> item) {
        MatcherAssert.assertThat(
            "Does not contain duplicates",
            this.occurrences(item.iterator()),
            new IsEqual<>(1)
        );
        return new BehavesAsCollection<>(this.sample).matchesSafely(item);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("not a valid set");
    }

    /**
     * Occurrences of sample in iterator.
     * @param iterator Set iterator
     * @return Occurrences
     */
    private int occurrences(final Iterator<T> iterator) {
        int occurrences = 0;
        while (iterator.hasNext()) {
            if (this.sample.equals(iterator.next())) {
                ++occurrences;
            }
        }
        return occurrences;
    }
}
