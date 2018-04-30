package com.linkedin.learning;


import rx.Observable;

public class Main {

  public static void main(String[] args) {

    hello("Chris", "Jon Snow", "Ironman");
  }

  public static void hello(String... names) {
    Observable.from(names).subscribe(s -> System.out.println("Hello " + s + "!"));
  }
}
