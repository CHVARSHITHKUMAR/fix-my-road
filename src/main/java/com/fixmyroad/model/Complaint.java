package com.fixmyroad.model;

import jakarta.persistence.*;

@Entity
public class Complaint {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String title;
private String description;
private double latitude;
private double longitude;
private String imageUrl;
private String status;
private String userEmail;
private int reportCount;
private int severity;
private int trafficLevel;
private int priorityScore;

public Long getId(){ return id; }
public void setId(Long id){ this.id=id; }

public String getTitle(){ return title; }
public void setTitle(String title){ this.title=title; }

public String getDescription(){ return description; }
public void setDescription(String description){ this.description=description; }

public double getLatitude(){ return latitude; }
public void setLatitude(double latitude){ this.latitude=latitude; }

public double getLongitude(){ return longitude; }
public void setLongitude(double longitude){ this.longitude=longitude; }

public String getImageUrl(){ return imageUrl; }
public void setImageUrl(String imageUrl){ this.imageUrl=imageUrl; }

public String getStatus(){ return status; }
public void setStatus(String status){ this.status=status; }
public String getUserEmail() {
    return userEmail;
}

public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
}
public int getReportCount() {
    return reportCount;
}

public void setReportCount(int reportCount) {
    this.reportCount = reportCount;
}
public int getSeverity() {
    return severity;
}

public void setSeverity(int severity) {
    this.severity = severity;
}

public int getTrafficLevel() {
    return trafficLevel;
}

public void setTrafficLevel(int trafficLevel) {
    this.trafficLevel = trafficLevel;
}

public int getPriorityScore() {
    return priorityScore;
}

public void setPriorityScore(int priorityScore) {
    this.priorityScore = priorityScore;
}

}