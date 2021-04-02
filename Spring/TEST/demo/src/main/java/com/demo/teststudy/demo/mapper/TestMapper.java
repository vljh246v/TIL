package com.demo.teststudy.demo.mapper;


import com.demo.teststudy.demo.dto.TestDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {

  public TestDTO findById(Long id);
}
