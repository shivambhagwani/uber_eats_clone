package com.ubereats.ubereatsclone.tax.services;

import com.ubereats.ubereatsclone.tax.entity.Tax;

import java.util.List;

public interface TaxService {

    Tax addTaxRateInformation(Tax tax);

    List<Tax> getAllTaxRates();

    double getPincodeTax(String pincode);
}
