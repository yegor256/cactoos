/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.set;

import java.util.Iterator;
import java.util.Set;
import org.cactoos.collection.BehavesAsCollection;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsEqual;
import org.llorllale.cactoos.matchers.Assertion;

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
        new Assertion<>(
            "Does not contain duplicates",
            this.occurrences(item.iterator()),
            new IsEqual<>(1)
        ).affirm();
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
