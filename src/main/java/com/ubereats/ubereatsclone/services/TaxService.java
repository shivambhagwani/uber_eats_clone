package com.ubereats.ubereatsclone.services;

import com.ubereats.ubereatsclone.entities.Tax;

import java.util.List;

public interface TaxService {

    Tax addTaxRateInformation(Tax tax);

    List<Tax> getAllTaxRates();

    double getPincodeTax(String pincode) throws Throwable;
}
