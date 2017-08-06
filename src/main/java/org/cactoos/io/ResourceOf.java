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
package org.cactoos.io;

import java.io.IOException;
import java.io.InputStream;
import org.cactoos.Func;
import org.cactoos.Input;
import org.cactoos.func.IoCheckedFunc;
import org.cactoos.text.FormattedText;

/**
 * Classpath resource.
 *
 * <p>Pay attention that the name of resource must always be
 * global, <strong>not</strong> starting with a leading slash. Thus,
 * if you want to load a text file from {@code /com/example/Test.txt},
 * you must provide this name: {@code "com/example/Test.txt"}.</p>
 *
 * @author Kirill (g4s8.public@gmail.com)
 * @version $Id$
 * @see ClassLoader#getResource(String)
 * @since 0.1
 */
public final class ResourceOf implements Input {

    /**
     * Resource name.
     */
    private final CharSequence path;

    /**
     * Fallback.
     */
    private final Func<CharSequence, Input> fallback;

    /**
     * Resource class loader.
     */
    private final ClassLoader loader;

    /**
     * New resource input with current context {@link ClassLoader}.
     * @param res Resource name
     */
    public ResourceOf(final CharSequence res) {
        this(res, Thread.currentThread().getContextClassLoader());
    }

    /**
     * New resource input with current context {@link ClassLoader}.
     * @param res Resource name
     * @param fbk Fallback
     */
    public ResourceOf(final CharSequence res, final CharSequence fbk) {
        this(res, input -> new InputOf(new BytesOf(fbk)));
    }

    /**
     * New resource input with current context {@link ClassLoader}.
     * @param res Resource name
     * @param fbk Fallback
     */
    public ResourceOf(final CharSequence res, final Input fbk) {
        this(res, input -> fbk);
    }

    /**
     * New resource input with current context {@link ClassLoader}.
     * @param res Resource name
     * @param fbk Fallback
     */
    public ResourceOf(final CharSequence res,
        final Func<CharSequence, Input> fbk) {
        this(res, fbk, Thread.currentThread().getContextClassLoader());
    }

    /**
     * New resource input with specified {@link ClassLoader}.
     * @param res Resource name
     * @param ldr Resource class loader
     */
    public ResourceOf(final CharSequence res, final ClassLoader ldr) {
        this(
            res,
            input -> {
                throw new IOException(
                    new FormattedText(
                        "Resource \"%s\" was not found",
                        input
                    ).asString()
                );
            },
            ldr
        );
    }

    /**
     * New resource input with specified {@link ClassLoader}.
     * @param res Resource name
     * @param ldr Resource class loader
     * @param fbk Fallback
     */
    public ResourceOf(final CharSequence res,
        final Func<CharSequence, Input> fbk, final ClassLoader ldr) {
        this.path = res;
        this.loader = ldr;
        this.fallback = fbk;
    }

    @Override
    public InputStream stream() throws IOException {
        InputStream input = this.loader.getResourceAsStream(
            this.path.toString()
        );
        if (input == null) {
            input = new IoCheckedFunc<>(this.fallback)
                .apply(this.path)
                .stream();
        }
        return input;
    }
}
