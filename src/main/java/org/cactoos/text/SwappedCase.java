/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import org.cactoos.Text;

/**
 * Swaps the case of a Text changing upper and title case to lower case,
 * and lower case to upper case.
 * @since 0.13.3
 */
public final class SwappedCase extends TextEnvelope {

    /**
     * Ctor.
     * @param text The text
     */
    public SwappedCase(final Text text) {
        super(
            new Mapped(
                origin -> {
                    final char[] chars = origin.toCharArray();
                    for (int idx = 0; idx < chars.length; idx += 1) {
                        final char chr = chars[idx];
                        if (Character.isUpperCase(chr)) {
                            chars[idx] = Character.toLowerCase(chr);
                        } else if (Character.isLowerCase(chr)) {
                            chars[idx] = Character.toUpperCase(chr);
                        }
                    }
                    return new String(chars);
                },
                text
            )
        );
    }
}
