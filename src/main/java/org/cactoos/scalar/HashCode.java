/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import java.util.Objects;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;

/**
 * Object HashCode.
 *
 * Replace this code:
 * {@code
 *  public int hashCode() {
 *      int hash = 5;
 *      hash = 67 * hash + Objects.hashCode(this.attr1);
 *      hash = 67 * hash + Objects.hashCode(this.attr2);
 *      hash = 67 * hash + Objects.hashCode(this.attr3);
 *      // ... more attributes
 *      return hash;
 *  }
 * }
 *
 * With this:
 * {@code
 *  public int hashCode() {
 *      return new HashCode(
 *          5, 67,
 *          this.attr1, this.attr2, this.attr3, ...
 *      ).value();
 *  }
 * }
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 1.0
 */
public final class HashCode implements Scalar<Integer> {
    /**
     * Hash code.
     */
    private final Unchecked<Integer> origin;

    /**
     * Ctor.
     *
     * <p>The {@code initial} and {@code multiplier} values are arbitrarily
     * set to 17 and 31 respectively.
     * @param attributes The object's attributes
     */
    public HashCode(final Object... attributes) {
        this(new IterableOf<>(attributes));
    }

    /**
     * Ctor.
     *
     * <p>The {@code initial} and {@code multiplier} values are arbitrarily
     * set to 17 and 31 respectively.
     * @param attributes The object's attributes
     */
    public HashCode(final Iterable<?> attributes) {
        this(17, 31, attributes);
    }

    /**
     * Ctor.
     * @param initial Initial value (non-zero recommended)
     * @param multiplier Step multiplier (odd prime recommended)
     * @param attributes The object's attributes
     */
    public HashCode(
        final int initial, final int multiplier, final Object... attributes
    ) {
        this(initial, multiplier, new IterableOf<>(attributes));
    }

    /**
     * Ctor.
     * @param initial Initial value (non-zero recommended)
     * @param multiplier Step multiplier (odd prime recommended)
     * @param attributes The object's attributes
     */
    public HashCode(
        final int initial,
        final int multiplier,
        final Iterable<?> attributes
    ) {
        this(
            new Folded<>(
                initial,
                (hash, attr) -> hash * multiplier + Objects.hashCode(attr),
                attributes
            )
        );
    }

    /**
     * Ctor.
     * @param hash Hashcode
     */
    private HashCode(final Scalar<Integer> hash) {
        this.origin = new Unchecked<>(hash);
    }

    @Override
    public Integer value() {
        return this.origin.value();
    }
}
