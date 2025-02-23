/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.list;

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

/**
 * Test cases for {@link ListIteratorEnvelope}.
 *
 * @since 0.35
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle JavadocTypeCheck (500 lines)
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
final class ListIteratorEnvelopeTest {
    @Test
    void mustReturnPreviousIndex() {
        new Assertion<>(
            "List Iterator must return previous index",
            new StringListIterator(
                "1"
            ).previousIndex(),
            new IsEqual<>(-1)
        ).affirm();
    }

    @Test
    void mustReturnPreviousElement() {
        new Assertion<>(
            "List Iterator must return previous element",
            new StringListIterator(
                1,
                "3", "7"
            ).previous(),
            new IsEqual<>("3")
        ).affirm();
    }

    @Test
    void mustReturnNextIndex() {
        new Assertion<>(
            "List iterator must return next index",
            new StringListIterator(
                "1"
            ).nextIndex(),
            new IsEqual<>(0)
        ).affirm();
    }

    @Test
    void mustReturnNextElement() {
        new Assertion<>(
            "List iterator must return next item",
            new StringListIterator(
                1,
                "5", "11", "13"
            ).next(),
            new IsEqual<>("11")
        ).affirm();
    }

    private static final class StringListIterator extends ListIteratorEnvelope<String> {
        StringListIterator(final int index, final String... elements) {
            super(new ListOf<>(elements).listIterator(index));
        }

        StringListIterator(final String... elements) {
            super(new ListOf<>(elements).listIterator());
        }
    }
}
