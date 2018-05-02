package com.linkedin.learning;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

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

    Observable.interval(100, TimeUnit.MILLISECONDS);

    customObservableNonBlocking
        .subscribe(eachVal -> System.out.println("Printing emited Value: " + eachVal));

  }

  public static void hello(String... names) {
    Observable.fromArray(names).subscribe(s -> System.out.println("Hello " + s + "!"));
  }


  public static Observable customObservableNonBlocking() {
    return Observable.create(emitter -> {
      try {
        IntStream.range(0, 50)
            .boxed().forEach(intVal ->
            simulateDelay(emitter, "Item: " + intVal.toString())
        );

        emitter.onComplete();
      } catch (Exception e) {
        emitter.onError(e);
      }
    });
  }

  public static void simulateDelay(ObservableEmitter emitter, String s) {
    try {
      Integer waitTime = Double.valueOf(Math.random() * 305).intValue();
      System.out.println("waitTime = " + waitTime);
      TimeUnit.MILLISECONDS.sleep(waitTime);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    emitter.onNext(s);
  }

}
