package advent.of.code.day5;

import advent.of.code.DailyChallenge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

class Day5 extends DailyChallenge {

  @Override
  public String part1(List<String> input) {
    return process(input, this::single);
  }

  @Override
  public String part2(List<String> input) {
    return process(input, this::multiple);
  }

  private static String process(List<String> input, BiConsumer<Map<Integer, LinkedList<Character>>, String> movement) {
    Map<Integer, LinkedList<Character>> stackMap = new HashMap<>();

    int i = 0;
    for (; i < input.size(); i++) {
      String line = input.get(i);
      if (line.startsWith(" 1 ")) {  // stack number line
        continue;
      }

      if (line.isBlank()) {
        i++;
        break;
      }

      // items will be on idx 1, 5, 9, 13, ...
      int stack = 0;
      for (int j = 1; j < line.length(); j = j + 4) {

        stack++;

        if (line.charAt(j) == ' ') {
          continue;
        }

        stackMap.computeIfAbsent(stack, k -> new LinkedList<>()).addFirst(line.charAt(j));
      }
    }

    for (; i < input.size(); i++) {
      movement.accept(stackMap, input.get(i));
    }

    StringBuilder top = new StringBuilder();
    int j = 1;
    while (stackMap.get(j) != null) {
      top.append(stackMap.get(j).removeLast());
      j++;
    }

    return top.toString();
  }

  private void multiple(Map<Integer, LinkedList<Character>> stackMap, String move) {
    String[] words = move.split(" ");
    int count = Integer.parseInt(words[1]);
    int from = Integer.parseInt(words[3]);
    int to = Integer.parseInt(words[5]);

    List<Character> supplies = new ArrayList<>(count);
    for (int i = 0; i < count; i++) {
      // take from "from" stack
      supplies.add(stackMap.get(from).removeLast());
    }
    for (int i = count - 1; i >= 0; i--) {
      stackMap.get(to).addLast(supplies.get(i));
    }
  }

  private void single(Map<Integer, LinkedList<Character>> stackMap, String move) {
    String[] words = move.split(" ");
    int count = Integer.parseInt(words[1]);
    int from = Integer.parseInt(words[3]);
    int to = Integer.parseInt(words[5]);

    for (int i = 0; i < count; i++) {
      // take from "from" stack
      Character supply = stackMap.get(from).removeLast();
      // add to "to" stack
      stackMap.get(to).addLast(supply);
    }
  }
}
