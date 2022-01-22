package com.demo.ddd.common.jpa;

import com.demo.ddd.common.model.Money;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MoneyConverter implements AttributeConverter<Money, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final Money money) {
        if (money == null) {
            return null;
        } else {
            return money.getValue();
        }
    }

    @Override
    public Money convertToEntityAttribute(final Integer value) {
        if (value == null) {
            return null;
        } else {
            return new Money(value);
        }
    }
}