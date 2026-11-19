/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.func;

import java.security.SecureRandom;
import java.time.Duration;
import org.cactoos.Func;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.IsApplicable;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Test case for {@link Retry}.
 *
 * @since 0.8
 */
@SuppressWarnings("PMD.UnnecessaryLocalRule")
final class RetryTest {

    @Test
    void runsFuncMultipleTimes() {
        MatcherAssert.assertThat(
            "Didn't run multiple times",
            new Retry<>(
                input -> {
                    if (new SecureRandom().nextDouble() > 0.3d) {
                        throw new IllegalArgumentException("May happen");
                    }
                    return 0;
                },
                Integer.MAX_VALUE
            ),
            new IsApplicable<>(true, 0)
        );
    }

    @Test
    void runsFuncConditionMultipleTimes() {
        MatcherAssert.assertThat(
            "Didn't check condition multiple times",
            new Retry<>(
                input -> {
                    if (new SecureRandom().nextDouble() > 0.3d) {
                        throw new IllegalArgumentException("May happen");
                    }
                    return true;
                },
                count -> count == Integer.MAX_VALUE
            ),
            new IsApplicable<>(true, true)
        );
    }

    @Test
    void processInterruptExceptionOnWait() {
        final Thread main = Thread.currentThread();
        new Thread(
            () -> {
                while (true) {
                    if (Thread.State.TIMED_WAITING == main.getState()) {
                        main.interrupt();
                        return;
                    }
                }
            }
        ).start();
        MatcherAssert.assertThat(
            "Must be interrupted on wait",
            () -> new Retry<>(
                input -> {
                    throw new IllegalArgumentException("Happens");
                },
                attempts -> false,
                Duration.ofMillis(Long.MAX_VALUE)
            ).apply(true),
            new Throws<>("sleep interrupted", InterruptedException.class)
        );
    }

    @Test
    void acceptsFuncOfSupertypeInput() {
        final Func<Object, String> fnc = Object::toString;
        MatcherAssert.assertThat(
            "Must accept func of supertype input",
            new Retry<CharSequence, CharSequence>(fnc),
            new IsApplicable<>("abc", "abc")
        );
    }

    @Test
    void acceptsFuncOfSupertypeInputWithAttempts() {
        final Func<Object, String> fnc = Object::toString;
        MatcherAssert.assertThat(
            "Must accept func of supertype input with attempts",
            new Retry<CharSequence, CharSequence>(fnc, 2),
            new IsApplicable<>("abc", "abc")
        );
    }

    @Test
    void acceptsFuncOfSupertypeInputWithExit() {
        final Func<Object, String> fnc = Object::toString;
        final Func<Integer, Boolean> exit = attempts -> attempts >= 3;
        MatcherAssert.assertThat(
            "Must accept func of supertype input with exit condition",
            new Retry<CharSequence, CharSequence>(fnc, exit),
            new IsApplicable<>("abc", "abc")
        );
    }

    @Test
    void acceptsFuncOfSupertypeInputWithDuration() {
        final Func<Object, String> fnc = Object::toString;
        MatcherAssert.assertThat(
            "Must accept func of supertype input with duration",
            new Retry<CharSequence, CharSequence>(fnc, 2, Duration.ZERO),
            new IsApplicable<>("abc", "abc")
        );
    }

    @Test
    void acceptsFuncOfSupertypeInputWithExitAndDuration() {
        final Func<Object, String> fnc = Object::toString;
        final Func<Integer, Boolean> exit = attempts -> attempts >= 3;
        MatcherAssert.assertThat(
            "Must accept func of supertype input with exit and duration",
            new Retry<CharSequence, CharSequence>(
                fnc, exit, Duration.ofMillis(1L)
            ),
            new IsApplicable<>("abc", "abc")
        );
    }
}
