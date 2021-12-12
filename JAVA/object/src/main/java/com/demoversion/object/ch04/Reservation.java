package com.demoversion.object.ch04;

import com.demoversion.object.domain.movie.Money;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Reservation {

    private final Customer customer;
    private final Screening screening;
    private final Money money;
    private final int audienceCount;

}
