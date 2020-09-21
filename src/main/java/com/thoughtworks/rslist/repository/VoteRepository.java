package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.entity.VoteEntity;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VoteRepository extends PagingAndSortingRepository<VoteEntity,Integer> {
    List<VoteEntity> findAll();
    List<VoteEntity>findAllByUserIdAndRsEventId(int userId, int rsEventId, Pageable pageable);
      //下面的手写方法与上面的方法等价

    @Query("SELECT v FROM VoteEntity  v WHERE v.user.id = :userId AND v.rsEvent.id = :rsEventId")
    List<VoteEntity>findJdsdwBYsddsd(int userId,int rsEventId,Pageable pageable);

    @Query(nativeQuery = true,value="SELECT * FROM vote WHERE iser_id = :userId AND rs_event_id = :rsEventId")
    List<VoteEntity>sadasBYfdsfs(int userId,int rsEventId,Pageable pageable);

    List<VoteEntity>findAllByLocalDateTimeBetween(LocalDateTime start,LocalDateTime end);

}
