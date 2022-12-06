package advent.of.code.day6;

import advent.of.code.DailyChallenge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Day6 extends DailyChallenge {

  @Override
  public String part1(List<String> input) {
    return indexAfterUniqueSequence(input.get(0).toCharArray(), 4);
  }

  @Override
  public String part2(List<String> input) {
    return indexAfterUniqueSequence(input.get(0).toCharArray(), 14);
  }

  private static String indexAfterUniqueSequence(char[] chars, int sequenceLength) {
    int i = 0;
    while (i < chars.length) {
      int next = next(chars, i, sequenceLength);
      if (next == -1) {
        return "" + (i + sequenceLength);
      }
      i = next;
    }
    throw new IllegalArgumentException("No unique sequence");
  }

  private static int next(char[] chars, int sequenceStartIndex, int sequenceLength) {
    Map<Character, Integer> seenAt = new HashMap<>();
    for (int i = 0; i < sequenceLength; i++) {
      int idx = sequenceStartIndex + i;
      char ch = chars[idx];
      if (seenAt.containsKey(ch)) {
        return seenAt.get(ch) + 1;  // start next check from here
      } else {
        seenAt.put(ch, idx);
      }
    }
    return -1;  // found
  }
}
