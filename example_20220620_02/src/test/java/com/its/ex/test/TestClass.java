package com.its.ex.test;

import com.its.ex.dto.TestDTO;
import com.its.ex.service.TestService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class TestClass {
    @Autowired
    private TestService testService;

    // testService의 save() 호출
    // 호출 후 리턴값을 print
    @Test
    @Transactional
    @Rollback(value = true)
    public void saveTest() {
//        Long testResult = testService.save();
//        System.out.println("testResult = " + testResult);
        /**
         * 1. 저장할 TestDTO 객체를 만들고 필드값을 저장.
         * 2. 객체를 서비스의 save 메서드로 전달
         * 3. 전달 후 리턴 값을 받아서(Long)
         * 4. 그 리턴값으로 DB에서 findById를 하고
         * 5. DB에서 조회된 데이터와 1. 번에서 저장한 데이터가 일치하는지를 판단하여
         * 6. 일치하면 테스트 통과, 일치하지 않으면 실패
         *
         */
        // 1.
        TestDTO testDTO = new TestDTO("테스트데이터999","테스트데이터999");
        // 2. 3.
        Long saveId = testService.save(testDTO);
        // 4.
        TestDTO findDTO = testService.findById(saveId);
        // 5. 6.
        assertThat(testDTO.getColumn1()).isEqualTo(findDTO.getColumn1());


    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("findAll 테스트")
    public void findAllTest() {
        /**
         * 1. 3개의 테스트 데이터 저장
         * 2. findAll 호출
         * 3. 호출한 리스크의 크기가 3인지를 판단
         * 4. 3이면 테스트 통과, 아니면 테스트 실패
         *
         */
        // 3개의 테스트 데이터를 저장해봅시다. 반복문을 사용
//        TestDTO testDTO = new TestDTO("테스트데이터1","테스트데이터2");
//
//        for (int i= 1; i < 3; i++) {
//            // 두 줄로 쓴다면
////            testDTO = new TestDTO("테스트데이터1"+i, "테스트데이터2"+i);
////            testService.save(testDTO);
//
//            // 한 줄로 쓴다면
//            testService.save(new TestDTO("테스트데이터1"+i, "테스트데이터2"+i));
//        }

        // 자바 람다식(화살표함수), IntStream
        IntStream.rangeClosed(1, 3).forEach(i -> {
            testService.save(new TestDTO("테스트데이터1"+i, "테스트데이터2"+i));
        });

        // findAll 호출해서 리스트 크기가 3과 일치하는지 확인해봅시다.
        List<TestDTO> testDTOList = testService.findAll();
        assertThat(testDTOList.size()).isEqualTo(3);

    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("삭제테스트")
    public void deleteTest() {
//        assertThat(비교대상 여기).isNull();
        // 1. 삭제할 대상을 저장
        TestDTO testDTO = new TestDTO("테스트데이터1","테스트데이터2");
        // 2. 3.
        Long saveId = testService.save(testDTO);
        // 4. 삭제 수행
        // 첫 번째 방법
        testService.delete(saveId);
        TestDTO deleteDTO = testService.findById(saveId);
        // 두 번째 방법
//        testService.delete(saveId);
//        assertThat(testService.findById(saveId)).isNull();
        assertThat(deleteDTO).isNull();
    }
    // 방법 1
    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("수정 테스트")
    // 방법1
//    public void updateTest() {
//        /**
//         * 수정 테스트를 어떻게할지 시나리오 써보시고.
//         * assertThat().isNotEqualTo() 쓰면 됩니다.
//         * 1. 새로운 데이터 저장
//         * 2. 저장한 객체를 조회 (findById)
//         * 3. test_column1의 값을 변경하는 수정 처리
//         * 4. 수정 후 다시 객체 조회 (findById)
//         * 5. 2번에서 조회한 값과 5번에서 조회한 값이 같은지를 비교하여 (각각의 test_column1)
//         * 6. 다르면 테스트 성공, 같다면 테스트 실패
//         */
//        // 1. 저장
//        TestDTO testDTO = new TestDTO("테스트데이터1", "테스트데이터2");
//        Long saveId = testService.save(testDTO);
//        // 2.
//        TestDTO saveDTO = testService.findById(saveId);
//        // 3.
//        testDTO.setId(saveId);
//        testDTO.setColumn1("업데이트데이터1");
//        // 4.
//        Long updateId = testService.update(testDTO);
//        // 5.
//        TestDTO updateDTO = testService.findById(updateId);
//        // 6.
//        assertThat(updateDTO.getColumn1()).isEqualTo(saveDTO.getColumn1());
//    }

    // 방법 2
    // 선생님이 하신거
        public void updateTest() {
            // 1. 저장
            TestDTO testDTO = new TestDTO("수정데이터1", "수정데이터2");
            Long saveId = testService.save(testDTO);
            // 2.
            TestDTO saveDTO = testService.findById(saveId);
            // 3.
            TestDTO updateDTO = new TestDTO(saveId, "변경데이터1", "변경데이터2");
            // 4.
            testService.update(updateDTO);
            // 5.
            TestDTO afterUpdateDTO = testService.findById(saveId);
            // 6. 7.
            assertThat(updateDTO.getColumn1()).isEqualTo(afterUpdateDTO.getColumn1());
    }



        // 방법 1
//    @Test
//    @DisplayName("수정 테스트")
//    public void updateTest() {
//        TestDTO testDTO = new TestDTO("테스트데이터1", "테스트데이터2");
//        Long saveId = testService.save(testDTO);
//        testDTO.setId(saveId);
//        TestDTO updateDTO = new TestDTO("수정1", "수정2");
//        updateDTO.setId(saveId);
//        testService.update(updateDTO);
//        assertThat(testService.findById(saveId)).isNotEqualTo(testDTO);
//    }

    // 방법 2
//    @Test
//    @DisplayName("수정 테스트")
//    public void updateTest() {
//        /**
//         * 수정 테스트를 어떻게할지 시나리오 써보시고.
//         * assertThat().isNotEqualTo() 쓰면 됩니다.
//         */
//        // 1.
//        TestDTO testDTO = new TestDTO("테스트데이터1", "테스트데이터2");
//        Long saveId = testService.save(testDTO);
//        TestDTO updateDTO = testService.findById(saveId);
//        updateDTO.setColumn1("업데이트데이터1");
//        updateDTO.setTestColumn2("업데이트데이터2");
//        Long updateId = testService.update(updateDTO);
//        TestDTO resultDTO = testService.findById(updateId);
//        assertThat(resultDTO.getColumn1()).isNotEqualTo(testDTO.getColumn1());
//    }


}
