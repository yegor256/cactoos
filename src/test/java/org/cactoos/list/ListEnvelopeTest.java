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
 * @since 0.32
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle JavadocTypeCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
final class ListEnvelopeTest {

    @Test
    void returnsListIteratorWithSupportedSet() {
        final List<String> list = new ListEnvelopeTest.StringList("alpha", "beta");
        final ListIterator<String> iterator = list.listIterator(1);
        iterator.next();
        iterator.set("gamma");
        iterator.previous();
        MatcherAssert.assertThat(
            "iterator is not equal to expected",
            () -> iterator,
            Matchers.contains(
                "gamma"
            )
        );
    }

    @Test
    void removeIsDelegated() {
        final List<String> list = new ListEnvelopeTest.StringList("delta");
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
            new ListEnvelopeTest.StringList("epsilon").indexOf("epsilon"),
            new IsEqual<>(0)
        );
    }

    @Test
    void addAllIsDelegated() {
        final List<String> list = new ListEnvelopeTest.StringList("zeta");
        list.addAll(0, new ListEnvelopeTest.StringList("eta"));
        MatcherAssert.assertThat(
            "element must be added at 0th position",
            list,
            new IsEqual<>(new ListOf<>("eta", "zeta"))
        );
    }

    @Test
    void setIsDelegatedToTheOriginal() {
        final List<String> list = new ListEnvelopeTest.StringList("theta");
        list.set(0, "iota");
        MatcherAssert.assertThat(
            "value must be changed",
            list,
            new HasValues<>("iota")
        );
    }

    @Test
    void addIsDelegated() {
        final List<String> list = new ListEnvelopeTest.StringList("kappa");
        list.add("lambda");
        MatcherAssert.assertThat(
            "value must be added",
            list,
            new HasValues<>("kappa", "lambda")
        );
    }

    @Test
    void returnsSubListWithRemove() {
        final List<String> list = new ListEnvelopeTest.StringList("mu");
        list.subList(0, 1).remove(0);
        MatcherAssert.assertThat(
            "must be empty after removal of 0th element via subList",
            list,
            new IsEmptyCollection<>()
        );
    }

    @Test
    void returnsSubListWithSupportedSet() {
        final List<String> list = new ListEnvelopeTest.StringList("nu");
        list.subList(0, 1).set(0, "xi");
        MatcherAssert.assertThat(
            "ListEnvelope().subList(...).set() must change the original list",
            list,
            new HasValues<>(
                "xi"
            )
        );
    }

    @Test
    void addsAtGivenIndex() {
        final List<String> list = new ListEnvelopeTest.StringList("omicron");
        list.add(0, "pi");
        MatcherAssert.assertThat(
            "must add value at given index",
            list,
            new HasValues<>("pi")
        );
    }

    @Test
    void getsAtGivenIndex() {
        MatcherAssert.assertThat(
            "must get 0th value",
            new ListEnvelopeTest.StringList("rho").get(0),
            new IsEqual<>("rho")
        );
    }

    @Test
    void getsLastIndexOfValue() {
        final List<String> list = new ListEnvelopeTest.StringList("sigma");
        list.add(1, "sigma");
        MatcherAssert.assertThat(
            "must return correct last index of element",
            list.lastIndexOf("sigma"),
            new IsEqual<>(1)
        );
    }

    @Test
    void mustReturnPreviousIndex() {
        MatcherAssert.assertThat(
            "List iterator must return previous index",
            new ListEnvelopeTest.StringList("1").listIterator().previousIndex(),
            new IsEqual<>(-1)
        );
    }

    @Test
    void mustReturnPreviousElement() {
        MatcherAssert.assertThat(
            "List iterator must return previous element",
            new ListEnvelopeTest.StringList("3", "7").listIterator(1).previous(),
            new IsEqual<>("3")
        );
    }

    @Test
    void mustReturnNextIndex() {
        MatcherAssert.assertThat(
            "List iterator must return next index",
            new ListEnvelopeTest.StringList("1").listIterator().nextIndex(),
            new IsEqual<>(0)
        );
    }

    @Test
    void mustReturnNextElement() {
        MatcherAssert.assertThat(
            "List iterator must return next item",
            new ListEnvelopeTest.StringList("5", "11", "13").listIterator(1).next(),
            new IsEqual<>("11")
        );
    }

    private static final class StringList extends ListEnvelope<String> {

        StringList(final String... elements) {
            super(new ListOf<>(elements));
        }
    }
}
