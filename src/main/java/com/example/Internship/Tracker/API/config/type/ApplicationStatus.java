package com.example.Internship.Tracker.API.config.type;

import java.util.EnumSet;
import java.util.Set;

public enum ApplicationStatus {
    OFFER,
    REJECTED,
    INTERVIEW,
    APPLIED;

    public boolean canTransitionTo(ApplicationStatus nextStatus) {
        return getAllowedNext().contains(nextStatus);
    }

    private Set<ApplicationStatus> getAllowedNext() {
        switch (this) {
            case OFFER:
                return EnumSet.noneOf(ApplicationStatus.class);
            case REJECTED:
                return EnumSet.noneOf(ApplicationStatus.class);
            case INTERVIEW:
                return EnumSet.of(OFFER, REJECTED);
            case APPLIED:
                return EnumSet.of(INTERVIEW, REJECTED);
            default:
                return EnumSet.noneOf(ApplicationStatus.class);
        }
    }
}