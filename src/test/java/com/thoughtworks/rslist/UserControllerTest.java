package com.thoughtworks.rslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.entity.UserEntity;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;

    @Test
    public void should_register_user1() throws Exception {
        User user = new User("pyq", "female", 18, "a@b.com", "12345678912");
        ObjectMapper objectMapper = new ObjectMapper();
        String request = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(request).contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect((status().isOk()));
        List<UserEntity> users = userRepository.findAll();

        assertEquals(1, users.size());
        assertEquals("pyq", users.get(0).getUserName());
    }

//    @Test
//    public void shouldDeleteUSer(){
//        UserEntity user = UserEntity.builder()
//                .userName("user0")
//                .gender("male")
//                .age(19)
//                .phone("13579245810")
//                .email("a@b.cn")
//                .voteNum(10)
//                .build();
//        userRepository.save(user);
//        RsEventEntity rsEvent =RsEventEntity.builder()
//                .eventName("event0")
//                .keyWord("key")
//                .userId(user.getId())
//                .build();
//        rsEventRepository.save(rsEvent);
//    }

//    @Test
//    public void should_search_and_return_user_info() throws Exception {
//        User user = new User("pyq", "female", 18, "a@b.com", "12345678912");
//        User user1 = new User("mayun", "male", 58, "alibaba@b.com", "88345678912");
//        ObjectMapper objectMapper = new ObjectMapper();
//        String request = objectMapper.writeValueAsString(user);
//        mockMvc.perform(post("/user").content(request).contentType(MediaType.APPLICATION_JSON).content(request))
//                .andExpect((status().isOk()));
//        mockMvc.perform(post("/user1").content(request).contentType(MediaType.APPLICATION_JSON).content(request))
//                .andExpect((status().isOk()));
//
//        mockMvc.perform(get("/getuser/1"))
//                .andExpect((status().isOk()))
//                .andExpect(jsonPath("$.name", is("mayun")))
//                .andExpect(jsonPath("$.email", is("alibaba@b.com")));
//    }


//    @Test
//    @Order(1)
//    public void should_register_user() throws Exception {
//        User user =new User("pyq", "female",18,"a@b.com","12345678912");
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonString = objectMapper.writeValueAsString(user);
//        mockMvc.perform(post("/user").content(jsonString).contentType(MediaType.APPLICATION_JSON))
//                .andExpect((status().isOk()));
//        mockMvc.perform(get("/user"))
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].name", is("pyq")))
//                .andExpect(jsonPath("$[0].gender", is("female")))
//                .andExpect(jsonPath("$[0].age", is(18)))
//                .andExpect(jsonPath("$[0].email", is("a@b.com")))
//                .andExpect(jsonPath("$[0].phone", is("12345678912")))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @Order(2)
//    public void neme_should_less_than_8() throws Exception {
//        User user =new User("pyq123456", "female",18,"a@b.com","12345678912");
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonString = objectMapper.writeValueAsString(user);
//        mockMvc.perform(post("/user").content(jsonString).contentType(MediaType.APPLICATION_JSON))
//                .andExpect((status().isBadRequest()));
//    }
//
//    @Test
//    @Order(3)
//    public void gender_should_not_null() throws Exception {
//        User user =new User("pyq", null,19,"a@b.com","12345678912");
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonString = objectMapper.writeValueAsString(user);
//        mockMvc.perform(post("/user").content(jsonString).contentType(MediaType.APPLICATION_JSON))
//                .andExpect((status().isBadRequest()));
//    }
//
//    @Test
//    @Order(4)
//    public void age_should_between_18_and_100() throws Exception {
//        User user =new User("pyq", "female",10,"a@b.com","12345678912");
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonString = objectMapper.writeValueAsString(user);
//        mockMvc.perform(post("/user").content(jsonString).contentType(MediaType.APPLICATION_JSON))
//                .andExpect((status().isBadRequest()));
//    }
//
//    @Test
//    @Order(5)
//    public void email_should_suit_format() throws Exception {
//        User user =new User("pyq", "female",19,"ab.com","12345678912");
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonString = objectMapper.writeValueAsString(user);
//        mockMvc.perform(post("/user").content(jsonString).contentType(MediaType.APPLICATION_JSON))
//                .andExpect((status().isBadRequest()));
//    }
//
//    @Test
//    @Order(6)
//    public void tel_should_suit_format() throws Exception {
//        User user =new User("pyq", "female",19,"a@b.com","123456789120");
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonString = objectMapper.writeValueAsString(user);
//        mockMvc.perform(post("/user").content(jsonString).contentType(MediaType.APPLICATION_JSON))
//                .andExpect((status().isBadRequest()));
//    }


}


