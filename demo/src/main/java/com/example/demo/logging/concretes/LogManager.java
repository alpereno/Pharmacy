package com.example.demo.logging.concretes;

import com.example.demo.logging.abstracts.PharmacyLogger;
import com.example.demo.models.Log;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class LogManager {
    PharmacyLogger pharmacyLogger;
    
    @Autowired
    public LogManager(@Qualifier("postgreLogger") PharmacyLogger pharmacyLogger){
        this.pharmacyLogger = pharmacyLogger;
    }
    
    public void logUserOperation(String userTrId, String logOperation, long logTime){
        pharmacyLogger.log(userTrId, logOperation, new Timestamp(logTime));
    }
    
    public List<Log> getAllLogs(){
        return pharmacyLogger.getAllLogs();
    }
}
