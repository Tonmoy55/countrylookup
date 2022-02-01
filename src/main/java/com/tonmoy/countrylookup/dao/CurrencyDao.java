package com.tonmoy.countrylookup.dao;

import com.tonmoy.countrylookup.entity.Currency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyDao extends CrudRepository<Currency, String> {
}
