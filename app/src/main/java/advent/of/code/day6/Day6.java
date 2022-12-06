package advent.of.code.day6;

import advent.of.code.DailyChallenge;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Day6 implements DailyChallenge {

  @Override
  public String part1(List<String> input) {
    return indexAfterUniqueSequence(input.get(0).toCharArray(), 4);
  }

  @Override
  public String part2(List<String> input) {
    return indexAfterUniqueSequence(input.get(0).toCharArray(), 14);
  }

  private static String indexAfterUniqueSequence(char[] chars, int sequenceSize) {
    for (int i = 0; i < chars.length; i++) {
      if (uniqueSequence(chars, i, sequenceSize)) {
        return "" + (i + sequenceSize);
      }
    }
    throw new IllegalArgumentException("No unique sequence");
  }

  private static boolean uniqueSequence(char[] chars, int sequenceStartIndex, int sequenceSize) {
    Set<Character> seen = new HashSet<>();
    for (int i = 0; i < sequenceSize; i++) {
      seen.add(chars[sequenceStartIndex + i]);
      if (seen.size() < i + 1) {
        return false;
      }
    }
    return true;
  }
}
