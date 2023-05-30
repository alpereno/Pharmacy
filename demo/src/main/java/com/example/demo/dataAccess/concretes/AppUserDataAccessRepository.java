package com.example.demo.dataAccess.concretes;

import com.example.demo.dataAccess.abstracts.AppUserRepository;
import com.example.demo.models.AppUser;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.stereotype.Repository;

@Repository("appUserPostgreDB")
public class AppUserDataAccessRepository implements AppUserRepository{

    Connection c;
    Statement stmt;
    
    String url = "jdbc:postgresql://localhost:5432/Pharmacy";
    String user = "postgres";
    String password = "1234";
    
    @Override
    public boolean insertUser(AppUser appUser) {
        
        String sql = "INSERT INTO public.appUser (tcidnumber,password,isadmin) "
                + "VALUES ('" + appUser.getTrIdNumber() + "' , '" + appUser.getPassword() + "' , '" + appUser.isIsAdmin() +"');";
        
        executeSqlCommand(sql);
        
        System.out.println("appUser records created successfully");
        return true;
    }
    
    private void executeSqlCommand(String sql){
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, user, password);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            
            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            c.commit();
            
            stmt.close();
            c.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    @Override
    public AppUser getUserByTcId(String trId) {
        String sql = "SELECT * from public.appuser WHERE tcidnumber= '" + trId + "'";
        AppUser selectedAppUser = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, user, password);
            c.setAutoCommit(false);
            
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
            if (rs.getInt("id") != 0) {
                selectedAppUser = new AppUser(rs.getInt("id"), rs.getString("tcidnumber"), rs.getString("password"), rs.getBoolean("isadmin"));
                }    
            }        
            stmt.close();
            c.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return selectedAppUser;
    }
    
}
