package advent.of.code;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public interface DailyChallenge {

  default String day() {
    return this.getClass().getSimpleName().toLowerCase();
  }

  default List<String> exampleData() {
    return load(day() + ".example");
  }

  default List<String> data() {
    return load(day() + ".input");
  }

  default List<String> load(String inputFile) {
    try {
      URL resource = this.getClass().getClassLoader().getResource(inputFile);
      return Files.readAllLines(Path.of(resource.toURI()));
    } catch (IOException | URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  String part1(List<String> input);

  String part2(List<String> input);
}
