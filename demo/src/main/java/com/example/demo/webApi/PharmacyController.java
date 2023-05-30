package com.example.demo.webApi;

import com.example.demo.business.abstracts.PharmacyService;
import com.example.demo.models.AppUser;
import com.example.demo.models.Pharmacy;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pharmacies")
public class PharmacyController {

    private PharmacyService pharmacyService;
    
    @Autowired
    public PharmacyController(PharmacyService pharmacyService){
        this.pharmacyService = pharmacyService;
    }
    
    // AppUser is needed because the getall action should be logged if it is not an admin.
    @GetMapping("/getall")
    public List<Pharmacy> getAllPharmacies(@RequestBody AppUser appUser){
        return pharmacyService.getAllPharmacies(appUser);
    }
    
    // Adding new pharmacy
    @PostMapping()
    public boolean addPharmacy(@RequestBody Pharmacy pharmacy) throws Exception{
        boolean value = pharmacyService.addPharmacy(pharmacy) == 1;
        return value;
    }
    
    // Updating existing pharmacy. id of the pharmacy to be updated and "pharmacy" is required for new information (such as for new name/address)
    @PostMapping(path = "/update/{id}")
    public void updatePharmacy(@PathVariable("id") int id, @RequestBody Pharmacy pharmacy) throws Exception{
        pharmacyService.updatePharmacyById(id, pharmacy);
    }
    
    // deletion by id
    @PostMapping(path = "{id}")
    public void deletePharmacyById(@PathVariable("id")int id){
        pharmacyService.deletePharmacyById(id);
    }
    
    // selection operation. TrId of the person who chose it and id of the pharmacy he chose are sufficient
    @PostMapping(path = "/select/{userid}/{id}")
    public Pharmacy selectPharmacyById(@PathVariable("userid")String userTrId, @PathVariable("id")int id){
        System.out.println(userTrId + " is selecting... " + id);
        return pharmacyService.selectPharmacyById(userTrId, id);
    }
}
