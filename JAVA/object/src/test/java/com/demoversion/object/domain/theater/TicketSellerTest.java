package com.demoversion.object.domain.theater;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TicketSellerTest {

  @Test
  void sellTo() {

    final Long baseAmount = 10L;
    final Long ticketFee = 1L;
    final Bag bag = new Bag(null, 10L);
    final Audience audience = new Audience(bag);

    final Ticket ticket = new Ticket();
    ticket.setFea(ticketFee);
    final TicketOffice ticketOffice = new TicketOffice(baseAmount, ticket);

    final MockTicketSeller ticketSeller = new MockTicketSeller(ticketOffice);

    ticketSeller.sellTo(audience);

    assertThat(ticketSeller.isSetToIsVerify()).isTrue();
  }

  class MockTicketSeller extends TicketSeller {

    private boolean setToIsVerify = false;

    public MockTicketSeller(final TicketOffice ticketOffice) {
      super(ticketOffice);
    }

    @Override
    public void sellTo(final Audience audience) {
      super.sellTo(audience);
      setToIsVerify = true;
    }

    public boolean isSetToIsVerify() {
      return setToIsVerify;
    }
  }
}