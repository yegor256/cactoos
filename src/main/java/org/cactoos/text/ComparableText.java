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

import org.cactoos.Text;

/**
 * Text implementing Comparable.<br>
 * Below the example how you can sort words in a string:
 * <pre>
 * Iterable&lt;Text> sorted = new Sorted<>(
 *   new Mapped<>(
 *     ComparableText::new,
 *     new SplitText("The quick brown fox jumps over the lazy dog", " ")
 *   )
 * )
 * </pre>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Sergey Sharov (zefick@mail.ru)
 * @version $Id$
 * @since 0.27
 */
public final class ComparableText implements Text, Comparable<ComparableText> {

    /**
     * The origin.
     */
    private final UncheckedText text;

    /**
     * Ctor.
     * @param text The text
     */
    public ComparableText(final Text text) {
        this(new UncheckedText(text));
    }

    /**
     * Ctor.
     * @param text The text
     */
    public ComparableText(final UncheckedText text) {
        this.text = text;
    }

    @Override
    public int compareTo(final ComparableText other) {
        return this.text.asString().compareTo(other.asString());
    }

    @Override
    public String asString() {
        return this.text.asString();
    }

}
