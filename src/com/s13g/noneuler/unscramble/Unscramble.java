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

package com.s13g.noneuler.unscramble;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The question was taken from: https://careercup.com/question?id=5732347262533632
 * <p>
 * You are given a scrambled input sentence. Each word is scrambled independently, and the results
 * are concatenated. So:
 * <p>
 * 'elhloothtedrowl' might become 'hello to the world'
 * <p>
 * You have a dictionary with all words in it. Unscramble the sentence.
 */
public class Unscramble {
  public static void main(String[] args) {
    // An example dictionary.
    Set<String> dictionary = new HashSet<>();
    dictionary.add("hello");
    dictionary.add("world");
    dictionary.add("how");
    dictionary.add("the");
    dictionary.add("are");
    dictionary.add("you");
    dictionary.add("to");

    // Unscramble!
    Unscramble unscrambler = new Unscramble(dictionary).initialize();
    System.out.println("Unscrambled: " + unscrambler.unscramble("elhloothtedrowl"));
  }


  private final Set<String> mDictionary;
  private final Map<String, String> mProcessedDictionary;

  /**
   * Create the unscrambler with the give dictionary.
   */
  private Unscramble(Set<String> dictionary) {
    mDictionary = checkNotNull(dictionary);
    mProcessedDictionary = new HashMap<>(mDictionary.size());
  }

  /**
   * Initializes the dictionary by generating keys of the sorted chars of the original words.
   */
  private Unscramble initialize() {
    for (String dictWord : mDictionary) {
      char[] word = dictWord.toLowerCase().toCharArray();
      Arrays.sort(word);
      String sortedWord = String.valueOf(word);

      if (mProcessedDictionary.containsKey(sortedWord)) {
        throw new RuntimeException("Illegal dictionary. Cannot insert " + dictWord);
      }
      mProcessedDictionary.put(sortedWord, dictWord);
    }
    return this;
  }

  /**
   * Attempts to unscramble the input sentence, whose words are not separated by spaces.
   */
  private String unscramble(String input) {
    input = checkNotNull(input, "Input may not be null").toLowerCase();
    String result = "";
    for (int from = 0, to = 1; to <= input.length(); ++to) {
      char[] part = input.substring(from, to).toCharArray();
      Arrays.sort(part);
      String sorted = String.valueOf(part);
      if (mProcessedDictionary.containsKey(sorted)) {
        result += mProcessedDictionary.get(sorted);
        result += " ";
        from = to;
      }
    }
    return result;
  }
}
