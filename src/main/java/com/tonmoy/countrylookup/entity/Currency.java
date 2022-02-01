package com.tonmoy.countrylookup.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Currency {
    @Id
    private String currencyName;
    private String exchangeRateIDR;
}
