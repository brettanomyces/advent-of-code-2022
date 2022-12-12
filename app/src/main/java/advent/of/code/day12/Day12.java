package advent.of.code.day12;

import advent.of.code.DailyChallenge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

class Day12 extends DailyChallenge {

  // https://www.baeldung.com/java-dijkstra

  @Override
  public String part1(List<String> input) {
    Node[][] nodes = new Node[input.size()][];
    Node start = null;
    Node end = null;
    for (int i = 0; i < input.size(); i++) {
      Node[] row = new Node[input.get(i).length()];
      for (int j = 0; j < input.get(i).length(); j++) {
        char height = height(input.get(i).charAt(j));
        Node node = new Node(i, j, height);
        if (input.get(i).charAt(j) == 'S') {
          start = node;
        } else if (input.get(i).charAt(j) == 'E') {
          end = node;
        }
        row[j] = node;
      }
      nodes[i] = row;
    }

    Graph graph = new Graph(nodes);
    List<Node> path = graph.shortestPath(start, end);

    return "" + path.size();
  }

  static char height(char c) {
    if ('S' == c) {
      return 'a';
    } else if ('E' == c) {
      return 'z';
    } else {
      return c;
    }
  }

  @Override
  public String part2(List<String> input) {
    Node[][] nodes = new Node[input.size()][];
    List<Node> possibleStarts = new ArrayList<>();
    Node end = null;
    for (int i = 0; i < input.size(); i++) {
      Node[] row = new Node[input.get(i).length()];
      for (int j = 0; j < input.get(i).length(); j++) {
        char height = height(input.get(i).charAt(j));
        Node node = new Node(i, j, height);
        if (input.get(i).charAt(j) == 'S' || input.get(i).charAt(j) == 'a') {
          possibleStarts.add(node);
        } else if (input.get(i).charAt(j) == 'E') {
          end = node;
        }
        row[j] = node;
      }
      nodes[i] = row;
    }

    Graph graph = new Graph(nodes);
    int shortestRoute = Integer.MAX_VALUE;
    for (Node start : possibleStarts) {
      List<Node> path = graph.shortestPath(start, end);
      if (!path.isEmpty() && path.size() < shortestRoute) {
        shortestRoute = path.size();
      }
      graph.reset();

    }
    return "" + shortestRoute;
  }

  class Graph {
    private Node[][] nodes;

    public Graph(Node[][] nodes) {
      this.nodes = nodes;
    }

    public void reset() {
      for (int i = 0; i < nodes.length; i++) {
        for (int j = 0; j < nodes[i].length; j++) {
          nodes[i][j].shortestPath = new LinkedList<>();
          nodes[i][j].distance = Integer.MAX_VALUE;
        }
      }
    }

    public List<Node> shortestPath(Node start, Node end) {
      start.distance = 0;
      Set<Node> settled = new HashSet<>();
      Set<Node> unsettled = new HashSet<>();

      unsettled.add(start);

      while (!unsettled.isEmpty()) {
        Node current = getLowestDistance(unsettled);
        unsettled.remove(current);
        for (Node adjacent : adjacentNodes(current)) {
          if (!settled.contains(adjacent)) {
            calculateDistance(current, adjacent);
            unsettled.add(adjacent);
          }
        }
        settled.add(current);
      }
      return end.shortestPath;
    }

    public void calculateDistance(Node source, Node target) {
      int sourceDistance = source.distance;
      if (sourceDistance + 1 < target.distance) {
        target.distance = sourceDistance + 1;
        LinkedList<Node> shortestPath = new LinkedList<>(source.shortestPath);
        shortestPath.add(target);
        target.shortestPath = shortestPath;
      }
    }

    public Node getLowestDistance(Set<Node> unsettled) {
      int lowestDistance = Integer.MAX_VALUE;
      Node lowestDistanceNode = null;
      for (Node current : unsettled) {
        if (current.distance < lowestDistance) {
          lowestDistance = current.distance;
          lowestDistanceNode = current;
        }
      }
      return lowestDistanceNode;
    }

    public List<Node> adjacentNodes(Node node) {
      List<Node> adjacentNodes = new ArrayList<>();
      if (node.i >= 1) {
        Node adjacent = nodes[node.i - 1][node.j];
        if (adjacent.height - node.height <= 1) {
          adjacentNodes.add(adjacent);
        }
      }
      if (node.i < nodes.length - 1) {
        Node adjacent = nodes[node.i + 1][node.j];
        if (adjacent.height - node.height <= 1) {
          adjacentNodes.add(adjacent);
        }
      }
      if (node.j >= 1) {
        Node adjacent = nodes[node.i][node.j - 1];
        if (adjacent.height - node.height <= 1) {
          adjacentNodes.add(adjacent);
        }
      }
      if (node.j < nodes[0].length - 1) {
        Node adjacent = nodes[node.i][node.j + 1];
        if (adjacent.height - node.height <= 1) {
          adjacentNodes.add(adjacent);
        }
      }
      return adjacentNodes;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      for (int i = 0; i < nodes.length; i++) {
        for (int j = 0; j < nodes[i].length; j++) {
          builder.append(nodes[i][j]);
        }
        builder.append("\n");
      }
      return builder.toString();
    }
  }

  class Node {
    private int i;
    private int j;
    private char height;

    private List<Node> shortestPath = new LinkedList<>();
    private Integer distance = Integer.MAX_VALUE;

    public Node(int i, int j, char height) {
      this.i = i;
      this.j = j;
      this.height = height;
    }

    @Override
    public String toString() {
      return "" + height;
    }
  }
}
