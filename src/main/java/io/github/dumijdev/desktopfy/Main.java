package io.github.dumijdev.desktopfy;

import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException {
    Desktopfy.mainPage(Desktopfy.class.getResource("/index.html").getPath().replaceFirst("/", ""))
        .width(800)
        .height(600)
        .resizable(true)
        .open();

    /*var desktop1 = Desktopfy.url("https://github.com/dumijdev")
        .javaFxContainer()
        .open();*/
  }
}
