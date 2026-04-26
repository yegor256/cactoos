/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.number;

import java.util.stream.Stream;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.MethodSource;
import org.llorllale.cactoos.matchers.IsTrue;

/**
 * Test case for {@link NumberEnvelope}.
 * @since 1.0.0
 */
@SuppressWarnings({"serial", "PMD.TooManyMethods"})
final class NumberEnvelopeTest implements ArgumentsProvider {

    @ParameterizedTest
    @MethodSource("arguments")
    void floatValue(final Number nbr) {
        MatcherAssert.assertThat(
            "Must forward floatValue",
            new NumberEnvelope(nbr) { }.floatValue(),
            new IsEqual<>(nbr.floatValue())
        );
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void intValue(final Number nbr) {
        MatcherAssert.assertThat(
            "Must forward intValue",
            new NumberEnvelope(nbr) { }.intValue(),
            new IsEqual<>(nbr.intValue())
        );
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void doubleValue(final Number nbr) {
        MatcherAssert.assertThat(
            "Must forward doubleValue",
            new NumberEnvelope(nbr) { }.doubleValue(),
            new IsEqual<>(nbr.doubleValue())
        );
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void longValue(final Number nbr) {
        MatcherAssert.assertThat(
            "Must forward longValue",
            new NumberEnvelope(nbr) { }.longValue(),
            new IsEqual<>(nbr.longValue())
        );
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void byteValue(final Number nbr) {
        MatcherAssert.assertThat(
            "Must forward byteValue",
            new NumberEnvelope(nbr) { }.byteValue(),
            new IsEqual<>(nbr.byteValue())
        );
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void shortValue(final Number nbr) {
        MatcherAssert.assertThat(
            "Must forward shortValue",
            new NumberEnvelope(nbr) { }.shortValue(),
            new IsEqual<>(nbr.shortValue())
        );
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void hashCode(final Number nbr) {
        MatcherAssert.assertThat(
            "Must forward hashCode",
            new NumberEnvelope(nbr) { }.hashCode(),
            new IsEqual<>(nbr.hashCode())
        );
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void equalsNumber(final Number nbr) {
        MatcherAssert.assertThat(
            "Must forward equals and be true for correct Number",
            new NumberEnvelope(nbr) { }.equals(nbr),
            new IsTrue()
        );
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void notEquals(final Number nbr) {
        MatcherAssert.assertThat(
            "Must forward equals and be false for Object",
            new NumberEnvelope(nbr) { }.equals(new Object()),
            new IsNot<>(new IsTrue())
        );
    }

    @ParameterizedTest
    @MethodSource("arguments")
    @SuppressWarnings("unlikely-arg-type")
    void notEqualsObject(final Number nbr) {
        MatcherAssert.assertThat(
            "Must forward equals and be false for incorrect Number",
            new NumberEnvelope(nbr) { }.equals(-1),
            new IsNot<>(new IsTrue())
        );
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void toString(final Number nbr) {
        MatcherAssert.assertThat(
            "Must forward toString",
            new NumberEnvelope(nbr) { }.toString(),
            new IsEqual<>(nbr.toString())
        );
    }

    private static Stream<Arguments> arguments() {
        return Stream.of(
            Arguments.of(4),
            Arguments.of(8.2d),
            Arguments.of(5.1f),
            Arguments.of(8.2f),
            Arguments.of((short) 2),
            Arguments.of((byte) 9)
        );
    }
}
