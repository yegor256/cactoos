/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Input;
import org.cactoos.Text;

/**
 * Rotate (circular shift) a String of shift characters.
 *
 * @since 0.12
 */
public final class Rotated extends TextEnvelope {

    /**
     * Ctor.
     *
     * @param input The input
     * @param shift The shift
     * @since 0.73.2
     */
    public Rotated(final Input input, final int shift) {
        this(new TextOf(input), shift);
    }

    /**
     * Ctor.
     *
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
                        // @checkstyle ParameterAssignmentCheck (2 lines)
                        origin = origin.substring(offset)
                            + origin.substring(0, offset);
                    }
                    return origin;
                },
                text
            )
        );
    }
}
