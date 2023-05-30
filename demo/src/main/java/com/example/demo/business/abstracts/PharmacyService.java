package com.example.demo.business.abstracts;

import com.example.demo.models.AppUser;
import com.example.demo.models.Pharmacy;
import java.util.List;

public interface PharmacyService {
    List<Pharmacy> getAllPharmacies(AppUser appUser);
    int addPharmacy(Pharmacy pharmacy) throws Exception;
    int updatePharmacyById(int id, Pharmacy pharmacy) throws Exception;
    int deletePharmacyById(int id);
    Pharmacy selectPharmacyById(String userTrId, int id);
    Pharmacy selectPharmacyByName(String pharmacyName);
}
