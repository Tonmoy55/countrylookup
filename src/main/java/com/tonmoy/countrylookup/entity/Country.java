package com.tonmoy.countrylookup.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Country {

    private String name;
    private List<Currency> currencyNames;
    private Integer population;

}
