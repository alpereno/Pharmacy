package com.example.demo.webApi;

import com.example.demo.logging.concretes.LogManager;
import com.example.demo.models.Log;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logs")
public class LogController {
    LogManager logManager;
    
    @Autowired
    public LogController(LogManager logManager){
        this.logManager = logManager;
    }
    
    @GetMapping("/getall")
    public List<Log> getAllPharmacies(){
        return logManager.getAllLogs();
    }
}
