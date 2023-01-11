package com.example.gof.structural.adapter.before.security;

public interface UserDetailsService {

    UserDetails loadUser(String username);

}
