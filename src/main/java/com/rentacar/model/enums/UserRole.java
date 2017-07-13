package com.rentacar.model.enums;

/**
 * Created by Andrei.Plesca
 */
public enum UserRole {
    USER("USER"),
    ADMIN("ADMIN");

    private final String role;

    UserRole(String userRole) {
        this.role = userRole;
    }

    public String getValue() {
        return role;
    }

    public boolean equalsName(String otherName) {
        return role.equals(otherName);
    }

    @Override
    public String toString() {
        return this.role;
    }
}
