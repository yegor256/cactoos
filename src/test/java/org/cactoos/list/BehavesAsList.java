/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.list;

import java.util.List;
import org.cactoos.collection.BehavesAsCollection;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsTrue;
import org.llorllale.cactoos.matchers.Satisfies;

/**
 * Matcher for collection.
 * @param <E> Type of source item
 * @since 0.23
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
        new Assertion<>(
            "must contain at least one non-null element",
            list.get(0),
            new IsNot<>(new IsNull<>())
        ).affirm();
        new Assertion<>(
            "must have an index for the sample item",
            list.indexOf(this.sample),
            new Satisfies<>(i -> i >= 0)
        ).affirm();
        new Assertion<>(
            "must have a last index for the sample item",
            list.lastIndexOf(this.sample),
            new Satisfies<>(i -> i >= 0)
        ).affirm();
        new Assertion<>(
            "must have at least one element in list iterator",
            list.listIterator().hasNext(),
            new IsTrue()
        ).affirm();
        new Assertion<>(
            "must have at least one element in sublist iterator",
            list.subList(0, 1).iterator().hasNext(),
            new IsTrue()
        ).affirm();
        return new BehavesAsCollection<>(this.sample).matchesSafely(list);
    }

    @Override
    public void describeTo(final Description desc) {
        desc.appendText("not a valid list");
    }
}
