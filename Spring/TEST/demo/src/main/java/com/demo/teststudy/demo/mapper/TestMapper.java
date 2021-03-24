package com.demo.teststudy.demo.mapper;


import com.demo.teststudy.demo.dto.TestDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TestMapper {

  TestDTO findById(Long id);
}
