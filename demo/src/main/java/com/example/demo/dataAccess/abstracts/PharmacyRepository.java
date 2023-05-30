package com.example.demo.dataAccess.abstracts;

import com.example.demo.models.Pharmacy;
import java.util.List;

public interface PharmacyRepository {
    List <Pharmacy> getAllPharmacies();
    int insertPharmacy(Pharmacy pharmacy);
    int updatePharmacyById(int id, Pharmacy pharmacy);
    int deletePharmacyById(int id);
    Pharmacy selectPharmacyById(int id);
    Pharmacy selectPharmacyByName(String pharmacyName);
}
