/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Yegor Bugayenko
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
package org.cactoos.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.func.UncheckedScalar;
import org.cactoos.text.TextOf;

/**
 * Characters of.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Ix (ixmanuel@yahoo.com)
 * @version $Id$
 * @param <X> Type of item
 * @since 0.12
 */
public final class CharactersOf<X> implements Iterable<X> {

    /**
     * The encapsulated iterator of X.
     */
    private final UncheckedScalar<Iterator<X>> scalar;

    /**
     * Ctor.
     * @param string The string
     */
    public CharactersOf(final String string) {
        this(new TextOf(string));
    }

    /**
     * Ctor.
     * @param text The text
     */
    @SuppressWarnings("unchecked")
    public CharactersOf(final Text text) {
        this(() -> {
            final char[] raws = text.asString().toCharArray();
            final List<Character> chars = new ArrayList<>(raws.length);
            for (final char chr : raws) {
                chars.add(chr);
            }
            return (Iterator<X>) Arrays.asList(
                chars.toArray(new Character[raws.length])
            ).iterator();
        });
    }

    /**
     * Ctor.
     * @param sclr The encapsulated iterator of x
     */
    private CharactersOf(final Scalar<Iterator<X>> sclr) {
        this.scalar = new UncheckedScalar<>(sclr);
    }

    @Override
    public Iterator<X> iterator() {
        return this.scalar.value();
    }

}
