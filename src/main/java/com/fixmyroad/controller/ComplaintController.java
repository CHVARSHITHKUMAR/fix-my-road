package com.fixmyroad.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.io.*;

import com.fixmyroad.model.Complaint;
import com.fixmyroad.repository.ComplaintRepository;
import com.fixmyroad.util.GeoUtil;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin
public class ComplaintController {

    private final ComplaintRepository repo;

    public ComplaintController(ComplaintRepository repo){
        this.repo = repo;
    }

    // REPORT NEW COMPLAINT WITH DUPLICATE DETECTION
    @PostMapping(value="/report", consumes="multipart/form-data")
    public Complaint report(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam("image") MultipartFile image,
            @RequestParam("userEmail") String userEmail,
            @RequestParam("severity") int severity,
            @RequestParam("trafficLevel") int trafficLevel
    ) throws Exception {

        List<Complaint> allComplaints = repo.findAll();

        for(Complaint c : allComplaints){

            double distance = GeoUtil.distanceMeters(
                    latitude,
                    longitude,
                    c.getLatitude(),
                    c.getLongitude()
            );

            // If pothole already exists within 20 meters
            if(distance <= 20){

                c.setReportCount(c.getReportCount() + 1);
                int newPriority = c.getReportCount() * c.getSeverity() * c.getTrafficLevel();
                c.setPriorityScore(newPriority);

                return repo.save(c);
            }
        }

        // Save new complaint if no duplicate found
        String uploadDir = System.getProperty("user.dir") + "/uploads/";

        File dir = new File(uploadDir);

        if(!dir.exists()){
            dir.mkdirs();
        }

        String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();

        File destination = new File(uploadDir + filename);

        image.transferTo(destination);

        Complaint complaint = new Complaint();

        complaint.setTitle(title);
        complaint.setDescription(description);
        complaint.setLatitude(latitude);
        complaint.setLongitude(longitude);
        complaint.setImageUrl("/uploads/" + filename);
        complaint.setStatus("Pending");
        complaint.setUserEmail(userEmail);
        complaint.setReportCount(1);
        complaint.setSeverity(severity);
        complaint.setTrafficLevel(trafficLevel);

        int priority = complaint.getReportCount() * severity * trafficLevel;

        complaint.setPriorityScore(priority);

        return repo.save(complaint);
    }

    // GET ALL COMPLAINTS (ADMIN)
    @GetMapping
    public List<Complaint> getAll(){
        return repo.findAll();
    }

    // GET USER COMPLAINTS
    @GetMapping("/user/{email}")
    public List<Complaint> getUserComplaints(@PathVariable String email){
        return repo.findByUserEmail(email);
    }

    // UPDATE COMPLAINT STATUS
    @PutMapping("/updateStatus/{id}")
    public Complaint updateStatus(
            @PathVariable Long id,
            @RequestParam String status
    ){

        Complaint complaint = repo.findById(id).orElseThrow();

        complaint.setStatus(status);

        return repo.save(complaint);
    }

    // DASHBOARD STATISTICS

    @GetMapping("/stats/total")
    public long totalIssues(){
        return repo.count();
    }

    @GetMapping("/stats/pending")
    public long pendingIssues(){
        return repo.countByStatus("Pending");
    }

    @GetMapping("/stats/progress")
    public long progressIssues(){
        return repo.countByStatus("In Progress");
    }

    @GetMapping("/stats/fixed")
    public long fixedIssues(){
        return repo.countByStatus("Fixed");
    }

}