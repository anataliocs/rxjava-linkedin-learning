package com.linkedin.learning;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Scheduler;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Main {

  public static void main(String[] args) {

    hello("My old friend", "Chris", "World");

    /** Create Observables in many different ways
     *
     */

    //From array
    String[] stringArray = new String[]{"hi", "hola", "bonjour"};
    Observable<String> observable = Observable.fromArray(stringArray);

    //From a List
    List<Integer> list = Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
    Observable<Integer> integerObservable = Observable.fromIterable(list);

    //From a single string
    Observable<String> stringObservable = Observable.just("one object");

    //Defining a custom observable
    Observable customObservableNonBlocking = customObservableNonBlocking();

    //Define interval observable
    Observable interval = Observable.interval(100, TimeUnit.MILLISECONDS);

    /** END Create
     *
     */

    /** Applying operators to Observables
     *
     */
    //Examples of map

    System.out.println("Printing: integerObservable");
    System.out.println();

    //Using consumer static method reference
    System.out.println("BEFORE FILTER");
    integerObservable.subscribe(Main::printObservable);
    System.out.println();

    //Filter out values
    System.out.println("AFTER FILTER");
    integerObservable.filter(v -> v > 4).subscribe(Main::printObservable);
    System.out.println();

    //Filter map values to square
    System.out.println("AFTER FILTER AND MAP");
    integerObservable.filter(i -> i > 4).map(i -> Math.multiplyExact(i,i) ).subscribe(Main::printObservable);

    /** Using observers
     *
     */

    integerObservable.subscribe(new ConsolePrintObserver());


    /** Using scheduler
     *
     */

    System.out.print("Using Scheduler.io");
    integerObservable.subscribeOn(Schedulers.newThread()).subscribe(new ConsolePrintObserver());


    /** Using operators, observer and a scheduler
     *
     */

    integerObservable.subscribeOn(Schedulers.newThread()).filter(v -> v > 4).map(i -> Math.multiplyExact(i,i)).subscribe(new ConsolePrintObserver());
  }

  private static <T> void printObservable(T val) {

    System.out.print(val + " --- ");
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
