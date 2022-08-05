/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2022 Yegor Bugayenko
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
import org.cactoos.Text;
import org.cactoos.bytes.BytesOf;
import org.cactoos.func.IoCheckedFunc;
import org.cactoos.text.FormattedText;
import org.cactoos.text.TextOf;

/**
 * Classpath resource.
 *
 * <p>Pay attention that the name of resource must always be
 * global, <strong>not</strong> starting with a leading slash. Thus,
 * if you want to load a text file from {@code /com/example/Test.txt},
 * you must provide this name: {@code "com/example/Test.txt"}.</p>
 *
 * @see ClassLoader#getResource(String)
 * @since 0.1
 */
public final class ResourceOf implements Input {

    /**
     * Resource name.
     */
    private final Text path;

    /**
     * Fallback.
     */
    private final Func<? super Text, ? extends Input> fallback;

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
     * New resource input with {@link ClassLoader} from the specified {@link Class}.
     * @param res Resource name
     * @param cls Resource class loader
     * @since 0.49
     */
    public ResourceOf(final CharSequence res, final Class<?> cls) {
        this(res, cls.getClassLoader());
    }

    /**
     * New resource input with specified {@link ClassLoader}.
     * @param res Resource name
     * @param ldr Resource class loader
     */
    public ResourceOf(final CharSequence res, final ClassLoader ldr) {
        this(new TextOf(res), ldr);
    }

    /**
     * New resource input with {@link ClassLoader} from the specified {@link Class}.
     * @param res Resource name
     * @param fbk Fallback
     * @param cls Resource class loader
     * @since 0.49
     */
    public ResourceOf(final CharSequence res,
        final Func<? super CharSequence, ? extends Input> fbk, final Class<?> cls) {
        this(res, fbk, cls.getClassLoader());
    }

    /**
     * New resource input with specified {@link ClassLoader}.
     * @param res Resource name
     * @param fbk Fallback
     * @param ldr Resource class loader
     */
    public ResourceOf(final CharSequence res,
        final Func<? super CharSequence, ? extends Input> fbk, final ClassLoader ldr) {
        this(new TextOf(res), input -> fbk.apply(input.asString()), ldr);
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
    public ResourceOf(final CharSequence res,
        final Func<CharSequence, Input> fbk) {
        this(res, fbk, Thread.currentThread().getContextClassLoader());
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
     */
    public ResourceOf(final Text res) {
        this(res, Thread.currentThread().getContextClassLoader());
    }

    /**
     * New resource input with specified {@link ClassLoader}.
     * @param res Resource name
     * @param ldr Resource class loader
     */
    public ResourceOf(final Text res, final ClassLoader ldr) {
        this(
            res,
            input -> {
                throw new IOException(
                    new FormattedText(
                        "The resource \"%s\" was not found in %s",
                        input,
                        ldr
                    ).asString()
                );
            },
            ldr
        );
    }

    /**
     * New resource input with current context {@link ClassLoader}.
     * @param res Resource name
     * @param fbk Fallback
     */
    public ResourceOf(final Text res, final Text fbk) {
        this(res, input -> new InputOf(fbk));
    }

    /**
     * New resource input with current context {@link ClassLoader}.
     * @param res Resource name
     * @param fbk Fallback
     */
    public ResourceOf(final Text res, final Input fbk) {
        this(res, input -> fbk);
    }

    /**
     * New resource input with current context {@link ClassLoader}.
     * @param res Resource name
     * @param fbk Fallback
     */
    public ResourceOf(final Text res,
        final Func<? super Text, ? extends Input> fbk) {
        this(res, fbk, Thread.currentThread().getContextClassLoader());
    }

    /**
     * New resource input with specified {@link ClassLoader}.
     * @param res Resource name
     * @param fbk Fallback
     * @param ldr Resource class loader
     */
    public ResourceOf(final Text res,
        final Func<? super Text, ? extends Input> fbk, final ClassLoader ldr) {
        this.path = res;
        this.loader = ldr;
        this.fallback = fbk;
    }

    @Override
    public InputStream stream() throws Exception {
        InputStream input = this.loader.getResourceAsStream(
            this.path.asString()
        );
        if (input == null) {
            input = new IoCheckedFunc<>(this.fallback)
                .apply(this.path)
                .stream();
        }
        return input;
    }
}
