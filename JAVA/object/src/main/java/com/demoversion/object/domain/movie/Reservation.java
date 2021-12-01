package com.demoversion.object.domain.movie;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Reservation {

  private final Customer customer;
  private final Screening screening;
  private final Money fee;
  private final int audienceCount;
}
