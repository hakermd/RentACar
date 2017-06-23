package com.rentacar.model.enums;

/**
 * Created by Andrei.Plesca
 */
public enum UserRole {
    USER("USER"),
    ADMIN("ADMIN");

    private final String userRole;

    UserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getValue() {
        return userRole;
    }

    public boolean equalsName(String otherName) {
        return userRole.equals(otherName);
    }

    public String toString() {
        return this.userRole;
    }
}
