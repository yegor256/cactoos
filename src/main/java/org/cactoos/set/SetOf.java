/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.set;

import java.util.HashSet;
import java.util.Set;
import org.cactoos.iterable.IterableOf;

/**
 * Iterable as {@link Set} based on {@link HashSet}.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @param <T> Set type
 * @since 0.49.2
 */
public final class SetOf<T> extends SetEnvelope<T> {

    /**
     * Ctor.
     *
     * @param array An array of some elements
     */
    @SafeVarargs
    public SetOf(final T... array) {
        this(new IterableOf<>(array));
    }

    /**
     * Ctor.
     * @param src An {@link Iterable}
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public SetOf(final Iterable<? extends T> src) {
        super(new HashSet<>());
        src.forEach(super::add);
    }
}
