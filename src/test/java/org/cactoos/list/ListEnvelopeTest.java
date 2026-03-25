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
import org.llorllale.cactoos.matchers.HasValues;

/**
 * Test case for {@link ListEnvelope}.
 *
 * @since 0.32
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle JavadocTypeCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
final class ListEnvelopeTest {

    @Test
    void returnsListIteratorWithSupportedSet() {
        final List<String> list = new StringList("one", "two");
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
        final List<String> list = new StringList("one");
        list.remove(0);
        MatcherAssert.assertThat(
            "must be empty after removal of 0th element",
            list,
            new IsEmptyCollection<>()
        );
    }

    @Test
    void indexOfIsDelegated() {
        MatcherAssert.assertThat(
            "must return correct index of element",
            new StringList("one").indexOf("one"),
            new IsEqual<>(0)
        );
    }

    @Test
    void addAllIsDelegated() {
        final List<String> list = new StringList("one");
        list.addAll(0, new StringList("two"));
        MatcherAssert.assertThat(
            "element must be added at 0th position",
            list,
            new IsEqual<>(new ListOf<>("two", "one"))
        );
    }

    @Test
    void setIsDelegatedToTheOriginal() {
        final List<String> list = new StringList("one");
        list.set(0, "zero");
        MatcherAssert.assertThat(
            "value must be changed",
            list,
            new HasValues<>("zero")
        );
    }

    @Test
    void addIsDelegated() {
        final List<String> list = new StringList("one");
        list.add("two");
        MatcherAssert.assertThat(
            "value must be added",
            list,
            new HasValues<>("one", "two")
        );
    }

    @Test
    void returnsSubListWithRemove() {
        final List<String> list = new StringList("one");
        list.subList(0, 1).remove(0);
        MatcherAssert.assertThat(
            "must be empty after removal of 0th element via subList",
            list,
            new IsEmptyCollection<>()
        );
    }

    @Test
    void returnsSubListWithSupportedSet() {
        final List<String> list = new StringList("one");
        list.subList(0, 1).set(0, "zero");
        MatcherAssert.assertThat(
            "ListEnvelope().subList(...).set() must change the original list",
            list,
            new HasValues<>(
                "zero"
            )
        );
    }

    @Test
    void addsAtGivenIndex() {
        final List<String> list = new StringList("one");
        list.add(0, "two");
        MatcherAssert.assertThat(
            "must add value at given index",
            list,
            new HasValues<>("two")
        );
    }

    @Test
    void getsAtGivenIndex() {
        MatcherAssert.assertThat(
            "must get 0th value",
            new StringList("one").get(0),
            new IsEqual<>("one")
        );
    }

    @Test
    void getsLastIndexOfValue() {
        final List<String> list = new StringList("one");
        list.add(1, "one");
        MatcherAssert.assertThat(
            "must return correct last index of element",
            list.lastIndexOf("one"),
            new IsEqual<>(1)
        );
    }

    @Test
    void mustReturnPreviousIndex() {
        MatcherAssert.assertThat(
            "List iterator must return previous index",
            new StringList("1").listIterator().previousIndex(),
            new IsEqual<>(-1)
        );
    }

    @Test
    void mustReturnPreviousElement() {
        MatcherAssert.assertThat(
            "List iterator must return previous element",
            new StringList("3", "7").listIterator(1).previous(),
            new IsEqual<>("3")
        );
    }

    @Test
    void mustReturnNextIndex() {
        MatcherAssert.assertThat(
            "List iterator must return next index",
            new StringList("1").listIterator().nextIndex(),
            new IsEqual<>(0)
        );
    }

    @Test
    void mustReturnNextElement() {
        MatcherAssert.assertThat(
            "List iterator must return next item",
            new  StringList("5", "11", "13").listIterator(1).next(),
            new IsEqual<>("11")
        );
    }

    private static final class StringList extends ListEnvelope<String> {
        StringList(final String... elements) {
            super(new ListOf<>(elements));
        }
    }
}
