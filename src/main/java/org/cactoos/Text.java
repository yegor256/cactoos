/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos;

/**
 * Text.
 *
 * <p>If you don't want to have any checked exceptions being thrown
 * out of your {@link Text}, you can use
 * {@link org.cactoos.text.UncheckedText} decorator.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @see org.cactoos.text.TextOf
 * @since 0.1
 */
@FunctionalInterface
public interface Text {

    /**
     * Convert it to the string.
     * @return The string
     * @throws Exception If fails
     */
    String asString() throws Exception;
}
