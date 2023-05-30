package com.example.demo.webApi;

import com.example.demo.business.abstracts.AppUserService;
import com.example.demo.models.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/appUsers")
public class AppUserController {
    AppUserService appUserService;
    
    @Autowired
    public AppUserController(AppUserService appUserService){
        this.appUserService = appUserService;
    }
    
    // new User registration. // Features such as TrId,password and admin information are needed with AppUser parameter
    @PostMapping("/addUser")
    public boolean addUser(@RequestBody AppUser appUser) throws Exception {
        return appUserService.addUser(appUser);
    }
    
    // User login.  // Features such as TrId,password and admin information are needed with AppUser parameter (to compare with registered users)
    @GetMapping
    public AppUser logIn(@RequestBody AppUser appUser){
        return appUserService.login(appUser);
    }
}
