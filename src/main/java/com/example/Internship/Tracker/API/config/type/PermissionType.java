package com.example.Internship.Tracker.API.config.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PermissionType {
    USER_WRITE,
    USER_READ,
    USER_DELETE,
    APPLICATION_READ,
    APPLICATION_DELETE,
    APPLICATION_WRITE,
    COMPANY_READ,
    COMPANY_WRITE,
    COMPANY_DELETE,
    INTERNSHIP_READ,
    INTERNSHIP_WRITE,
    INTERNSHIP_DELETE

}
