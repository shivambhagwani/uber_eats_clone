package com.ubereats.ubereatsclone.services.impl;

import com.ubereats.ubereatsclone.entities.Tax;
import com.ubereats.ubereatsclone.exceptions.DetailNotFoundException;
import com.ubereats.ubereatsclone.repositories.TaxRepository;
import com.ubereats.ubereatsclone.services.TaxService;
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
        List<Tax> taxes = this.taxRepository.findAll();
        for(Tax t : taxes) {
            if(t.getPincode().equals(pincode))
                return t.getTax();
        }
        return 0.00;
    }
}
