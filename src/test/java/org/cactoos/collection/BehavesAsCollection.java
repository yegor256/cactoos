/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.collection;

import java.util.Collection;
import org.cactoos.list.ListOf;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.collection.IsCollectionWithSize;
import org.hamcrest.collection.IsEmptyCollection;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;
import org.llorllale.cactoos.matchers.Satisfies;

/**
 * Matcher for collection.
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
        new Assertion<>(
            "Must contain item",
            col,
            new HasValues<>(this.sample)
        ).affirm();
        new Assertion<>(
            "Must not be empty",
            col,
            new IsNot<>(
                new IsEmptyCollection<>()
            )
        ).affirm();
        new Assertion<>(
            "Size must be more than 0",
            col,
            new IsCollectionWithSize<>(
                new Satisfies<>(s -> s > 0)
            )
        ).affirm();
        new Assertion<>(
            "Array must contain item",
            new ListOf<>(
                (E[]) col.toArray()
            ),
            new HasValues<>(this.sample)
        ).affirm();
        final E[] array = (E[]) new Object[col.size()];
        col.toArray(array);
        new Assertion<>(
            "Array from collection must contain item",
            new ListOf<>(array),
            new HasValues<>(this.sample)
        ).affirm();
        new Assertion<>(
            "Must contain list with the item",
            col.containsAll(
                new ListOf<>(this.sample)
            ),
            new IsEqual<>(true)
        ).affirm();
        return true;
    }

    @Override
    public void describeTo(final Description desc) {
        desc.appendText("not a valid collection");
    }
}
