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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Part1 {

  private static final Pattern MOVE_PATTERN = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");

  public static void main(String[] args) {
    URL inputPath = Part1.class.getClassLoader().getResource("day5.input");
    String line;
    try (BufferedReader br = Files.newBufferedReader(Path.of(inputPath.toURI()))) {

      Map<Integer, LinkedList<Character>> stackMap = new HashMap<>();

      while ((line = br.readLine()) != null) { // process stacks
        if (line.isEmpty()) {  // break
          break;
        }
        if (line.startsWith(" 1 ")) {  // stack number line
          continue;
        }
        // items will be on idx 1, 5, 9, 13, ...
        int stack = 0;
        for (int i = 1; i < line.length(); i = i + 4) {
          stack++;
          if (line.charAt(i) == ' ') {
            continue;
          }
          stackMap.computeIfAbsent(stack, k -> new LinkedList<>())
              .addFirst(line.charAt(i));  // item at top of the stack is in pos 0
        }
      }

      while ((line = br.readLine()) != null) { // process moves
        Matcher matcher = MOVE_PATTERN.matcher(line);  // e.g. move 1 from 2 to 1;
        matcher.find();
        int count = Integer.parseInt(matcher.group(1));
        int from = Integer.parseInt(matcher.group(2));
        int to = Integer.parseInt(matcher.group(3));

        System.out.printf("move %s from %s to %s%n", count, from, to);

        for (int i = 0; i < count; i++) {
          // take from "from" stack
          Character supply = stackMap.get(from).removeLast();
          // add to "to" stack
          stackMap.get(to).addLast(supply);
        }
      }

      StringBuilder top = new StringBuilder();
      for (int i = 1; i < Integer.MAX_VALUE; i++) {
        if (stackMap.get(i) == null) {
          break;
        }
        top.append(stackMap.get(i).removeLast());
      }

      System.out.println("top: " + top.toString());
    } catch (IOException | URISyntaxException e) {
      throw new RuntimeException(e);
    }

  }
}
