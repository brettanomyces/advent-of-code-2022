package advent.of.code.day5;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

class Part1 {

  public static void main(String[] args) {
    URL inputPath = Part1.class.getClassLoader().getResource("day5.input");
    String line;
    try (BufferedReader br = Files.newBufferedReader(Path.of(inputPath.toURI()))) {

      Map<Integer, LinkedList<Character>> stackMap = new HashMap<>();

      while ((line = br.readLine()) != null) { // process stacks
        if (line.startsWith(" 1 ")) {  // stack number line
          br.readLine(); // swallow empty line
          break;
        }

        // items will be on idx 1, 5, 9, 13, ...
        int stack = 0;
        for (int i = 1; i < line.length(); i = i + 4) {

          stack++;

          if (line.charAt(i) == ' ') {
            continue;
          }

          stackMap.computeIfAbsent(stack, k -> new LinkedList<>()).addFirst(line.charAt(i));
        }
      }

      while ((line = br.readLine()) != null) { // process moves
        String[] words = line.split(" ");
        int count = Integer.parseInt(words[1]);
        int from = Integer.parseInt(words[3]);
        int to = Integer.parseInt(words[5]);

        // System.out.printf("move %s from %s to %s%n", count, from, to);

        for (int i = 0; i < count; i++) {
          // take from "from" stack
          Character supply = stackMap.get(from).removeLast();
          // add to "to" stack
          stackMap.get(to).addLast(supply);
        }
      }

      StringBuilder top = new StringBuilder();
      int i = 1;
      while (stackMap.get(i) != null) {
        top.append(stackMap.get(i).removeLast());
        i++;
      }

      System.out.println("top: " + top);
    } catch (IOException | URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }
}
