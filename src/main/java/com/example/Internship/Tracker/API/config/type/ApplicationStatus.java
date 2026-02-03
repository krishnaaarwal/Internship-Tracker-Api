package com.example.Internship.Tracker.API.config.type;

import java.util.EnumSet;
import java.util.Set;

public enum ApplicationStatus {

    OFFER(EnumSet.noneOf(ApplicationStatus.class)) ,
    REJECTED(EnumSet.noneOf(ApplicationStatus.class)) ,
    INTERVIEW(EnumSet.of(OFFER,REJECTED)),
    APPLIED(EnumSet.of(INTERVIEW,REJECTED));


    private final Set<ApplicationStatus> allowedNext;

   ApplicationStatus(Set<ApplicationStatus> allowedNext){
        this.allowedNext = allowedNext;
    }

    public boolean canTransitionTo(ApplicationStatus nextStatus){
       return allowedNext.contains(nextStatus);
    }
}
