package com.example.demo.logging.concretes;

import org.springframework.stereotype.Component;
import com.example.demo.logging.abstracts.PharmacyLogger;
import com.example.demo.models.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component("postgreLogger")
public class PharmacyPostgreSqlLogger implements PharmacyLogger{
    
    Connection c;
    Statement stmt;
    String url = "jdbc:postgresql://localhost:5432/Pharmacy";
    String user = "postgres";
    String password = "1234";

    @Override
    public void log(String userTrId, String logOperation, Timestamp logTime) {
        String sql = "INSERT INTO public.appuser_logs (tridno,logstring,timestamp) "
                + "VALUES ('" + userTrId+ "' , '" + logOperation + "' , '" + logTime + "');";
        
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, user, password);
            c.setAutoCommit(false);
            System.out.println("Opened log database successfully");
            
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
    public List<Log> getAllLogs() {
        String query = "SELECT * FROM appuser_logs;";
        List<Log> allLogs = new ArrayList<>();
        
        try {            
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, user, password);
            c.setAutoCommit(false);
            System.out.println("Opened log database successfully");

            stmt = c.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            while ( resultSet.next() ) {
               String trIdNo = resultSet.getString("tridno");
               String  logString = resultSet.getString("logstring");
               Timestamp  timeStamp = resultSet.getTimestamp("timestamp");
               Log newLog = new Log(trIdNo, logString, timeStamp);
               allLogs.add(newLog);
            }
            resultSet.close();
            stmt.close();
            c.close();
        } catch ( ClassNotFoundException | SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        
         System.out.println("Operation done successfully");
         return allLogs;
    }
}
