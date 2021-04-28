package com.demo.spring.teststudy.p20210426;

public class Study {

  private StudyStatus status = StudyStatus.DRAFT;

  private int limit;

  public Study(int limit) {
    this.limit = limit;
  }

  public StudyStatus getStatus() {
    return this.status;
  }

  public int getLimit() {
    return limit;
  }
}
