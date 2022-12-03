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

class Part1 {

  public static void main(String[] args) {
    URL inputPath = Part1.class.getClassLoader().getResource("day3.input");

    List<Character> erroneousItems = new ArrayList<>();

    String line;
    try (BufferedReader br = Files.newBufferedReader(Path.of(inputPath.toURI()))) {
      while ((line = br.readLine()) != null) {
        Set<Character> compartment1 = new HashSet<>();
        for (int i = 0; i < line.length() / 2; i++) {
          compartment1.add(line.charAt(i));
        }
        for (int i = line.length() / 2; i < line.length(); i++) {
          if (compartment1.contains(line.charAt(i))) {
            erroneousItems.add(line.charAt(i));
            break;
          }
        }
      }
    } catch (IOException | URISyntaxException e) {
      throw new RuntimeException(e);
    }

    int sumOfPriorities = erroneousItems.stream()
        .map(Part1::priority)
        .reduce(0, Integer::sum);

    System.out.println(sumOfPriorities);
  }

  private static int priority(char c) {
    if (c >= 97 ) {  // lowercase
      return c - 97 + 1;  // priority starts at 1
    } else {  // uppercase
      return c - 65 + 1 + 26;
    }
  }
}
