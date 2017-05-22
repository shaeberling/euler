/*
 * Copyright 2017 Sascha Haeberling
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

package com.s13g.aoc

import com.google.common.io.CharStreams
import kotlin.text.Charsets
import java.io.InputStreamReader
import java.io.FileInputStream
import java.io.IOException


/**
 * Reads a file as string.
 */
@Throws(IOException::class)
fun readAsString(filename: String): String {
    val inputStream = FileInputStream(filename)
    return CharStreams.toString(InputStreamReader(inputStream, Charsets.UTF_8))
}
