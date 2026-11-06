/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.list;

import java.util.ListIterator;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test cases for {@link ListIteratorEnvelope}.
 *
 * @since 0.35
 */
final class ListIteratorEnvelopeTest {

    @Test
    void mustReturnPreviousIndex() {
        new Assertion<>(
            "List Iterator must return previous index",
            new ListIteratorEnvelopeTest.StringListIterator(
                new ListOf<>("1").listIterator(0)
            ).previousIndex(),
            new IsEqual<>(-1)
        ).affirm();
    }

    @Test
    void mustReturnPreviousElement() {
        new Assertion<>(
            "List Iterator must return previous element",
            new ListIteratorEnvelopeTest.StringListIterator(
                new ListOf<>("3", "7").listIterator(1)
            ).previous(),
            new IsEqual<>("3")
        ).affirm();
    }

    @Test
    void mustReturnNextIndex() {
        new Assertion<>(
            "List iterator must return next index",
            new ListIteratorEnvelopeTest.StringListIterator(
                new ListOf<>("1").listIterator(0)
            ).nextIndex(),
            new IsEqual<>(0)
        ).affirm();
    }

    @Test
    void mustReturnNextElement() {
        new Assertion<>(
            "List iterator must return next item",
            new ListIteratorEnvelopeTest.StringListIterator(
                new ListOf<>("5", "11", "13").listIterator(1)
            ).next(),
            new IsEqual<>("11")
        ).affirm();
    }

    private static final class StringListIterator extends ListIteratorEnvelope<String> {

        StringListIterator(final ListIterator<String> origin) {
            super(origin);
        }
    }
}
