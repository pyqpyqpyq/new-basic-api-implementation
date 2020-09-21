package com.thoughtworks.rslist;

import com.sun.xml.txw2.annotation.XmlValue;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
public class VoteControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RsEventRepository rsEventRepository;
    @Autowired
    private VoteRepository voteRepository;

    private UserEntity user;
    private RsEventEntity rsEvent;

    @BeforeEach
    void setUp() {
        setData();
    }

    @AfterEach
    void tearDown() {
        voteRepository.deleteAll();
        rsEventRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldGetVotesInPageByUserIdAndRsEventId() throws Exception {
        mockMvc.perform(get("/vote")
                .param("userId", String.valueOf(user.getId()))
                .param("rsEventId", String.valueOf(rsEvent.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].userId", is(user.getId())))
                .andExpect(jsonPath("$[0].rsEventId", is(rsEvent.getId())))
                .andExpect(jsonPath("$[0].voteNum", is(1)));
        mockMvc.perform(get("/vote")
                .param("userId", String.valueOf(user.getId()))
                .param("rsEventId", String.valueOf(rsEvent.getId()))
                .param("pageIndex","2"))
                .andExpect((jsonPath("$",hasSize(3))))
                .andExpect((jsonPath("$[0].voteNum",is(6))));

    }

    private void setData() {
        user = UserEntity.builder()
                .userName("user0")
                .gender("male")
                .age(19)
                .phone("13579245810")
                .email("a@b.cn")
                .voteNum(10)
                .build();
        userRepository.save(user);
        rsEvent = RsEventEntity.builder()
                .eventName("event0")
                .keyWord("key")
                .user(user)
                .build();
        rsEventRepository.save(rsEvent);
        VoteEntity vote = VoteEntity.builder()
                .user(user)
                .rsEvent(rsEvent)
                .num(1)
                .localDateTime(LocalDateTime.now())
                .build();
        voteRepository.save(vote);

        vote = VoteEntity.builder()
                .user(user)
                .rsEvent(rsEvent)
                .num(2)
                .localDateTime(LocalDateTime.now())
                .build();
        voteRepository.save(vote);

        vote = VoteEntity.builder()
                .user(user)
                .rsEvent(rsEvent)
                .num(3)
                .localDateTime(LocalDateTime.now())
                .build();
        voteRepository.save(vote);

        vote = VoteEntity.builder()
                .user(user)
                .rsEvent(rsEvent)
                .num(4)
                .localDateTime(LocalDateTime.now())
                .build();
        voteRepository.save(vote);

        vote = VoteEntity.builder()
                .user(user)
                .rsEvent(rsEvent)
                .num(5)
                .localDateTime(LocalDateTime.now())
                .build();
        voteRepository.save(vote);

        vote = VoteEntity.builder()
                .user(user)
                .rsEvent(rsEvent)
                .num(6)
                .localDateTime(LocalDateTime.now())
                .build();
        voteRepository.save(vote);

        vote = VoteEntity.builder()
                .user(user)
                .rsEvent(rsEvent)
                .num(7)
                .localDateTime(LocalDateTime.now())
                .build();
        voteRepository.save(vote);

        vote = VoteEntity.builder()
                .user(user)
                .rsEvent(rsEvent)
                .num(8)
                .localDateTime(LocalDateTime.now())
                .build();
        voteRepository.save(vote);

    }


}
