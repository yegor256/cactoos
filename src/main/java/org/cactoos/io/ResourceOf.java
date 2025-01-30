/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2025 Yegor Bugayenko
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
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
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
    private final Func<Text, Input> fallback;

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
    @SuppressWarnings("PMD.UseProperClassLoader")
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
    @SuppressWarnings("PMD.UseProperClassLoader")
    public ResourceOf(final CharSequence res,
        final Func<CharSequence, Input> fbk, final Class<?> cls) {
        this(res, fbk, cls.getClassLoader());
    }

    /**
     * New resource input with specified {@link ClassLoader}.
     * @param res Resource name
     * @param fbk Fallback
     * @param ldr Resource class loader
     */
    public ResourceOf(final CharSequence res,
        final Func<CharSequence, Input> fbk, final ClassLoader ldr) {
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
                        "The resource \"%s\" was not found in %s (%s)",
                        input,
                        ldr,
                        ldr.getClass().getCanonicalName()
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
        final Func<Text, Input> fbk) {
        this(res, fbk, Thread.currentThread().getContextClassLoader());
    }

    /**
     * New resource input with specified {@link ClassLoader}.
     * @param res Resource name
     * @param fbk Fallback
     * @param ldr Resource class loader
     */
    public ResourceOf(final Text res,
        final Func<Text, Input> fbk, final ClassLoader ldr) {
        this.path = res;
        this.loader = ldr;
        this.fallback = fbk;
    }

    @Override
    @SuppressWarnings("PMD.CloseResource")
    public InputStream stream() throws Exception {
        if (this.path == null) {
            throw new IllegalArgumentException(
                "The \"path\" of the resource is NULL, which is not allowed"
            );
        }
        if (this.loader == null) {
            throw new IllegalArgumentException(
                "The \"classloader\" is NULL, which is not allowed"
            );
        }
        final InputStream input;
        final String pth = this.path.asString();
        final URL resource = this.loader.getResource(pth);
        if (pth.endsWith("/")
            && resource != null
            && "jar".equals(resource.getProtocol())
        ) {
            input = new JarDirectoryFileNameStream(resource, pth)
                .files();
        } else if (resource != null) {
            input = resource.openStream();
        } else {
            if (this.fallback == null) {
                throw new IllegalArgumentException(
                    "The \"fallback\" is NULL, which is not allowed"
                );
            }
            input = new IoCheckedFunc<>(this.fallback)
                .apply(this.path)
                .stream();
        }
        return input;
    }

    /**
     * Class for creating a stream of file names from a directory to a jar.
     *
     * @since 0.56.2
     */
    private static final class JarDirectoryFileNameStream {

        /**
         * URL of the jar file.
         */
        private final URL url;

        /**
         * Path to the directory in the jar file.
         */
        private final String path;

        /**
         * Ctor.
         *
         * @param jarurl URL of the jar file.
         * @param pth The directory for which we want to get a list of files.
         */
        JarDirectoryFileNameStream(final URL jarurl, final String pth) {
            this.url = jarurl;
            this.path = pth;
        }

        /**
         * Create InputStream of file names from directory to jar.
         *
         * @return Stream with file names.
         * @throws Exception If something goes wrong
         */
        public InputStream files() throws Exception {
            try (JarFile jar = new JarFile(this.extract())) {
                return new InputStreamOf(
                    jar
                        .stream()
                        .map(JarEntry::getName)
                        .filter(
                            name -> !this.path.equals(name)
                                && name.lastIndexOf(this.path) >= 0
                        )
                        .map(name -> name.substring(this.path.length()))
                        .collect(Collectors.joining("\n"))
                        .getBytes(StandardCharsets.UTF_8)
                    );
            }
        }

        /**
         * Extracts the path to a jar file from a URL.
         *
         * @return Path to jar file.
         * @throws URISyntaxException If this URL cannot be converted to a URI.
         */
        private String extract() throws URISyntaxException {
            final String fullpath = this.url.toURI().getSchemeSpecificPart();
            final int idx = fullpath.indexOf("!/");
            return fullpath.substring("file:".length(), idx);
        }
    }
}
