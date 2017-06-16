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

import java.util.Iterator;
import org.cactoos.func.UncheckedFunc;

/**
 * Iterable over the output of a function over natural Numbers.
 *
 * The supplied func will get a sequence of natural numbers (0,1,2,3....)
 *
 * @author Tim Hinkes (timmeey@timmeey.de)
 * @version $Id$
 * @param <Y> The Type of the Iterable
 * @since 0.7
 */
public final class NaturalNumbersFuncAsIterable<Y> implements Iterable<Y> {

    /**
     * The underlying iterable.
     */
    private final FuncAsIterable<Long, Y> iterable;

    /**
     * Ctor.
     * @param func The Function that takes Longs and provides the output
     */
    public NaturalNumbersFuncAsIterable(final UncheckedFunc<Long, Y> func) {
        this.iterable = new FuncAsIterable<Long, Y>(func, new NaturalNumbers());
    }

    @Override
    public Iterator<Y> iterator() {
        return this.iterable.iterator();
    }
}
