package com.example.demo.dataAccess.concretes;

import com.example.demo.dataAccess.abstracts.PharmacyRepository;
import com.example.demo.models.Pharmacy;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository("postgreDB")
public class PharmacyDataAccessRepository implements PharmacyRepository{
    Connection c;
    Statement stmt;
    String url = "jdbc:postgresql://localhost:5432/Pharmacy";
    String user = "postgres";
    String password = "1234";
    
    @Override
    public List<Pharmacy> getAllPharmacies() {
        String query = "SELECT * FROM pharmacy;";
        List<Pharmacy> allPharmacies = new ArrayList<>();
        
        try {            
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, user, password);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            while ( resultSet.next() ) {
               int id = resultSet.getInt("id");
               String  name = resultSet.getString("name");
               String  address = resultSet.getString("address");
               Pharmacy newPharmacy = new Pharmacy(id, name, address);
               allPharmacies.add(newPharmacy);
            }
            resultSet.close();
            stmt.close();
            c.close();
        } catch ( ClassNotFoundException | SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        
         System.out.println("get all Operation done successfully in pdar");
         return allPharmacies;
    }

    @Override
    public int insertPharmacy(Pharmacy pharmacy) {
        String sql = "INSERT INTO public.pharmacy (name,address) "
                + "VALUES ('" + pharmacy.getName() + "' , '" + pharmacy.getAddress() + "');";
        executeSqlCommand(sql);

        System.out.println("Records created successfully");
        return 1;
    }

    @Override
    public int updatePharmacyById(int id, Pharmacy pharmacy) {
        String sql = "UPDATE pharmacy SET name = '" + pharmacy.getName() + "', address = '" + pharmacy.getAddress() +"' WHERE id = " + id;
        executeSqlCommand(sql);

        System.out.println("Operation done successfully");
        return 1;
    }

    @Override
    public int deletePharmacyById(int id) {
        String sql = "DELETE from pharmacy where ID = " + id + " ;";
        executeSqlCommand(sql);
        
        System.out.println("Delete Operation done successfully");
        return 1;
    }

    @Override
    public Pharmacy selectPharmacyById(int id) {
        String sql = "SELECT * from public.pharmacy WHERE id= " + id;
        return executeSelectSqlCommand(sql);
    }
    
    @Override
    public Pharmacy selectPharmacyByName(String pharmacyName) {
        String sql = "SELECT * from public.pharmacy WHERE name= '" + pharmacyName + "'";
        return executeSelectSqlCommand(sql);
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
    
    private Pharmacy executeSelectSqlCommand(String sql){
        Pharmacy selectedPharmacy = null;
        
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, user, password);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                selectedPharmacy = new Pharmacy(rs.getInt("id"), rs.getString("name"), rs.getString("address"));
            }        
            stmt.close();
            c.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("select opreation = " + selectedPharmacy.getName());
        return selectedPharmacy;
    }
}
