package advent.of.code.day3;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class Part2 {

  public static void main(String[] args) {
    URL inputPath = Part2.class.getClassLoader().getResource("day3.input");

    List<Character> badges = new ArrayList<>();

    String line;
    Set<Character> rucksack1, rucksack2, rucksack3;

    try (BufferedReader br = Files.newBufferedReader(Path.of(inputPath.toURI()))) {
      while ((line = br.readLine()) != null) {
        
        rucksack1 = items(line);
        rucksack2 = items(br.readLine());
        rucksack3 = items(br.readLine());

        HashSet<Character> badgeCandidates = new HashSet<>(rucksack1);
        badgeCandidates.retainAll(rucksack2);
        badgeCandidates.retainAll(rucksack3);

        badges.addAll(badgeCandidates); // should only be 1
      }
    } catch (IOException | URISyntaxException e) {
      throw new RuntimeException(e);
    }

    int sumOfPriorities = badges.stream()
        .map(Part2::priority)
        .reduce(0, Integer::sum);

    System.out.println(sumOfPriorities);
  }

  private static Set<Character> items(String line) {
    return line.chars()
        .mapToObj(c -> (char) c)
        .collect(Collectors.toSet());
  }

  private static int priority(char c) {
    if (c >= 97 ) {  // lowercase
      return c - 97 + 1;  // priority starts at 1
    } else {  // uppercase
      return c - 65 + 1 + 26;
    }
  }
}
