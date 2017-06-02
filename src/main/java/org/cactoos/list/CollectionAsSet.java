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

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Collection as set.
 *
 * @author Kirill (g4s8.public@gmail.com)
 * @version $Id$
 * @param <T> Elements type
 * @since 0.2
 */
public final class CollectionAsSet<T> extends
    AbstractCollection<T> implements Set<T> {

    /**
     * Origin set.
     */
    private final Set<T> origin;

    /**
     * Ctor.
     *
     * @param collection Source collection
     */
    public CollectionAsSet(final Collection<T> collection) {
        super();
        this.origin = new HashSet<>(collection);
    }

    @Override
    public Iterator<T> iterator() {
        return this.origin.iterator();
    }

    @Override
    public int size() {
        return this.origin.size();
    }

    @Override
    public boolean contains(final Object item) {
        return this.origin.contains(item);
    }

    @Override
    public boolean isEmpty() {
        return this.origin.isEmpty();
    }
}
