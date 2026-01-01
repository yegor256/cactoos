/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.list;

import java.util.List;
import java.util.ListIterator;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsEmptyCollection;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;

/**
 * Test case for {@link ListEnvelope}.
 *
 * @since 0.32
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle JavadocTypeCheck (500 lines)
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.AvoidDuplicateLiterals"})
final class ListEnvelopeTest {

    @Test
    void returnsListIteratorWithSupportedSet() {
        final ListEnvelope<String> list = new StringList("one", "two");
        final ListIterator<String> iterator = list.listIterator(1);
        iterator.next();
        iterator.set("zero");
        iterator.previous();
        MatcherAssert.assertThat(
            "iterator is not equal to expected",
            () -> iterator,
            Matchers.contains(
                "zero"
            )
        );
    }

    @Test
    void removeIsDelegated() {
        final ListEnvelope<String> list = new StringList("one");
        list.remove(0);
        new Assertion<>(
            "must be empty after removal of 0th element",
            list,
            new IsEmptyCollection<>()
        ).affirm();
    }

    @Test
    void indexOfIsDelegated() {
        final ListEnvelope<String> list = new StringList("one");
        new Assertion<>(
            "must return correct index of element",
            list.indexOf("one"),
            new IsEqual<>(0)
        ).affirm();
    }

    @Test
    void addAllIsDelegated() {
        final ListEnvelope<String> list = new StringList("one");
        list.addAll(0, new StringList("two"));
        new Assertion<>(
            "element must be added at 0th position",
            list,
            new IsEqual<>(new ListOf<>("two", "one"))
        ).affirm();
    }

    @Test
    void setIsDelegatedToTheOriginal() {
        final ListEnvelope<String> list = new StringList("one");
        list.set(0, "zero");
        new Assertion<>(
            "value must be changed",
            list,
            new HasValues<>("zero")
        ).affirm();
    }

    @Test
    void addIsDelegated() {
        final ListEnvelope<String> list = new StringList("one");
        list.add("two");
        new Assertion<>(
            "value must be added",
            list,
            new HasValues<>("one", "two")
        ).affirm();
    }

    @Test
    void returnsSubListWithRemove() {
        final ListEnvelope<String> list = new StringList("one");
        list.subList(0, 1).remove(0);
        new Assertion<>(
            "must be empty after removal of 0th element via subList",
            list,
            new IsEmptyCollection<>()
        ).affirm();
    }

    @Test
    void returnsSubListWithSupportedSet() {
        final List<String> list = new StringList("one");
        list.subList(0, 1).set(0, "zero");
        new Assertion<>(
            "ListEnvelope().subList(...).set() must change the original list",
            list,
            new HasValues<>(
                "zero"
            )
        ).affirm();
    }

    @Test
    void addsAtGivenIndex() {
        final ListEnvelope<String> list = new StringList("one");
        list.add(0, "two");
        new Assertion<>(
            "must add value at given index",
            list,
            new HasValues<>("two")
        ).affirm();
    }

    @Test
    void getsAtGivenIndex() {
        final ListEnvelope<String> list = new StringList("one");
        new Assertion<>(
            "must get 0th value",
            list.get(0),
            new IsEqual<>("one")
        ).affirm();
    }

    @Test
    void getsLastIndexOfValue() {
        final ListEnvelope<String> list = new StringList("one");
        list.add(1, "one");
        new Assertion<>(
            "must return correct last index of element",
            list.lastIndexOf("one"),
            new IsEqual<>(1)
        ).affirm();
    }

    @Test
    void mustReturnPreviousIndex() {
        new Assertion<>(
            "List iterator must return previous index",
            new StringList("1").listIterator().previousIndex(),
            new IsEqual<>(-1)
        ).affirm();
    }

    @Test
    void mustReturnPreviousElement() {
        new Assertion<>(
            "List iterator must return previous element",
            new StringList("3", "7").listIterator(1).previous(),
            new IsEqual<>("3")
        ).affirm();
    }

    @Test
    void mustReturnNextIndex() {
        new Assertion<>(
            "List iterator must return next index",
            new StringList("1").listIterator().nextIndex(),
            new IsEqual<>(0)
        ).affirm();
    }

    @Test
    void mustReturnNextElement() {
        new Assertion<>(
            "List iterator must return next item",
            new  StringList("5", "11", "13").listIterator(1).next(),
            new IsEqual<>("11")
        ).affirm();
    }

    private static final class StringList extends ListEnvelope<String> {
        StringList(final String... elements) {
            super(new ListOf<>(elements));
        }
    }
}
