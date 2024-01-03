/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2024 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
                '!', '~', ch -> ++ch
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
