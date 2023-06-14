package com.example.demo.business.concretes;

import com.example.demo.business.abstracts.PharmacyService;
import com.example.demo.dataAccess.abstracts.PharmacyRepository;
import com.example.demo.logging.concretes.LogManager;
import com.example.demo.models.AppUser;
import com.example.demo.models.Pharmacy;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PharmacyManager implements PharmacyService{
    PharmacyRepository pharmacyRepository;
    LogManager logManager;
                          // can be changed for a different database at any time 
                          // There is no need to change the business codes here because the new database must implement the PharmacyRepository interface.
    
    @Autowired            //@Qualifier("inMemoryDB")
    public PharmacyManager(@Qualifier("postgreDB") PharmacyRepository repository, LogManager logManager){
        this.pharmacyRepository = repository;
        this.logManager = logManager;
    }
    
    @Override
    public int addPharmacy(Pharmacy pharmacy) throws Exception{
        // Checks if name and address values are empty
        if (pharmacy.getName().equals("") && pharmacy.getAddress().equals("")){
            throw new Exception("name or address cannot be null");
        }
        return pharmacyRepository.insertPharmacy(pharmacy);
    }

    @Override
    public List<Pharmacy> getAllPharmacies(AppUser appUser) {
        List<Pharmacy> allPharmacies = pharmacyRepository.getAllPharmacies();
        if(allPharmacies != null){
            // this action should be logged if the user is not an administrator
            if (!appUser.isIsAdmin()) {
                logManager.logUserOperation(appUser.getTrIdNumber(), "get all operation", System.currentTimeMillis());
            }            
        }
        return allPharmacies;
    }

    @Override
    public int updatePharmacyById(int id, Pharmacy pharmacy) throws Exception {
        // Checks if the Name and address values of the NEW pharmacy are empty
        if (pharmacy.getName().equals("") && pharmacy.getAddress().equals("")){
            throw new Exception("name or address cannot be null");
        }
        return pharmacyRepository.updatePharmacyById(id, pharmacy);
    }

    @Override
    public int deletePharmacyById(int id) {
        // There is no need for any checks regarding id here.
        return pharmacyRepository.deletePharmacyById(id);
    }

    @Override
    public Pharmacy selectPharmacyById(String userTrId, int id) {
        Pharmacy selectedPharmacy = pharmacyRepository.selectPharmacyById(id);
        // if the pharmacy is not null this operation should be logged
        if (selectedPharmacy != null) {
            logManager.logUserOperation(userTrId, selectedPharmacy.getName() + " is selected", System.currentTimeMillis());
        }
        return selectedPharmacy;
    }

    // Written as an addition, currently not used
    @Override
    public Pharmacy selectPharmacyByName(String pharmacyName) {
        return pharmacyRepository.selectPharmacyByName(pharmacyName);
    }
}
