/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2019 Yegor Bugayenko
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
package org.cactoos.scalar;

import java.util.Objects;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;

/**
 * Object HashCode.
 *
 * Replace this code:
 * {@code
 *  public int hashCode() {
 *      int hash = 5;
 *      hash = 67 * hash + Objects.hashCode(this.attr1);
 *      hash = 67 * hash + Objects.hashCode(this.attr2);
 *      hash = 67 * hash + Objects.hashCode(this.attr3);
 *      // ... more attributes
 *      return hash;
 *  }
 * }
 *
 * With this:
 * {@code
 *  public int hashCode() {
 *      return new HashCode(
 *          5, 67,
 *          this.attr1, this.attr2, this.attr3, ...
 *      ).value();
 *  }
 * }
 *
 * <p>There is no thread-safety guarantee.
 *
 * @since 1.0
 */
public final class HashCode implements Scalar<Integer> {
    /**
     * Hash code.
     */
    private final Unchecked<Integer> origin;

    /**
     * Ctor.
     *
     * <p>The {@code initial} and {@code multiplier} values are arbitrarily
     * set to 17 and 31 respectively.
     * @param attributes The object's attributes
     * @checkstyle MagicNumber (3 lines)
     */
    public HashCode(final Object... attributes) {
        this(17, 31, attributes);
    }

    /**
     * Ctor.
     * @param initial Initial value (non-zero recommended)
     * @param multiplier Step multiplier (odd prime recommended)
     * @param attributes The object's attributes
     */
    public HashCode(
        final int initial, final int multiplier, final Object... attributes
    ) {
        this(
            new Folded<>(
                initial,
                (hash, attr) -> hash * multiplier + Objects.hashCode(attr),
                new IterableOf<>(attributes)
            )
        );
    }

    /**
     * Ctor.
     * @param hash Hashcode
     */
    private HashCode(final Scalar<Integer> hash) {
        this.origin = new Unchecked<>(hash);
    }

    @Override
    public Integer value() {
        return this.origin.value();
    }
}
