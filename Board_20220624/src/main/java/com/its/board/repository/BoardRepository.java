package com.its.board.repository;

import com.its.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    // native sql: update board_table set boardHits=board_hits+1 where board_id=? db에 쓸때는 update 부터

    // jpql(java persistence query language)
    // 아래 BoardEntity의 별칭(b) 를 써줘야함.
    @Modifying
    @Query(value = "update BoardEntity b set b.boardHits = b.boardHits + 1 where b.id = :id")
    void boardHits(@Param("id") Long id);
    // @Param 안에 id는 그 위에 맨 오른쪽 id 임.

}
