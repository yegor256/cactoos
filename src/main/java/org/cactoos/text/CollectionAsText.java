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
package org.cactoos.text;

import java.util.Collection;
import java.util.stream.Collectors;
import org.cactoos.Text;

/**
 * Collection as Text.
 *
 * @author Mehmet Yildirim (memoyil@gmail.com)
 * @version $Id$
 * @since 0.2
 */
public final class CollectionAsText implements Text {

    /**
     * The source.
     */
    private final Collection<? extends String> source;

    /**
     * The delimeter.
     */
    private final CharSequence delimeter;

    /**
     * Ctor.
     * @param collection The Collection
     * @param seperator The seperator
     */
    public CollectionAsText(final Collection<? extends String> collection, final
        CharSequence seperator) {
        this.source = collection;
        this.delimeter = seperator;
    }

    @Override
    public String asString() {
        return this.source.stream()
            .map(i -> i.toString())
            .collect(Collectors.joining(this.delimeter));
    }

}
