/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.list;

import java.util.AbstractList;
import java.util.List;

/**
 * A live view of a slice of a list, backed by it.
 *
 * <p>Reads are delegated to the original list at the shifted indexes,
 * so the view reflects the later changes of the original list, and
 * mutations of the view are propagated to the original list, and through
 * it further down, if the original list is backed itself.</p>
 *
 * <p>There is no thread-safety guarantee.</p>
 *
 * @param <X> Type of source item
 * @since 1.0.0
 */
final class Slice<X> extends AbstractList<X> {

    /**
     * The original list.
     */
    private final List<X> origin;

    /**
     * Index of the first element of the slice in the original list.
     */
    private final int offset;

    /**
     * Current number of elements in the slice.
     */
    private int size;

    /**
     * Ctor.
     *
     * @param origin The original list
     * @param offset Index of the first element in the original list
     * @param size Number of elements in the slice
     */
    Slice(final List<X> origin, final int offset, final int size) {
        this.origin = origin;
        this.offset = offset;
        this.size = size;
    }

    @Override
    public X get(final int index) {
        this.checkBounds(index);
        return this.origin.get(this.offset + index);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public X set(final int index, final X element) {
        this.checkBounds(index);
        return this.origin.set(this.offset + index, element);
    }

    @Override
    public void add(final int index, final X element) {
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException(
                String.format(
                    "index %d is out of the sublist bounds (size %d)",
                    index,
                    this.size
                )
            );
        }
        this.origin.add(this.offset + index, element);
        ++this.size;
    }

    @Override
    public X remove(final int index) {
        this.checkBounds(index);
        final X removed = this.origin.remove(this.offset + index);
        --this.size;
        return removed;
    }

    private void checkBounds(final int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(
                String.format(
                    "index %d is out of the sublist bounds (size %d)",
                    index,
                    this.size
                )
            );
        }
    }
}
