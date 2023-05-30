package com.example.demo.logging.abstracts;

import com.example.demo.models.Log;
import java.sql.Timestamp;
import java.util.List;

public interface PharmacyLogger {
    void log(String userTrId, String logOperation, Timestamp logTime);
    List<Log> getAllLogs();
}
