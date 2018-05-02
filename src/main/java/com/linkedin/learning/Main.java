package com.linkedin.learning;


import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

  public static void main(String[] args) {

    hello("My old friend", "Chris", "World");

    //Create
    String[] stringArray = new String[]{"hi", "hola", "bonjour"};
    Observable<String> observable = Observable.fromArray(stringArray);

    //
    List<Integer> list = Arrays.asList(new Integer[]{1, 2, 3, 4});
    Observable<Integer> integerObservable = Observable.fromIterable(list);

    //
    Observable<String> stringObservable = Observable.just("one object");


    Observable.just("Hello");

    Observable customObservableNonBlocking = customObservableNonBlocking();
  }

  public static void hello(String... names) {
    Observable.fromArray(names).subscribe(s -> System.out.println("Hello " + s + "!"));
  }


  public static Observable customObservableNonBlocking() {
    return Observable.create(emitter -> {
      try {
        List<String> todos = new ArrayList<>();
        for (String todo : todos) {
          emitter.onNext(todo);
        }
        emitter.onComplete();
      } catch (Exception e) {
        emitter.onError(e);
      }
    });
  }

}
