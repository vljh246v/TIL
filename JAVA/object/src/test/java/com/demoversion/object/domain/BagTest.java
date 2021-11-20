package com.demoversion.object.domain;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class BagTest {

  @Test
  void hasInvitation() {
    final Bag bag = new Bag(new Invitation(), 10L);

    assertThat(bag.hasInvitation()).isTrue();
  }

  @Test
  void hasTicket() {
    final Bag bag = new Bag(new Invitation(), 10L);
    bag.setTicket(new Ticket());

    assertThat(bag.hasTicket()).isTrue();
  }

  @Test
  void setTicket() {

    final MockBag bag = new MockBag(new Invitation(), 10L);

    bag.setTicket(new Ticket());

    assertThat(bag.setTicketIsVerify()).isTrue();

  }

  @Test
  void minusAmount() {
    final Long baseAmount = 10L;
    final Long minusAmount = 1L;
    final MockBag bag = new MockBag(new Invitation(), baseAmount);
    bag.minusAmount(minusAmount);

    assertThat(bag.getAmount()).isEqualTo(baseAmount - minusAmount);
  }

  @Test
  void plusAmount() {
    final Long baseAmount = 10L;
    final Long plusAmount = 1L;
    final MockBag bag = new MockBag(new Invitation(), baseAmount);
    bag.plusAmount(plusAmount);

    assertThat(bag.getAmount()).isEqualTo(baseAmount + plusAmount);
  }

  class MockBag extends Bag {

    private boolean setTicketVerify = false;

    public MockBag(final Long amount) {
      super(amount);
    }

    public MockBag(final Invitation invitation, final Long amount) {
      super(invitation, amount);
    }

    public boolean setTicketIsVerify() {
      return setTicketVerify;
    }

    @Override
    public void setTicket(final Ticket ticket) {
      super.setTicket(ticket);
      setTicketVerify = true;
    }
  }
}