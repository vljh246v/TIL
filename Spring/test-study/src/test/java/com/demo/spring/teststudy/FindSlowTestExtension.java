package com.demo.spring.teststudy;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;

public class FindSlowTestExtension implements BeforeTestExecutionCallback,
    AfterTestExecutionCallback {

  private long THRESHOLD;
  public FindSlowTestExtension(long THRESHOLD){

    this.THRESHOLD = THRESHOLD;
  }

  @Override
  public void afterTestExecution(ExtensionContext context) throws Exception {
    Store store = getStore(context);
    String testMethodName = context.getRequiredTestMethod().getName();

    long start_time = store.remove("START_TIME", Long.class);
    long duration = System.currentTimeMillis() - start_time;

    if(duration >  THRESHOLD){
      System.out.printf("Please consider mark method [%s] with @SlowTest.\n", testMethodName);
    }
  }

  @Override
  public void beforeTestExecution(ExtensionContext context) throws Exception {
    Store store = getStore(context);
    store.put("START_TIME", System.currentTimeMillis());
  }

  private Store getStore(ExtensionContext context) {
    String testClassName = context.getRequiredTestClass().getName();
    String testMethodName = context.getRequiredTestMethod().getName();

    return context.getStore(Namespace.create(testClassName, testMethodName));
  }
}
