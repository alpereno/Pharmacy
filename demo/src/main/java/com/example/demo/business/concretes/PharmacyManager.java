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
    PharmacyRepository pharmacyRepositroy;
    LogManager logManager;
                        
                          // can be changed for a different database at any time 
                          // There is no need to change the business codes here because the new database must implement the PharmacyRepository interface.
    
    @Autowired            //@Qualifier("inMemoryDB")
    public PharmacyManager(@Qualifier("postgreDB") PharmacyRepository repository, LogManager logManager){
        this.pharmacyRepositroy = repository;
        this.logManager = logManager;
    }
    
    @Override
    public int addPharmacy(Pharmacy pharmacy) throws Exception{
        if (pharmacy.getName().equals("") && pharmacy.getAddress().equals("")){
            throw new Exception("name or address cannot be null");
        }
        return pharmacyRepositroy.insertPharmacy(pharmacy);
    }

    @Override
    public List<Pharmacy> getAllPharmacies(AppUser appUser) {
        List<Pharmacy> allPharmacies = pharmacyRepositroy.getAllPharmacies();
        if(allPharmacies != null){
            if (!appUser.isIsAdmin()) {
                logManager.logToPostgreSql(appUser.getTrIdNumber(), "get all operation", System.currentTimeMillis());
            }            
        }
        return pharmacyRepositroy.getAllPharmacies();
    }

    @Override
    public int updatePharmacyById(int id, Pharmacy pharmacy) throws Exception {
        if (pharmacy.getName().equals("") && pharmacy.getAddress().equals("")){
            throw new Exception("name or address cannot be null");
        }
        return pharmacyRepositroy.updatePharmacyById(id, pharmacy);
    }

    @Override
    public int deletePharmacyById(int id) {
        return pharmacyRepositroy.deletePharmacyById(id);
    }

    @Override
    public Pharmacy selectPharmacyById(String userTrId, int id) {
        Pharmacy selectedPharmacy = pharmacyRepositroy.selectPharmacyById(id);
        if (selectedPharmacy != null) {
            logManager.logToPostgreSql(userTrId, selectedPharmacy.getName() + " is selected", System.currentTimeMillis());
        }
        return selectedPharmacy;
    }

    // Written as an addition, currently not used
    @Override
    public Pharmacy selectPharmacyByName(String pharmacyName) {
        return pharmacyRepositroy.selectPharmacyByName(pharmacyName);
    }
}
