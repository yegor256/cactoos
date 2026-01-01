/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Scalar;

/**
 * Scalar that is thread-safe.
 *
 * <p>This class implements {@link Scalar}, which throws a checked
 * {@link Exception}. This may not be convenient in many cases. To make
 * it more convenient and get rid of the checked exception you can
 * use the {@link Unchecked} decorator. Or you may use
 * {@link IoChecked} to wrap it in an IOException.</p>
 *
 * <pre>{@code
 * final List<Integer> list = new LinkedList<>();
 * final int threads = 100;
 * new RunsInThreads<>(
 *     new SyncScalar<>(() -> list.add(1)), threads
 * ); // list.size() will be equal to threads value
 * }</pre>
 *
 * <p>Objects of this class are thread-safe.</p>
 *
 * @param <T> Type of result
 * @since 0.3
 */
public final class Synced<T> implements Scalar<T> {

    /**
     * The scalar to cache.
     */
    private final Scalar<? extends T> origin;

    /**
     * Sync lock.
     */
    private final Object mutex;

    /**
     * Ctor.
     * @param src The Scalar to cache
     */
    public Synced(final Scalar<? extends T> src) {
        this(src, src);
    }

    /**
     * Ctor.
     * @param scalar The Scalar to cache
     * @param lock Sync lock
     */
    public Synced(final Scalar<? extends T> scalar, final Object lock) {
        this.origin = scalar;
        this.mutex = lock;
    }

    @Override
    public T value() throws Exception {
        synchronized (this.mutex) {
            return this.origin.value();
        }
    }
}
