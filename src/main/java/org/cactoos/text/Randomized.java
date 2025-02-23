/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.text;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import org.cactoos.Scalar;
import org.cactoos.iterable.RangeOf;
import org.cactoos.list.ListOf;

/**
 * Randomized text.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.32
 */
public final class Randomized extends TextEnvelope {

    /**
     * Max length of generated text (if no length is specified).
     */
    private static final int MAX_RANDOM_LENGTH = 255;

    /**
     * Ctor.
     */
    public Randomized() {
        this(
            () -> new SecureRandom().nextInt(
                Randomized.MAX_RANDOM_LENGTH - 1
            ) + 1
        );
    }

    /**
     * Ctor.
     * @param len Length of generated text.
     */
    public Randomized(final Integer len) {
        this(() -> len);
    }

    /**
     * Ctor.
     * @param len Length of generated text.
     */
    public Randomized(final Scalar<Integer> len) {
        this(
            new RangeOf<>(
                '!', '~', ch -> (char) (ch + 1)
            ),
            len
        );
    }

    /**
     * Ctor.
     * @param chrs Array of characters allowed for generating.
     */
    public Randomized(final Character... chrs) {
        this(new ListOf<>(chrs));
    }

    /**
     * Ctor.
     * @param chrs List of characters allowed for generating.
     */
    public Randomized(final List<Character> chrs) {
        this(
            chrs,
            () -> new SecureRandom().nextInt(
                Randomized.MAX_RANDOM_LENGTH - 1
            ) + 1
        );
    }

    /**
     * Ctor.
     * @param len Length of generated text.
     * @param chrs Array of characters allowed for generating.
     */
    public Randomized(final Integer len, final Character... chrs) {
        this(() -> len, chrs);
    }

    /**
     * Ctor.
     * @param len Length of generated text.
     * @param chrs Array of characters allowed for generating.
     */
    public Randomized(final Scalar<Integer> len, final Character... chrs) {
        this(new ListOf<>(chrs), len);
    }

    /**
     * Ctor.
     * @param chrs Characters allowed for generating.
     * @param len Length of generated text.
     */
    public Randomized(final Iterable<Character> chrs, final Scalar<Integer> len) {
        this(chrs, len, new SecureRandom());
    }

    /**
     * Ctor.
     * @param itr Characters allowed for generating.
     * @param len Length of generated text.
     * @param rnd Characters index randomizer.
     */
    public Randomized(final Iterable<Character> itr, final Scalar<Integer> len, final Random rnd) {
        super(
            new TextOf(
                () -> {
                    final List<Character> chrs = new ListOf<>(itr);
                    final int length = len.value();
                    final StringBuilder builder = new StringBuilder(length);
                    final int bound = chrs.size();
                    for (int index = 0; index < length; index += 1) {
                        builder.append(chrs.get(rnd.nextInt(bound)));
                    }
                    return builder.toString();
                }
            )
        );
    }
}
