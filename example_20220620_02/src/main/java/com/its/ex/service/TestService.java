package com.its.ex.service;

import com.its.ex.dto.TestDTO;
import com.its.ex.entity.TestEntity;
import com.its.ex.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestService {

    private  final TestRepository testRepository;

    public Long save(TestDTO testDTO) {
        System.out.println("testDTO = " + testDTO);
        // TestDTO 객체에 담긴 값을 TestEntity 객체에 옮겨담기
//        TestEntity testEntity = new TestEntity();
//        testEntity.setColumn1(testDTO.getColumn1());
//        testEntity.setTestColumn2(testDTO.getTestColumn2());

        // toEntity() 메서드를 static 으로 선언했기때문에 직접 접근 가능 0
        // TestEntity.toEntity(testDTO); => 직접 접근
        // Long id = testRepository.save(TestEntity.toEntity(testDTO)).getId();

        TestEntity testEntity = TestEntity.toEntity(testDTO);
        Long id = testRepository.save(testEntity).getId();

        return id;
    }


//    public List<TestDTO> findAll() {
//        List<TestEntity> entityList = testRepository.findAll();
//        List<TestDTO> dtoList = new ArrayList<>();
//        for ( int i = 0; i < entityList.size(); i++) {
//            dtoList.add(TestDTO.toDTO(entityList.get(i)));
//        }
//        return dtoList;
//    }

    // 선생님이 하신거
    public List<TestDTO> findAll() {
        List<TestEntity> entityList = testRepository.findAll();
        List<TestDTO> findList = new ArrayList<>();
        for (TestEntity testEntity: entityList) {
            TestDTO testDTO = TestDTO.toDTO(testEntity);
            findList.add(testDTO);
        }
        return findList;
    }




    public void delete(Long id) {
        testRepository.deleteById(id);
    }

    public Long update(TestDTO testDTO) {
        // save 메서드 호출로 update 쿼리 가능(단, id가 같이 가야함.)
        TestEntity testEntity = TestEntity.toUpdateEntity(testDTO);
        Long id = testRepository.save(testEntity).getId();
        return id;
    }


    public TestDTO findById(Long id) {
        Optional<TestEntity> optionalTestEntity = testRepository.findById(id);
        if (optionalTestEntity.isPresent()) {
            // 조회된 결과가 있다.
//            TestEntity testEntity = optionalTestEntity.get();
//            TestDTO testDTO = TestDTO.toDTO(testEntity);
//            return  testDTO;

//            TestEntity testEntity = optionalTestEntity.get();
//            return TestDTO.toDTO(testEntity);
            return TestDTO.toDTO(optionalTestEntity.get());

        } else {
            // 조회된 결과가 없다.
            return null;
        }
    }

}
