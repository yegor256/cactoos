/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Scalar;

/**
 * Logical false.
 *
 * <p>This class is thread-safe.</p>
 *
 * @since 0.7
 */
public final class False implements Scalar<Boolean> {

    /**
     * Ctor.
     */
    public False() {
        // nothing to init
    }

    @Override
    public Boolean value() {
        return false;
    }
}
