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

package org.cactoos.text;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.cactoos.Scalar;

/**
 * It takes a collection of strings
 * and returns a list of strings where
 * the first letter of each string is capitalized.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.56.0
 */
public final class StringsInCamelCase implements Scalar<List<String>> {

  /**
   * The collection of strings.
   */
  private final Collection<String> strings;

  /**
   * Ctor.
   *
   * @param strngs collection of strings.
   */
  public StringsInCamelCase(final Collection<String> strngs) {
    this.strings = strngs;
  }

  @Override
  public List<String> value() throws Exception {
    return this.strings.stream()
      .map(
        word -> word.replaceFirst(
          new TextOf(word.charAt(0)).toString(),
          new Upper(new TextOf(word.charAt(0))).toString()
        )
      ).collect(Collectors.toList());
  }
}
