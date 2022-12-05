package advent.of.code.day3;

import advent.of.code.DailyChallenge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class Day3 implements DailyChallenge {

  @Override
  public String part1(List<String> input) {

    List<Character> erroneousItems = new ArrayList<>();

    for (String line : input) {
      Set<Character> compartment1 = items(line.substring(0, line.length() / 2));
      for (int i = line.length() / 2; i < line.length(); i++) {
        if (compartment1.contains(line.charAt(i))) {
          erroneousItems.add(line.charAt(i));
          break;
        }
      }
    }

    return erroneousItems.stream()
        .map(Day3::priority)
        .reduce(0, Integer::sum)
        .toString();
  }

  @Override
  public String part2(List<String> input) {
    List<Character> badges = new ArrayList<>();

    Set<Character> rucksack1, rucksack2, rucksack3;

    for (int i = 0; i < input.size(); i += 3) {

      rucksack1 = items(input.get(i));
      rucksack2 = items(input.get(i + 1));
      rucksack3 = items(input.get(i + 2));

      HashSet<Character> badgeCandidates = new HashSet<>(rucksack1);
      badgeCandidates.retainAll(rucksack2);
      badgeCandidates.retainAll(rucksack3);

      badges.addAll(badgeCandidates); // should only be 1
    }

    return badges.stream()
        .map(Day3::priority)
        .reduce(0, Integer::sum)
        .toString();
  }

  private static Set<Character> items(String line) {
    return line.chars()
        .mapToObj(c -> (char) c)
        .collect(Collectors.toSet());
  }

  private static int priority(char c) {
    if (c >= 97) {  // lowercase
      return c - 97 + 1;  // priority starts at 1
    } else {  // uppercase
      return c - 65 + 1 + 26;
    }
  }
}
