/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.number;

/**
 * {@link Number} as {@link Comparable}.
 *
 * <p>
 * There is no thread-safety guarantee.
 *
 * @since 1.0.0
 */
public final class ComparableNumber extends NumberEnvelope
    implements Comparable<Number> {

    /**
     * Serialization marker.
     */
    private static final long serialVersionUID = -2598821437507165938L;

    /**
     * Ctor.
     *
     * @param nbr Number
     */
    public ComparableNumber(final Number nbr) {
        super(nbr);
    }

    @Override
    public int compareTo(final Number nbr) {
        return Double.compare(this.doubleValue(), nbr.doubleValue());
    }
}
