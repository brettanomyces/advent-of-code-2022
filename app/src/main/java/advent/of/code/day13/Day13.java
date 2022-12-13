package advent.of.code.day13;

import advent.of.code.DailyChallenge;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day13 extends DailyChallenge {


  @Override
  public String part1(List<String> input) {
    try {
      int indexSum = 0;
      for (int i = 0; i < input.size(); i += 3) {
        String left = input.get(i);
        String right = input.get(i + 1);
        Node leftTree = parseSignal(left);
        Node rightTree = parseSignal(right);
      }
    } catch (Exception e) {
      System.err.println(e);
    }
    return null;
  }

  @Override
  public String part2(List<String> input) {
    return null;
  }

  public Node parseSignal(String signal) {
    Node root = new Node(null);
    Node current = root;
    int i = 1;
    while (i < signal.length()) {
      if ('[' == signal.charAt(i)) {
        Node child = new Node(current);
        current.addChild(child);
        current = child;
        i++;
      } else if (']' == signal.charAt(i)) {
        current = current.parent;
        i++;
      } else if (',' == signal.charAt(i)) {
        i++;
      } else {  // start of number, input only contains 1, 2, 3, 4, 5, 6, 7, 8, 9 and 10.
        int value;
        if ('0' == signal.charAt(i + 1)) {
          value = 10;
          i += 2;
        } else {
          value = Integer.parseInt("" + signal.charAt(i));
          i++;
        }
        current.addChild(new Node(current, value));
      }
    }
    return root;
  }

  static class Node {

    Node parent;
    Integer value = null;  // only leaf nodes will have a value
    List<Node> children = new ArrayList<>();

    public Node(Node parent) {
      this.parent = parent;
    }

    public Node(Node parent, int value) {
      this.parent = parent;
      this.value = value;
    }

    void addChild(Node node) {
      children.add(node);
    }

    @Override
    public String toString() {
      if (value != null) {
        return value.toString();
      }
      return "[" + children.stream().map(Node::toString).collect(Collectors.joining(",")) + "]";
    }
  }
}
