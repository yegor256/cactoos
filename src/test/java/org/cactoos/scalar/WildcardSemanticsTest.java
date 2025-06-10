/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.util.List;
import java.util.ArrayList;
import org.cactoos.Func;
import org.cactoos.Proc;
import org.cactoos.Scalar;
import org.cactoos.Text;
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
 * @checkstyle JavadocMethodCheck (500 lines)
 */
final class WildcardSemanticsTest {

    @Test
    void scalarOfAcceptsCovariantFunc() {
        // Testing ScalarOf constructor with Func<? super X, ? extends T>
        final Func<Object, String> func = input -> input.toString();
        final String input = "test";
        new Assertion<>(
            "ScalarOf must accept covariant function",
            new ScalarOf<>(func, input),
            new HasValue<>("test")
        ).affirm();
    }

    @Test
    void scalarOfAcceptsContravariantProc() {
        // Testing ScalarOf constructor with Proc<? super X>
        final List<String> result = new ArrayList<>();
        final Proc<Object> proc = input -> result.add(input.toString());
        final String input = "hello";
        final String expected = "world";
        new Assertion<>(
            "ScalarOf must accept contravariant processor",
            new ScalarOf<>(proc, input, expected),
            new HasValue<>(expected)
        ).affirm();
    }

    @Test
    void mappedAcceptsCovariantScalar() {
        // Testing Mapped with Scalar<? extends T>
        final Scalar<String> scalar = () -> "hello";
        final Func<Object, Text> func = input -> new Upper(new TextOf(input.toString()));
        new Assertion<>(
            "Mapped must accept covariant scalar",
            new Mapped<>(func, scalar),
            new HasValue<>(new IsText("HELLO"))
        ).affirm();
    }

    @Test
    void mappedAcceptsContravariantFunc() {
        // Testing Mapped with Func<? super T, ? extends U>
        final Scalar<String> scalar = () -> "hello";
        final Func<CharSequence, Text> func = input -> new Upper(new TextOf(input.toString()));
        new Assertion<>(
            "Mapped must accept contravariant function",
            new Mapped<>(func, scalar),
            new HasValue<>(new IsText("HELLO"))
        ).affirm();
    }

    @Test
    void andAcceptsContravariantFunc() {
        // Testing And with Func<? super X, Boolean>
        final Func<CharSequence, Boolean> func = input -> input.length() > 0;
        final String[] inputs = {"hello", "world"};
        new Assertion<>(
            "And must accept contravariant function",
            new And(func, inputs),
            new HasValue<>(true)
        ).affirm();
    }

    @Test
    void andAcceptsCovariantIterable() {
        // Testing And with Iterable<? extends X>
        final Func<CharSequence, Boolean> func = input -> input.length() > 0;
        final IterableOf<String> inputs = new IterableOf<>("hello", "world");
        new Assertion<>(
            "And must accept covariant iterable",
            new And(func, inputs),
            new HasValue<>(true)
        ).affirm();
    }

    @Test
    void orAcceptsContravariantFunc() {
        // Testing Or with Func<? super X, Boolean>
        final Func<CharSequence, Boolean> func = input -> input.length() > 5;
        final String[] inputs = {"hi", "hello"};
        new Assertion<>(
            "Or must accept contravariant function",
            new Or(func, inputs),
            new HasValue<>(false)
        ).affirm();
    }

    @Test
    void orAcceptsCovariantIterable() {
        // Testing Or with Iterable<? extends X>
        final Func<CharSequence, Boolean> func = input -> input.length() > 3;
        final IterableOf<String> inputs = new IterableOf<>("hi", "hello");
        new Assertion<>(
            "Or must accept covariant iterable",
            new Or(func, inputs),
            new HasValue<>(true)
        ).affirm();
    }

    @Test
    void flattenedAcceptsNestedWildcards() {
        // Testing Flattened with Scalar<? extends Scalar<? extends X>>
        final Scalar<String> inner = () -> "test";
        final Scalar<Scalar<String>> outer = () -> inner;
        new Assertion<>(
            "Flattened must accept nested wildcards",
            new Flattened<>(outer),
            new HasValue<>("test")
        ).affirm();
    }

    @Test
    void scalarWithFallbackAcceptsCovariantScalar() {
        // Testing ScalarWithFallback with Scalar<? extends T>
        final Scalar<String> scalar = () -> "success";
        new Assertion<>(
            "ScalarWithFallback must accept covariant scalar",
            new ScalarWithFallback<>(scalar),
            new HasValue<>("success")
        ).affirm();
    }

}