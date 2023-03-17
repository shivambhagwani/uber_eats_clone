package com.ubereats.ubereatsclone.tax.services.impl;

import com.ubereats.ubereatsclone.tax.entity.Tax;
import com.ubereats.ubereatsclone.tax.repository.TaxRepository;
import com.ubereats.ubereatsclone.tax.services.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaxServiceImpl implements TaxService {

    @Autowired
    TaxRepository taxRepository;

    @Override
    public Tax addTaxRateInformation(Tax tax) {

        Tax tax1 = taxRepository.save(tax);
        return tax1;
    }

    @Override
    public List<Tax> getAllTaxRates() {
        List<Tax> taxes = this.taxRepository.findAll();

        return taxes;
    }

    @Override
    public double getPincodeTax(String pincode) throws Throwable {
        Tax tax = this.taxRepository.findByPincode(pincode);
        if(tax != null)
            return tax.getTax();
        return 0.00;
    }
}
