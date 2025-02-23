/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos;

import java.io.InputStream;

/**
 * Input.
 *
 * <p>Here is for example how {@link Input} can be used
 * in order to read the content of a text file:</p>
 *
 * <pre> String content = new TextOf(
 *   new InputOf(new File("/tmp/names.txt"))
 * ).asString();</pre>
 *
 * <p>Here {@link org.cactoos.io.InputOf} implements
 * {@link Input} and behaves like
 * one, providing read-only access to
 * the encapsulated {@link java.io.File}.</p>
 *
 * <p>There is no thread-safety guarantee.
 *
 * @see org.cactoos.io.InputOf
 * @since 0.1
 */
@FunctionalInterface
public interface Input {

    /**
     * Get read access to it.
     * @return InputStream to read from
     * @throws Exception If something goes wrong
     */
    InputStream stream() throws Exception;

}
