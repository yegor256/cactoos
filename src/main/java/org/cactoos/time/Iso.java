/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.time;

import java.time.format.DateTimeFormatter;
import org.cactoos.Scalar;

/**
 * The formatter.
 * @since 0.27
 */
public final class Iso implements Scalar<DateTimeFormatter> {

    @Override
    public DateTimeFormatter value() {
        return DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    }

}
