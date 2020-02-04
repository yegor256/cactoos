/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2020 Yegor Bugayenko
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

/**
 * Collections, tests.
 *
 * @since 0.14
 * @todo #1184:30min The behaviours of the classes of this package
 *  are akward because CollectionOf is based on a Scalar while some
 *  of the tests of the other classes are expecting mutable collections,
 *  except for Sticky itself.
 *  This resulted in the use of Sticky in most of the classes of this package
 *  during the resolution of #1184 to preserve the actual behaviour. Some
 *  tests of Sticky were also ignored because of this.
 *  It is as if CollectionOf was once made to be immutable but
 *  now there is Immutable for that: ask ARC what direction to take and
 *  apply it to make this whole package consistent. Keep in mind that
 *  Collection is particular in the way that it is an Iterable and that
 *  there are no direct implementation of Collection in Java, but only
 *  sub-interfaces (Set, List, etc) with their own implementation.
 *  Also don't forget to unignore or modify the tests of Sticky and improve
 *  the other tests to be clear about the expected behaviour.
 */
package org.cactoos.collection;
