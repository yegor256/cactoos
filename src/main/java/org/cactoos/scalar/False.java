/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.scalar;

import org.cactoos.Scalar;

/**
 * Logical false.
 *
 * <p>This class is thread-safe.
 *
 * @since 0.7
 */
public final class False implements Scalar<Boolean> {

    @Override
    public Boolean value() {
        return false;
    }
}
