package advent.of.code.day7;

import advent.of.code.DailyChallenge;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class Day7 extends DailyChallenge {

  @Override
  public String part1(List<String> input) {
    Directory root = null;
    Directory current = null;
    for (String line : input) {
      System.out.println(line);
      if (line.equals("$ cd /")) {
        root = new Directory(null, "/");
        current = root;
      } else if (line.equals("$ cd ..")) {
        current = current.parent;
      } else if (line.startsWith("$ cd")) {
        // move
        current = current.subDirectory(line.substring(5));
      } else if (line.startsWith("$ ls")) {
        continue;
      } else if (line.startsWith("dir")) {
        // directory
        current.mkdir(new Directory(current, line.substring(4)));
      } else {
        // file
        String[] parts = line.split(" ");
        current.file(new File(parts[1], Integer.parseInt(parts[0])));
      }
    }
    return "" + root.flatMap()
        .map(Directory::size)
        .filter(size -> size < 100_000)
        .reduce(0, Integer::sum);
  }

  @Override
  public String part2(List<String> input) {
    return null;
  }

  static class Directory {

    private final String name;
    private final Directory parent;

    private final Map<String, Directory> subDirectories = new HashMap<>();
    private final Set<File> files = new HashSet<>();

    public Directory(Directory parent, String name) {
      this.parent = parent;
      this.name = name;
    }

    public void mkdir(Directory subDirectory) {
      subDirectories.putIfAbsent(subDirectory.name, subDirectory);
    }

    public Directory subDirectory(String name) {
      return subDirectories.get(name);
    }

    public void file(File file) {
      files.add(file);
    }

    public Stream<Directory> flatMap() {
      return Stream.concat(
          subDirectories.values().stream(),
          subDirectories.values().stream().flatMap(Directory::flatMap)
      );
    }

    public int size() {
      int sizeOfFiles = files.stream().map(File::size).reduce(0, Integer::sum);
      int sizeOfDirs = subDirectories.values().stream().map(Directory::size).reduce(0, Integer::sum);
      int total = sizeOfFiles + sizeOfDirs;

      return total;
    }
  }

  record File (
      String name,
      int size
  ) { }
}
