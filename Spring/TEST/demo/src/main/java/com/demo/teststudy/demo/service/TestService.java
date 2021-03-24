package com.demo.teststudy.demo.service;

import com.demo.teststudy.demo.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

  @Autowired
  TestMapper testMapper;

}
