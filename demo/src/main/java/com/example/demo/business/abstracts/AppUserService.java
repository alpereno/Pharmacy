package com.example.demo.business.abstracts;

import com.example.demo.models.AppUser;

public interface AppUserService {
    boolean addUser(AppUser user) throws Exception;
    AppUser login(AppUser user);
}
