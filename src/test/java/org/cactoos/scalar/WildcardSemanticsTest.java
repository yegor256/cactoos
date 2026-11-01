/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.util.ArrayList;
import org.cactoos.iterable.IterableOf;
import org.cactoos.text.TextOf;
import org.cactoos.text.Upper;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValue;
import org.llorllale.cactoos.matchers.IsText;

/**
 * Tests for wildcard semantics in scalar classes.
 * This test class verifies the proper behavior of relaxed wildcards
 * (? extends T, ? super T) in the scalar package as requested in #1569.
 *
 * @since 1.0.0
 */
final class WildcardSemanticsTest {

    /**
     * Test string constant.
     */
    private static final String HELLO = "hello";

    @Test
    void scalarOfAcceptsCovariantFunc() {
        new Assertion<>(
            "ScalarOf must accept covariant function",
            new ScalarOf<>(Object::toString, "test"),
            new HasValue<>("test")
        ).affirm();
    }

    @Test
    void scalarOfAcceptsContravariantProc() {
        new Assertion<>(
            "ScalarOf must accept contravariant processor",
            new ScalarOf<>(
                new ArrayList<Object>(1)::add,
                WildcardSemanticsTest.HELLO,
                "world"
            ),
            new HasValue<>("world")
        ).affirm();
    }

    @Test
    void mappedAcceptsCovariantScalar() {
        new Assertion<>(
            "Mapped must accept covariant scalar",
            new Mapped<>(
                (Object input) -> new Upper(new TextOf(input.toString())),
                () -> WildcardSemanticsTest.HELLO
            ),
            new HasValue<>(new IsText("HELLO"))
        ).affirm();
    }

    @Test
    void mappedAcceptsContravariantFunc() {
        new Assertion<>(
            "Mapped must accept contravariant function",
            new Mapped<>(
                (CharSequence input) -> new Upper(new TextOf(input)),
                () -> WildcardSemanticsTest.HELLO
            ),
            new HasValue<>(new IsText("HELLO"))
        ).affirm();
    }

    @Test
    void andAcceptsContravariantFunc() {
        new Assertion<>(
            "And must accept contravariant function",
            new And(
                (CharSequence input) -> input.length() > 0,
                WildcardSemanticsTest.HELLO,
                "world"
            ),
            new HasValue<>(true)
        ).affirm();
    }

    @Test
    void andAcceptsCovariantIterable() {
        new Assertion<>(
            "And must accept covariant iterable",
            new And(
                (CharSequence input) -> input.length() > 0,
                new IterableOf<String>(WildcardSemanticsTest.HELLO, "world")
            ),
            new HasValue<>(true)
        ).affirm();
    }

    @Test
    void orAcceptsContravariantFunc() {
        new Assertion<>(
            "Or must accept contravariant function",
            new Or(
                (CharSequence input) -> input.length() > 5,
                "hi",
                WildcardSemanticsTest.HELLO
            ),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void orAcceptsCovariantIterable() {
        new Assertion<>(
            "Or must accept covariant iterable",
            new Or(
                (CharSequence input) -> input.length() > 3,
                new IterableOf<String>("hi", WildcardSemanticsTest.HELLO)
            ),
            new HasValue<>(true)
        ).affirm();
    }

    @Test
    void flattenedAcceptsNestedWildcards() {
        new Assertion<>(
            "Flattened must accept nested wildcards",
            new Flattened<String>(() -> () -> "test"),
            new HasValue<>("test")
        ).affirm();
    }

    @Test
    void scalarWithFallbackAcceptsCovariantScalar() {
        new Assertion<>(
            "ScalarWithFallback must accept covariant scalar",
            new ScalarWithFallback<CharSequence>(() -> "success"),
            new HasValue<>("success")
        ).affirm();
    }
}
