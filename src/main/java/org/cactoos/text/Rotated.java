/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Text;

/**
 * Rotate (circular shift) a String of shift characters.
 * @since 0.12
 */
public final class Rotated extends TextEnvelope {

    /**
     * Ctor.
     * @param text The text
     * @param shift The shift
     */
    public Rotated(final Text text, final int shift) {
        super(
            new Mapped(
                origin -> {
                    final int length = origin.length();
                    if (length != 0 && shift != 0 && shift % length != 0) {
                        int offset = -(shift % length);
                        if (offset < 0) {
                            offset = origin.length() + offset;
                        }
                        final StringBuilder builder = new StringBuilder(length);
                        // @checkstyle ParameterAssignmentCheck (6 lines)
                        origin = builder.append(
                            origin.substring(offset)
                        ).append(
                            origin.substring(0, offset)
                        ).toString();
                    }
                    return origin;
                },
                text
            )
        );
    }
}
