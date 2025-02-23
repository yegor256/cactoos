/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.number;

/**
 * Envelope for the {@link Number}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 1.0.0
 */
public abstract class NumberEnvelope extends Number {

    /**
     * Serialization marker.
     */
    private static final long serialVersionUID = -8562608838611967858L;

    /**
     * Wrapped Number.
     */
    private final Number wrapped;

    /**
     * Ctor.
     * @param wrapped Number
     */
    public NumberEnvelope(final Number wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public final int intValue() {
        return this.wrapped.intValue();
    }

    @Override
    public final long longValue() {
        return this.wrapped.longValue();
    }

    @Override
    public final float floatValue() {
        return this.wrapped.floatValue();
    }

    @Override
    public final double doubleValue() {
        return this.wrapped.doubleValue();
    }

    @Override
    public final short shortValue() {
        return this.wrapped.shortValue();
    }

    @Override
    public final byte byteValue() {
        return this.wrapped.byteValue();
    }

    @Override
    public final boolean equals(final Object obj) {
        return this.wrapped.equals(obj);
    }

    @Override
    public final int hashCode() {
        return this.wrapped.hashCode();
    }

    @Override
    public final String toString() {
        return this.wrapped.toString();
    }
}
