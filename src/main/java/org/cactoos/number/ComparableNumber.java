/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
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
public final class ComparableNumber extends Number
    implements Comparable<ComparableNumber> {

    /**
     * Serialization marker.
     */
    private static final long serialVersionUID = -2598821437507165938L;

    /**
     * Wrapped number.
     */
    private final Number wrapped;

    /**
     * Ctor.
     * @param nbr Number
     */
    public ComparableNumber(final Number nbr) {
        super();
        this.wrapped = nbr;
    }

    @Override
    public int intValue() {
        return this.wrapped.intValue();
    }

    @Override
    public long longValue() {
        return this.wrapped.longValue();
    }

    @Override
    public float floatValue() {
        return this.wrapped.floatValue();
    }

    @Override
    public double doubleValue() {
        return this.wrapped.doubleValue();
    }

    @Override
    public int compareTo(final ComparableNumber nbr) {
        return Double.compare(this.doubleValue(), nbr.doubleValue());
    }

    @Override
    public boolean equals(final Object obj) {
        return this == obj || this.wrapped.equals(obj);
    }

    @Override
    public int hashCode() {
        return this.wrapped.hashCode();
    }

    @Override
    public String toString() {
        return this.wrapped.toString();
    }
}
