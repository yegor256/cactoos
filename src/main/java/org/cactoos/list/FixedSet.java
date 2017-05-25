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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Fixed unmodifiable set of elements.
 *
 * @author Kirill (g4s8.public@gmail.com)
 * @version $Id$
 * @param <T> Set type
 * @since 0.1
 */
public final class FixedSet<T> extends SetWrap<T> {

    /**
     * Ctor.
     *
     * @param elements Set elements
     */
    @SafeVarargs
    @SuppressWarnings("varargs")
    public FixedSet(final T... elements) {
        this(
            Arrays.asList(elements)
        );
    }

    /**
     * Ctor.
     *
     * @param elements Set elements
     */
    public FixedSet(final Collection<T> elements) {
        this(
            new HashSet<>(elements)
        );
    }

    /**
     * Ctor.
     *
     * @param elements Set elements
     */
    public FixedSet(final Set<T> elements) {
        super(
            Collections.unmodifiableSet(elements)
        );
    }
}
