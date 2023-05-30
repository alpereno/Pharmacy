package com.example.demo.business.concretes;

import com.example.demo.business.abstracts.AppUserService;
import com.example.demo.dataAccess.abstracts.AppUserRepository;
import com.example.demo.models.AppUser;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AppUserManager implements AppUserService{
    
    AppUserRepository appUserRepository;
    
                          // can be changed for a different database at any time 
                          // There is no need to change the business codes here because the new database must implement the AppUserRepository interface.
    
    @Autowired
    public AppUserManager(@Qualifier("appUserPostgreDB") AppUserRepository appUserRepositroy){
        this.appUserRepository = appUserRepositroy;
    }
    
    @Override
    public boolean addUser(AppUser user) throws NoSuchAlgorithmException{
        String userTrIdNumber = user.getTrIdNumber();
        String userPassword = user.getPassword();
        
        if (userTrIdNumber == null || userTrIdNumber.length() != 11) {
            System.out.println("tc must be 11 digits");
        }
        
        if (userPassword == null || userPassword.length() > 10) {
            System.out.println("password must be 1-10 digits");
        }
        
        int lastDigit = userTrIdNumber.charAt(userTrIdNumber.length()-1);
        // According to ASCII, numbers start at 48(0) and end at 57(9). I subtract 48 to know what number the last digit is.
        lastDigit -= 48;
        if (lastDigit%2 != 0) {
            System.out.println("the last digit of TC cannot end with an odd number");
            return false;
        }
        
        if (appUserRepository.getUserByTcId(userTrIdNumber) != null) {
            System.out.println("this tc already exists");
            return false;
        }
        
        userPassword = getMD5(userPassword);
        user.setPassword(userPassword);
        return appUserRepository.insertUser(user);
    }

    @Override
    public AppUser login(AppUser user){
        AppUser dbUser = appUserRepository.getUserByTcId(user.getTrIdNumber());
        if (dbUser != null) {
            String userPassword = user.getPassword();
            String dbUserPasswrod = dbUser.getPassword();
            //System.out.println("user pw b4 md5 = " + userPassword);
            try{
                userPassword = getMD5(userPassword);
            }
            catch(NoSuchAlgorithmException e){
                System.out.println("md5 exception");
            }
            
            if (!userPassword.equals(dbUserPasswrod)) {
                return null;
            }
        }
        return dbUser;
    }
    
    private String getMD5(String string) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] hash = messageDigest.digest(string.getBytes());
        BigInteger bigInteger = new BigInteger(1, hash);
        StringBuilder stringBuilder = new StringBuilder(bigInteger.toString(16));
        
        while (stringBuilder.length() < 32) {
            stringBuilder.insert(0, "0");
        }
        
        return stringBuilder.toString();
    }
}
