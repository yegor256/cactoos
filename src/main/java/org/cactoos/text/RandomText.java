/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
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
import org.cactoos.Text;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Random text.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 0.32
 */
public final class RandomText implements Text {

    /**
     * Max length of generated text (if no length is specified).
     */
    private static final int MAX_RANDOM_LENGTH = 255;

    /**
     * List of characters allowed for generating.
     */
    private final List<Character> characters;

    /**
     * Length of generated text.
     */
    private final Scalar<Integer> length;

    /**
     * Characters index randomizer.
     */
    private final Random random;

    /**
     * Ctor.
     */
    public RandomText() {
        this(
            () -> new SecureRandom().nextInt(
                RandomText.MAX_RANDOM_LENGTH - 1
            ) + 1
        );
    }

    /**
     * Ctor.
     * @param len Length of generated text.
     */
    public RandomText(final Integer len) {
        this(() -> len);
    }

    /**
     * Ctor.
     * @param len Length of generated text.
     */
    public RandomText(final Scalar<Integer> len) {
        this(
            new ListOf<>(
                '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*',
                '+', ',', '-', '.', '/', '0', '1', '2', '3', '4',
                '5', '6', '7', '8', '9', ':', ';', '<', '=', '>',
                '?', '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
                'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
                'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[', '\\',
                ']', '^', '_', '`', 'a', 'b', 'c', 'd', 'e', 'f',
                'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
                'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '{', '|', '}', '~'
            ),
            len
        );
    }

    /**
     * Ctor.
     * @param chrs Array of characters allowed for generating.
     */
    public RandomText(final Character... chrs) {
        this(new ListOf<>(chrs));
    }

    /**
     * Ctor.
     * @param chrs List of characters allowed for generating.
     */
    public RandomText(final List<Character> chrs) {
        this(
            chrs,
            () -> new SecureRandom().nextInt(
                RandomText.MAX_RANDOM_LENGTH - 1
            ) + 1
        );
    }

    /**
     * Ctor.
     * @param len Length of generated text.
     * @param chrs Array of characters allowed for generating.
     */
    public RandomText(final Integer len, final Character... chrs) {
        this(() -> len, chrs);
    }

    /**
     * Ctor.
     * @param len Length of generated text.
     * @param chrs Array of characters allowed for generating.
     */
    public RandomText(final Scalar<Integer> len, final Character... chrs) {
        this(new ListOf<>(chrs), len);
    }

    /**
     * Ctor.
     * @param chrs List of characters allowed for generating.
     * @param len Length of generated text.
     */
    public RandomText(final List<Character> chrs, final Scalar<Integer> len) {
        this(chrs, len, new SecureRandom());
    }

    /**
     * Ctor.
     * @param chrs List of characters allowed for generating.
     * @param len Length of generated text.
     * @param rnd Characters index randomizer.
     */
    public RandomText(final List<Character> chrs, final Scalar<Integer> len,
        final Random rnd) {
        this.characters = chrs;
        this.length = len;
        this.random = rnd;
    }

    @Override
    public String asString() {
        final int len = new UncheckedScalar<>(this.length).value();
        final StringBuilder builder = new StringBuilder(len);
        final int bound = this.characters.size();
        for (int index = 0; index < len; index = index + 1) {
            builder.append(this.characters.get(this.random.nextInt(bound)));
        }
        return builder.toString();
    }
}
