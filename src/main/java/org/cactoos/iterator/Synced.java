/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.iterator;

import java.util.Iterator;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Synchronized {@link Iterator} implementation using a {@link ReadWriteLock}
 * either provided to the constructor or an internally created
 * {@link ReentrantReadWriteLock}.
 *
 * <p>The {@link ReadWriteLock} is used to synchronize read calls to
 * {@link Synced#hasNext()} against write calls to
 * {@link Synced#next()} and write calls to any other read or write
 * calls.</p>
 *
 * <p>Objects of this class are thread-safe.</p>
 *
 * @param <T> The type of the iterator.
 * @since 1.0
 */
public final class Synced<T> implements Iterator<T> {
    /**
     * The original iterator.
     */
    private final Iterator<? extends T> iterator;

    /**
     * The lock to use.
     */
    private final ReadWriteLock lock;

    /**
     * Ctor.
     * @param iterator The iterator to synchronize access to.
     */
    public Synced(final Iterator<? extends T> iterator) {
        this(new ReentrantReadWriteLock(), iterator);
    }

    /**
     * Ctor.
     * @param lock The lock to use for synchronization.
     * @param iterator The iterator to synchronize access to.
     */
    public Synced(final ReadWriteLock lock, final Iterator<? extends T> iterator) {
        this.iterator = iterator;
        this.lock = lock;
    }

    @Override
    public boolean hasNext() {
        this.lock.readLock().lock();
        try {
            return this.iterator.hasNext();
        } finally {
            this.lock.readLock().unlock();
        }
    }

    @Override
    public T next() {
        this.lock.writeLock().lock();
        try {
            return this.iterator.next();
        } finally {
            this.lock.writeLock().unlock();
        }
    }
}
