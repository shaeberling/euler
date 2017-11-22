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

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.io.CharStreams;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Helper class for reading files that are e.g. required as input for some puzzles.
 */
public class FileUtil {
  public static String readAsString(File file) throws IOException {
    return readAsString(Preconditions.checkNotNull(file).getAbsolutePath());
  }

  public static String readAsString(String filename) throws IOException {
    FileInputStream inputStream = new FileInputStream(filename);
    return CharStreams.toString(new InputStreamReader(inputStream, Charsets.UTF_8));
  }
}
