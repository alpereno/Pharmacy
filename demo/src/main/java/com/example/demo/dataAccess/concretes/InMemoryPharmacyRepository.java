package com.example.demo.dataAccess.concretes;

import com.example.demo.dataAccess.abstracts.PharmacyRepository;
import com.example.demo.models.Pharmacy;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

                                        // ---------- OBSOLETE ----------
@Repository("inMemoryDB")
public class InMemoryPharmacyRepository implements PharmacyRepository{

    public static List<Pharmacy> DB = new ArrayList<>();
    
    public InMemoryPharmacyRepository(){
        DB.add(new Pharmacy(1, "a", "aaa"));
        DB.add(new Pharmacy(2, "b", "bbb"));
        DB.add(new Pharmacy(3, "c", "ccc"));
        DB.add(new Pharmacy(4, "d", "ddd"));
    }
    
    @Override
    public int insertPharmacy(Pharmacy pharmacy) {
        DB.add(new Pharmacy(0, pharmacy.getName(), pharmacy.getAddress()));
        return 1;
    }

    @Override
    public List<Pharmacy> getAllPharmacies() {
        return DB;
    }

    @Override
    public int updatePharmacyById(int id, Pharmacy pharmacy) {
        Pharmacy tempPharmacy = selectPharmacyById(id);
        if(tempPharmacy != null){
            int index = DB.indexOf(tempPharmacy);
            DB.set(index, pharmacy);
            //tempPharmacy = pharmacy;
            return 1;
        }
        return 0;
    }

    @Override
    public int deletePharmacyById(int id) {
        Pharmacy tempPharmacy = selectPharmacyById(id);
        if (tempPharmacy != null) {
            DB.remove(tempPharmacy);
            return 1;
        }
        return 0;
    }

    @Override
    public Pharmacy selectPharmacyById(int id) {
        for(Pharmacy currentPharmacy:DB){
            if (currentPharmacy.getId() == id) {
                return currentPharmacy;
            }
        }
        return null;
    }

    @Override
    public Pharmacy selectPharmacyByName(String pharmacyName) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
