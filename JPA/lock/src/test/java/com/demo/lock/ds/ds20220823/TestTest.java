package com.demo.lock.ds.ds20220823;

import com.demo.lock.ds.ds202000823.TestEntity;
import com.demo.lock.ds.ds202000823.TestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@DataJpaTest
class TestTest {

    @Autowired
    private TestRepository testRepository;

    @Test
    void findById() {
        // given
        testRepository.findById(1L);
    }
}