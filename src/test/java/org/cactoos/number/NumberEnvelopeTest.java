/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.number;

import java.util.stream.Stream;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsTrue;

/**
 * Test case for {@link NumberEnvelope}.
 *
 * @since 1.0.0
 */
@SuppressWarnings({ "serial", "PMD.TooManyMethods" })
final class NumberEnvelopeTest implements ArgumentsProvider {

    @Override
    public Stream<Arguments> provideArguments(final ExtensionContext ctx) {
        return Stream.of(
            Arguments.of(4),
            Arguments.of(8.2d),
            Arguments.of(5.1f),
            Arguments.of(8.2f),
            Arguments.of((short) 2),
            Arguments.of((byte) 9)
        );
    }

    @ParameterizedTest
    @ArgumentsSource(NumberEnvelopeTest.class)
    void floatValue(final Number nbr) {
        new Assertion<>(
            "Must forward floatValue",
            new NumberEnvelope(nbr) { }.floatValue(),
            new IsEqual<>(nbr.floatValue())
        ).affirm();
    }

    @ParameterizedTest
    @ArgumentsSource(NumberEnvelopeTest.class)
    void intValue(final Number nbr) {
        new Assertion<>(
            "Must forward intValue",
            new NumberEnvelope(nbr) { }.intValue(),
            new IsEqual<>(nbr.intValue())
        ).affirm();
    }

    @ParameterizedTest
    @ArgumentsSource(NumberEnvelopeTest.class)
    void doubleValue(final Number nbr) {
        new Assertion<>(
            "Must forward doubleValue",
            new NumberEnvelope(nbr) { }.doubleValue(),
            new IsEqual<>(nbr.doubleValue())
        ).affirm();
    }

    @ParameterizedTest
    @ArgumentsSource(NumberEnvelopeTest.class)
    void longValue(final Number nbr) {
        new Assertion<>(
            "Must forward longValue",
            new NumberEnvelope(nbr) { }.longValue(),
            new IsEqual<>(nbr.longValue())
        ).affirm();
    }

    @ParameterizedTest
    @ArgumentsSource(NumberEnvelopeTest.class)
    void byteValue(final Number nbr) {
        new Assertion<>(
            "Must forward byteValue",
            new NumberEnvelope(nbr) { }.byteValue(),
            new IsEqual<>(nbr.byteValue())
        ).affirm();
    }

    @ParameterizedTest
    @ArgumentsSource(NumberEnvelopeTest.class)
    void shortValue(final Number nbr) {
        new Assertion<>(
            "Must forward shortValue",
            new NumberEnvelope(nbr) { }.shortValue(),
            new IsEqual<>(nbr.shortValue())
        ).affirm();
    }

    @ParameterizedTest
    @ArgumentsSource(NumberEnvelopeTest.class)
    void hashCode(final Number nbr) {
        new Assertion<>(
            "Must forward hashCode",
            new NumberEnvelope(nbr) { }.hashCode(),
            new IsEqual<>(nbr.hashCode())
        ).affirm();
    }

    @ParameterizedTest
    @ArgumentsSource(NumberEnvelopeTest.class)
    void equalsNumber(final Number nbr) {
        new Assertion<>(
            "Must forward equals and be true for correct Number",
            new NumberEnvelope(nbr) { }.equals(nbr),
            new IsTrue()
        ).affirm();
    }

    @ParameterizedTest
    @ArgumentsSource(NumberEnvelopeTest.class)
    void notEquals(final Number nbr) {
        new Assertion<>(
            "Must forward equals and be false for Object",
            new NumberEnvelope(nbr) { }.equals(new Object()),
            new IsNot<>(new IsTrue())
        ).affirm();
    }

    @ParameterizedTest
    @ArgumentsSource(NumberEnvelopeTest.class)
    @SuppressWarnings("unlikely-arg-type")
    void notEqualsObject(final Number nbr) {
        new Assertion<>(
            "Must forward equals and be false for incorrect Number",
            new NumberEnvelope(nbr) { }.equals(-1),
            new IsNot<>(new IsTrue())
        ).affirm();
    }

    @ParameterizedTest
    @ArgumentsSource(NumberEnvelopeTest.class)
    void toString(final Number nbr) {
        new Assertion<>(
            "Must forward toString",
            new NumberEnvelope(nbr) { }.toString(),
            new IsEqual<>(nbr.toString())
        ).affirm();
    }
}
