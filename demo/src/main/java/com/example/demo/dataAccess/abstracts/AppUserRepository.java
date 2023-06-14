package com.example.demo.dataAccess.abstracts;

import com.example.demo.models.AppUser;

public interface AppUserRepository {
    boolean insertUser(AppUser appUser);
    AppUser getUserByTrId(String trId);
}
