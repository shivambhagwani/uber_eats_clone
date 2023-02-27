package com.ubereats.ubereatsclone.controllers;


import com.ubereats.ubereatsclone.entities.Tax;
import com.ubereats.ubereatsclone.services.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/taxes")
public class TaxController {
    @Autowired
    TaxService taxService;

    @PostMapping("/")
    public Tax addNewTaxInformation(@RequestBody Tax tax) {
        Tax tax1 = this.taxService.addTaxRateInformation(tax);

        return tax1;
    }

    @GetMapping("/")
    public List<Tax> getAllTaxes() {
        return this.taxService.getAllTaxRates();
    }

    @GetMapping("/{pincode}")
    public Double getTaxOfPincode(@PathVariable String pincode) throws Throwable {
        return this.taxService.getPincodeTax(pincode);
    }
}
