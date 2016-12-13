/*
 * Copyright 2016 Sascha Haeberling
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.s13g;

import com.google.common.base.Objects;

/**
 * Simple point class.
 */
public class Point {
  public final long x;
  public final long y;

  public Point(long first, long second) {
    this.x = first;
    this.y = second;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(x, y);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    Point other = (Point) obj;
    return x == other.x && y == other.y;
  }

  @Override
  public String toString() {
    return "(" + x + " ," + y + ")";
  }
}
