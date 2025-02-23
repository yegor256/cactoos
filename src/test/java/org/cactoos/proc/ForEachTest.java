/*
 * The MIT License (MIT)
 *
 * SPDX-FileCopyrightText: Copyright (c) 2017-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package org.cactoos.proc;

import java.util.Collection;
import org.cactoos.Proc;
import org.cactoos.list.ListOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.HasValues;

/**
 * Test case for {@link ForEach}.
 *
 * @since 1.0
 */
@SuppressWarnings("unchecked")
final class ForEachTest {

    @Test
    void worksWithGenerics() throws Exception {
        final Collection<Iterable<Number>> list = new ListOf<>();
        final Proc<? super Iterable<Number>> proc = list::add;
        new ForEach<Collection<Number>>(proc).exec(new ListOf<>(new ListOf<>(1), new ListOf<>(2)));
        new Assertion<>(
            "Must contain elements",
            list,
            new HasValues<>(
                new ListOf<>(1),
                new ListOf<>(2)
            )
        ).affirm();
    }

}
