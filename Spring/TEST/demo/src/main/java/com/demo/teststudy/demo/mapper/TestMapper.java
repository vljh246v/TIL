package com.demo.teststudy.demo.mapper;


import com.demo.teststudy.demo.dto.TestDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TestMapper {

  @Select("SELECT * FROM test WHERE id = #{id}")
  TestDTO findById(Long id);
}
