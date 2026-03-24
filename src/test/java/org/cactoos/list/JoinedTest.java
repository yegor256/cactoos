/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.list;

import java.util.List;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsInstanceOf;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsTrue;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Joined}.
 *
 * @since 0.20
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle DiamondOperatorCheck (500 lines)
 */
@SuppressWarnings("PMD.TooManyMethods")
final class JoinedTest {

    /**
     * Literal ONE value.
     */
    private static final String LITERAL_ONE = "ONE";

    /**
     * Literal TWO value.
     */
    private static final String LITERAL_TWO = "TWO";

    /**
     * Literal THREE value.
     */
    private static final String LITERAL_THREE = "THREE";

    /**
     * Literal FOUR value.
     */
    private static final String LITERAL_FOUR = "FOUR";

    @Test
    void behavesAsCollection() {
        MatcherAssert.assertThat(
            "Can't behave as a list",
            new Joined<String>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE, JoinedTest.LITERAL_TWO
                ),
                new ListOf<>(
                    JoinedTest.LITERAL_THREE, JoinedTest.LITERAL_FOUR
                )
            ),
            new BehavesAsList<>(JoinedTest.LITERAL_TWO)
        );
    }

    @Test
    void size() {
        MatcherAssert.assertThat(
            "Must evaluate the size of the joined list",
            new Joined<String>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE, JoinedTest.LITERAL_TWO
                ),
                new ListOf<>(
                    JoinedTest.LITERAL_THREE, JoinedTest.LITERAL_FOUR
                )
            ).size(),
            new IsEqual<>(4)
        );
    }

    @Test
    void isEmpty() {
        MatcherAssert.assertThat(
            "Must be evaluated as an empty list",
            new Joined<String>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE, JoinedTest.LITERAL_TWO
                ),
                new ListOf<>(
                    JoinedTest.LITERAL_THREE, JoinedTest.LITERAL_FOUR
                )
            ).isEmpty(),
            new IsNot<>(new IsTrue())
        );
    }

    @Test
    void contains() {
        MatcherAssert.assertThat(
            "Must contain element specified",
            new Joined<String>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE, JoinedTest.LITERAL_TWO
                ),
                new ListOf<>(
                    JoinedTest.LITERAL_THREE, JoinedTest.LITERAL_FOUR
                )
            ).contains(JoinedTest.LITERAL_THREE),
            new IsTrue()
        );
    }

    @Test
    void iterator() {
        MatcherAssert.assertThat(
            "Joined Iterator Must return next element equal to the first added",
            new Joined<String>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE, JoinedTest.LITERAL_TWO
                ),
                new ListOf<>(
                    JoinedTest.LITERAL_THREE, JoinedTest.LITERAL_FOUR
                )
            ).iterator().next(),
            new IsEqual<>(
                JoinedTest.LITERAL_ONE
            )
        );
    }

    @Test
    void add() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(JoinedTest.LITERAL_ONE),
            new ListOf<>(JoinedTest.LITERAL_TWO)
        );
        joined.add(JoinedTest.LITERAL_THREE);
        MatcherAssert.assertThat(
            "Must be able to add element specified",
            joined,
            new IsEqual<>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE,
                    JoinedTest.LITERAL_TWO,
                    JoinedTest.LITERAL_THREE
                )
            )
        );
    }

    @Test
    void remove() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(JoinedTest.LITERAL_ONE),
            new ListOf<>(JoinedTest.LITERAL_TWO)
        );
        joined.remove(JoinedTest.LITERAL_TWO);
        MatcherAssert.assertThat(
            "Must be able to remove element specified",
            joined,
            new IsEqual<>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE
                )
            )
        );
    }

    @Test
    void containsAll() {
        MatcherAssert.assertThat(
            "Must contain all elements",
            new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE, JoinedTest.LITERAL_THREE),
                new ListOf<>(JoinedTest.LITERAL_TWO, JoinedTest.LITERAL_FOUR)
            ).containsAll(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE,
                    JoinedTest.LITERAL_TWO
                )
            ),
            new IsTrue()
        );
    }

    @Test
    void addAll() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(JoinedTest.LITERAL_ONE),
            new ListOf<>(JoinedTest.LITERAL_TWO)
        );
        joined.addAll(
            new ListOf<>(
                JoinedTest.LITERAL_THREE,
                JoinedTest.LITERAL_FOUR
            )
        );
        MatcherAssert.assertThat(
            "Must be able to addAll elements specified",
            joined,
            new IsEqual<>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE,
                    JoinedTest.LITERAL_TWO,
                    JoinedTest.LITERAL_THREE,
                    JoinedTest.LITERAL_FOUR
                )
            )
        );
    }

    @Test
    void addAllInFront() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(JoinedTest.LITERAL_ONE),
            new ListOf<>(JoinedTest.LITERAL_TWO)
        );
        joined.addAll(
            0,
            new ListOf<>(
                JoinedTest.LITERAL_THREE,
                JoinedTest.LITERAL_FOUR
            )
        );
        MatcherAssert.assertThat(
            "Must be able to addAll elements in front",
            joined,
            new IsEqual<>(
                new ListOf<>(
                    JoinedTest.LITERAL_THREE,
                    JoinedTest.LITERAL_FOUR,
                    JoinedTest.LITERAL_ONE,
                    JoinedTest.LITERAL_TWO
                )
            )
        );
    }

    @Test
    void removeAll() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(
                JoinedTest.LITERAL_ONE,
                JoinedTest.LITERAL_TWO
            ),
            new ListOf<>(JoinedTest.LITERAL_THREE)
        );
        joined.removeAll(
            new ListOf<>(
                JoinedTest.LITERAL_TWO,
                JoinedTest.LITERAL_THREE
            )
        );
        MatcherAssert.assertThat(
            "Must be able to removeAll elements specified",
            joined,
            new IsEqual<>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE
                )
            )
        );
    }

    @Test
    void retainAll() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(JoinedTest.LITERAL_ONE),
            new ListOf<>(
                JoinedTest.LITERAL_TWO,
                JoinedTest.LITERAL_THREE
            )
        );
        joined.retainAll(
            new ListOf<>(
                JoinedTest.LITERAL_TWO,
                JoinedTest.LITERAL_THREE
            )
        );
        MatcherAssert.assertThat(
            "Must be able to retain all",
            joined,
            new IsEqual<>(
                new ListOf<>(
                    JoinedTest.LITERAL_TWO,
                    JoinedTest.LITERAL_THREE
                )
            )
        );
    }

    @Test
    void clear() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(
                JoinedTest.LITERAL_TWO,
                JoinedTest.LITERAL_THREE
            ),
            new ListOf<>(
                JoinedTest.LITERAL_ONE
            )
        );
        joined.clear();
        MatcherAssert.assertThat(
            "Must be able to clear",
            joined.size(),
            new IsEqual<>(0)
        );
    }

    @Test
    void get() {
        MatcherAssert.assertThat(
            "Must get element",
            new Joined<String>(
                new ListOf<>(
                    JoinedTest.LITERAL_TWO,
                    JoinedTest.LITERAL_THREE
                ),
                new ListOf<>(
                    JoinedTest.LITERAL_ONE
                )
            ).get(1),
            new IsEqual<>(JoinedTest.LITERAL_THREE)
        );
    }

    @Test
    void set() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(JoinedTest.LITERAL_ONE),
            new ListOf<>(JoinedTest.LITERAL_TWO)
        );
        joined.set(0, JoinedTest.LITERAL_THREE);
        MatcherAssert.assertThat(
            "Must be able to set element by specified index",
            joined.get(0),
            new IsEqual<>(JoinedTest.LITERAL_THREE)
        );
    }

    @Test
    void addByIndex() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(JoinedTest.LITERAL_ONE),
            new ListOf<>(JoinedTest.LITERAL_TWO)
        );
        joined.add(0, JoinedTest.LITERAL_THREE);
        MatcherAssert.assertThat(
            "Must be able to add element by specified index",
            joined.get(0),
            new IsEqual<>(JoinedTest.LITERAL_THREE)
        );
    }

    @Test
    void removeByIndex() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(JoinedTest.LITERAL_ONE),
            new ListOf<>(JoinedTest.LITERAL_TWO)
        );
        joined.remove(0);
        MatcherAssert.assertThat(
            "Must be able to remove element by specified index",
            joined.get(0),
            new IsEqual<>(JoinedTest.LITERAL_TWO)
        );
    }

    @Test
    void removeByElement() {
        final List<String> joined = new Joined<String>(
            new ListOf<>(JoinedTest.LITERAL_ONE),
            new ListOf<>(JoinedTest.LITERAL_TWO)
        );
        joined.remove(JoinedTest.LITERAL_ONE);
        MatcherAssert.assertThat(
            "Must be able to remove element by specified element",
            joined.get(0),
            new IsEqual<>(JoinedTest.LITERAL_TWO)
        );
    }

    @Test
    void listIteratorSecond() {
        MatcherAssert.assertThat(
            "Exception is expected for greater then size index",
            () -> new Joined<Integer>().listIterator(66),
            new Throws<>(IndexOutOfBoundsException.class)
        );
    }

    @Test
    void subList() {
        MatcherAssert.assertThat(
            "Must be able to to get sub list",
            new Joined<String>(
                new ListOf<>(JoinedTest.LITERAL_ONE),
                new ListOf<>(JoinedTest.LITERAL_TWO, JoinedTest.LITERAL_THREE)
            ).subList(1, 3),
            new IsEqual<>(
                new ListOf<>(
                    JoinedTest.LITERAL_TWO,
                    JoinedTest.LITERAL_THREE
                )
            )
        );
    }

    @Test
    void itemAndList() {
        MatcherAssert.assertThat(
            "Must be able to join element with a list",
            new Joined<>(
                JoinedTest.LITERAL_ONE,
                new ListOf<>(JoinedTest.LITERAL_TWO, JoinedTest.LITERAL_THREE)
            ),
            new IsEqual<>(
                new ListOf<>(
                    JoinedTest.LITERAL_ONE,
                    JoinedTest.LITERAL_TWO,
                    JoinedTest.LITERAL_THREE
                )
            )
        );
    }

    @Test
    void infersCorrectly() {
        MatcherAssert.assertThat(
            "Must be able to infer type of elements",
            new Joined<>(
                1,
                new ListOf<>(
                    2.0,
                    3.0
                )
            ),
            new IsIterableContainingInOrder<>(
                new ListOf<Matcher<? super Number>>(
                    new IsInstanceOf(Integer.class),
                    new IsInstanceOf(Double.class),
                    new IsInstanceOf(Double.class)
                )
            )
        );
    }
}
