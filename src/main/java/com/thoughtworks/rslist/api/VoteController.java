package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class VoteController {
    private final VoteRepository voteRepository;
    public VoteController(VoteRepository voteRepository){this.voteRepository=voteRepository;}

    private int pageGap =1;
    @GetMapping("/vote")
    public List<Vote> getVotes(
            @RequestParam int userId,
            @RequestParam int rsEventId,
            @RequestParam(defaultValue = "1") int pageIndex){
        Pageable pageable=PageRequest.of(pageIndex- pageGap,5);
//        List<VoteEntity> votes = voteRepository.findAllByUserIdAndRsEventId(userId,rsEventId,pageable);
        List<VoteEntity> votes = voteRepository.findJdsdwBYsddsd(userId,rsEventId,pageable);
        return  votes.stream().map(vote->Vote.builder()
                .userId(vote.getUser().getId())
                .rsEventId(vote.getRsEvent().getId())
                .voteNum(vote.getNum())
                .time(vote.getLocalDateTime())
                .build()
        ).collect(Collectors.toList());
    }
}
