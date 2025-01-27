package io.github.dumijdev.desktopfy;

import java.io.IOException;
import java.time.Duration;

public class Main {
  public static void main(String[] args) throws IOException, InterruptedException {
    final var url = args.length > 0 ? args[0] : "https://github.com/dumijdev/dumijdev";

    final var desktop1 = Desktopfy.url(url).javaFxContainer().open();

    Thread.sleep(Duration.ofSeconds(10).toMillis());

    desktop1.close();
  }
}
