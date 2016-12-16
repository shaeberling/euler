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

package com.s13g.aoc;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * We don't want to repeat this code in each puzzle.
 */
public class HashUtil {
  public static MessageDigest getMd5OrNull() {
    try {
      return MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException ignore) {
      return null;
    }
  }
}
