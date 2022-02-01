package com.tonmoy.countrylookup.service;


import com.tonmoy.countrylookup.dao.CurrencyDao;
import com.tonmoy.countrylookup.entity.Country;
import com.tonmoy.countrylookup.entity.Currency;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CountryService {

    @Autowired
    private CurrencyDao currencyDao;

    @Value("${api.key}")
    private String apiKey;


    /**
     * Get country information by country name
     *
     * @param countryName
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public Country getCountryInformation(String countryName) throws JSONException, IOException {

        JSONArray json = readJsonFromUrl("https://restcountries.com/v3.1/name/" + countryName); //External API call
        List<Currency> currencyList = new ArrayList<>();
        String name = null;
        Integer population = null;

        for (int i = 0; i < json.length(); i++) {

            //For getting country full name
            JSONObject jsonobject = json.getJSONObject(i);
            name = jsonobject.getJSONObject("name").getString("official");

            //For getting all available currency
            JSONObject jsonobject2 = jsonobject.getJSONObject("currencies");
            JSONArray currencyName = jsonobject2.names();

            for (int j = 0; j < currencyName.length(); j++) {
                Currency currency = new Currency();

                //For getting the Exchange rate
                String exchangeRate = "Not Found";
                try {
                    //External API call
                    JSONArray jsonExchangeRate = readJsonFromUrl("http://data.fixer.io/api/latest?access_key=" + apiKey + "&base=IDR&symbols=" + currencyName.getString(j));
                    for (int k = 0; k < jsonExchangeRate.length(); k++) {
                        jsonExchangeRate.getJSONObject(k);
                        exchangeRate = jsonobject.getJSONObject("rates").getString(currencyName.getString(j));
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }

                currency.setCurrencyName(currencyName.getString(j));
                currency.setExchangeRateIDR(exchangeRate);
                currencyList.add(currency);
            }

            //For getting population
            population = (Integer) jsonobject.get("population");

        }
        Country country = new Country();
        country.setName(name);
        country.setPopulation(population);
        country.setCurrencyNames(currencyList);

        //To store the exchange rates of the currencies in Currency table
        for (int i = 0; i < currencyList.size(); i++) {
            Optional<Currency> currency = currencyDao.findById(currencyList.get(i).getCurrencyName());
            try {
                currency.get().getCurrencyName();
            } catch (Exception e) {
                currencyDao.save(currencyList.get(i));
            }
        }
        return country;
    }

    //region private
    /**
     * Read all JSON data
     *
     * @param rd
     * @return
     * @throws IOException
     */
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    /**
     * Get JSON data from the external UI
     *
     * @param url
     * @return
     * @throws IOException
     * @throws JSONException
     */
    private static JSONArray readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            // JSONObject json = new JSONObject(jsonText);
            JSONArray json = new JSONArray(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
    //end region

}
