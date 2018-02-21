/**
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
package org.cactoos.iterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.cactoos.Scalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Iterable envelope.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @author Nikita Salomatin (nsalomatin@hotmail.com)
 * @version $Id$
 * @param <X> Type of item
 * @since 0.24
 * @checkstyle AbstractClassNameCheck (500 lines)
 */
@SuppressWarnings("PMD.AbstractNaming")
public abstract class IterableEnvelope<X> implements Iterable<X> {

    /**
     * The iterable.
     */
    private final UncheckedScalar<Iterable<X>> iterable;

    /**
     * The list to get an iterator from.
     */
    private final List<X> list;

    /**
     * Ctor.
     * @param scalar The source
     */
    public IterableEnvelope(final Scalar<Iterable<X>> scalar)  {
        this.iterable = new UncheckedScalar<>(scalar);
        // @checkstyle MagicNumberCheck (1 line)
        this.list = new ArrayList<>(30);
    }

    @Override
    public final Iterator<X> iterator() {
        if (this.list.isEmpty()) {
            for (final X item : this.iterable.value()) {
                this.list.add(item);
            }
        }
        return new UncheckedScalar<>(() -> this.list).value().iterator();
    }

}
