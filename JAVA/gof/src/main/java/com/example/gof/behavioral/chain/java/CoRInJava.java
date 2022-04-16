package com.example.gof.behavioral.chain.java;

import javax.servlet.Filter;

public class CoRInJava {

    public static void main(String[] args) {
        Filter filter = (request, response, chain) -> chain.doFilter(request, response);
    }

}
