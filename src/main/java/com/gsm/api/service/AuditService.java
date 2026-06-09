package com.gsm.api.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {
    private static AuditService serviceInstance = new AuditService();
    private final String filePath = "C:/Users/smadu/Desktop/proiectPAOJ/audit.csv";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private AuditService() {}

    public void logAction(String actionName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {

            String timestamp = LocalDateTime.now().format(formatter);

            bw.write(actionName + " , " + timestamp);
            bw.write("\n");

        } catch (IOException e) {
            System.err.println("Eroare la scrierea in fisierul de audit: " + e.getMessage());
        }
    }

    //getter
    public static AuditService getServiceInstance() { return serviceInstance; }
}