package com.linkedin.learning;

import io.reactivex.observers.DefaultObserver;

public class ConsolePrintObserver extends DefaultObserver {

  boolean printedHeader = false;

  @Override
  public void onNext(Object o) {
    if(!printedHeader) {
      System.out.println();
      System.out.println("Printing Observable using: " + ConsolePrintObserver.class);
      printedHeader = true;
    }

    System.out.print("[" + o + "]" + " - ");
  }

  @Override
  public void onError(Throwable e) {
    System.out.println("Error occurred: " + e.getMessage());
  }

  @Override
  public void onComplete() {
    System.out.println("Observable Complete");
    System.out.println();
  }
}
