package org.example.concert_booking.Service;


import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.example.concert_booking.Model.User;

public class CustomUserDetail implements UserDetails {


    private User user;


    public CustomUserDetail(User user) {
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convert the String role from your User model into a SimpleGrantedAuthority
        // Ensure user.getRole() returns something like "ROLE_USER" or "ROLE_ADMIN"
        return List.of(new SimpleGrantedAuthority(user.getRole()));
    }


    public String getFullname() {
        return user.getEmail();
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }


    @Override
    public String getUsername() {

        return user.getEmail();
    }


    @Override
    public boolean isAccountNonExpired() {

        return true;
    }


    @Override
    public boolean isAccountNonLocked() {

        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }


    @Override
    public boolean isEnabled() {
        return true;
    }
}