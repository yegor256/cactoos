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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.cactoos.Text;

/**
 * Text in Sprinf format.
 *
 * <p>There is no thread-safety guarantee.
 *
 * @author Yegor Bugayenko (yegor256@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public final class FormattedText implements Text {

    /**
     * Arguments.
     */
    private final Collection<Object> args;

    /**
     * String format.
     */
    private final StringFormat format;

    /**
     * New {@link FormattedText} with {@link SimpleFormat}.
     *
     * @param template Template for {@link SimpleFormat}
     * @param arguments Arguments
     */
    public FormattedText(final String template, final Object... arguments) {
        this(new SimpleFormat(template), arguments);
    }

    /**
     * New {@link FormattedText} with {@link SimpleFormat}.
     *
     * @param template Template for {@link SimpleFormat}
     * @param arguments Arguments
     */
    public FormattedText(
        final String template,
        final Collection<Object> arguments
    ) {
        this(new SimpleFormat(template), arguments);
    }

    /**
     * Ctor.
     *
     * @param format String format
     * @param arguments Arguments
     */
    public FormattedText(
        final StringFormat format,
        final Object... arguments
    ) {
        this(format, Arrays.asList(arguments));
    }

    /**
     * Ctor.
     *
     * @param format String format
     * @param arguments Arguments
     */
    public FormattedText(
        final StringFormat format,
        final Collection<Object> arguments
    ) {
        this.format = format;
        this.args = Collections.unmodifiableCollection(arguments);
    }

    @Override
    public String asString() {
        return this.format.apply(this.args);
    }
}
