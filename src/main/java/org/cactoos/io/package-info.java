/*
 * SPDX-FileCopyrightText: Copyright (c) 2017-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */

/**
 * Input/Output.
 *
 * @since 0.1
 * @todo #1449:30min We must find all the classes that closes stream of
 *  {@link org.cactoos.Input} or {@link org.cactoos.Output} that have been passed
 *  to them and document them about this behaviour and refer them to the existence
 *  of {@link org.cactoos.io.CloseShieldInput} and {@link org.cactoos.io.CloseShieldOutput}
 *  and how to use them.
 * @todo #1533:30min Exploit generic variance for package org.cactoos.io
 *  to ensure typing works as best as possible as it is explained in
 *  #1533 issue.
 */
package org.cactoos.io;
