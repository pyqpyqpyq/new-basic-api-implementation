package com.thoughtworks.rslist;

import com.thoughtworks.rslist.api.RsController;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//重要注释
import static org.hamcrest.Matchers.*;
//重要注释

@SpringBootTest
@AutoConfigureMockMvc
class RsControllerTest {

//    RsController rsController;
//    @BeforeEach
//    public void SetUp(){
//        rsController=new RsController(userRepository, rsEventRepository);
//    }

    @Autowired
    UserRepository userRepository;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    RsEventRepository rsEventRepository;

    @BeforeEach
    void init() {
        userRepository.deleteAll();
        rsEventRepository.deleteAll();
    }

    @Test
    public void shouldAddRsEventWhenUserExists() throws Exception {
        UserEntity user = UserEntity.builder()
                .userName("user0")
                .gender("male")
                .age(19)
                .phone("13579245810")
                .email("a@b.cn")
                .voteNum(10)
                .build();
        userRepository.save(user);
        String jsonValue = "{\"eventName\":\"猪肉涨价了\",\"keyWord\":\"经济\",\"userId\": " + user.getId() + "}";

        mockMvc.perform(post("/rs/event")
                .content(jsonValue)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        List<RsEventEntity> rsEvents = rsEventRepository.findAll();
        assertEquals(1, rsEvents.size());
        assertEquals("猪肉涨价了", rsEvents.get(0).getEventName());
        assertEquals("经济", rsEvents.get(0).getKeyWord());
        assertEquals(user.getId(), rsEvents.get(0).getUser().getId());
    }

    @Test
    public void shouldModifyRsEventWhenUserExists() throws Exception {
        UserEntity user = UserEntity.builder()
                .userName("user0")
                .gender("male")
                .age(19)
                .phone("13579245810")
                .email("a@b.cn")
                .voteNum(10)
                .build();
        userRepository.save(user);
        String jsonValue = "{\"eventName\":\"热搜事件名\",\"keyWord\":\"关键字\",\"userId\": " + user.getId() + "}";

        mockMvc.perform(post("/rs/event")
                .content(jsonValue)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());


        String newJsonValue = "{\"eventName\":\"新的热搜事件名\",\"keyWord\":\"新的关键字\",\"userId\": " + user.getId() + "}";

        mockMvc.perform(patch("/rs/1")
                .content(newJsonValue)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        List<RsEventEntity> rsEvents = rsEventRepository.findAll();
        assertEquals(1, rsEvents.size());
        assertEquals("新的热搜事件名", rsEvents.get(0).getEventName());
        assertEquals("新的关键字", rsEvents.get(0).getKeyWord());
        assertEquals(user.getId(), rsEvents.get(0).getUser().getId());
    }


    @Test
    void shouldAddRsEventFailedWhenUserDoesNotExit() throws Exception {
        String jsonValue1 = "{\"eventName\":\"猪肉涨价了\",\"keyWord\":\"经济\",\"userId\": 1 }";
        mockMvc.perform((post("/rs/event")
                .content(jsonValue1)
                .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldGetOneEvent() throws Exception {
        UserEntity user = UserEntity.builder()
                .userName("user0")
                .gender("male")
                .age(19)
                .phone("13579245810")
                .email("a@b.cn")
                .voteNum(10)
                .build();
        userRepository.save(user);
        RsEventEntity rsEvent = RsEventEntity.builder()
                .eventName("event0")
                .keyWord("key")
                .user(user)
                .build();
        rsEventRepository.save(rsEvent);

        mockMvc.perform(get("/rs/{id}", rsEvent.getId()))
                .andExpect(jsonPath("$.eventName", is("event0")))
                .andExpect(jsonPath("$.user.name", is("user0")));

    }

//
//    @DirtiesContext
//    @Test
//    public void should_get_rs_event_list() throws Exception{
//        mockMvc.perform(get("/rs/list"))
//                .andExpect(jsonPath("$", hasSize(3)))
//                .andExpect(jsonPath("$[0].eventName",is("第一条事件")))
//                .andExpect(jsonPath("$[0].keyWord",is("无标签")))
//                .andExpect(jsonPath("$[0]",not(hasKey("user"))))
//                .andExpect(jsonPath("$[1].eventName",is("第二条事件")))
//                .andExpect(jsonPath("$[1].keyWord",is("无标签")))
//                .andExpect(jsonPath("$[1]",not(hasKey("user"))))
//                .andExpect(jsonPath("$[2].eventName",is("第三条事件")))
//                .andExpect(jsonPath("$[2].keyWord",is("无标签")))
//                .andExpect(jsonPath("$[2]",not(hasKey("user"))))
//                .andExpect(status().isOk());
//    }
//
//    @DirtiesContext
//    @Test
//    public void should_get_one_rs_event() throws Exception {
//        mockMvc.perform(get("/rs/1"))
//                .andExpect(jsonPath("$.eventName",is("第一条事件")))
//                .andExpect(jsonPath("$.keyWord",is("无标签")))
//                .andExpect(jsonPath("$",not(hasKey("user"))))
//                .andExpect(status().isOk());
//        mockMvc.perform(get("/rs/2"))
//                .andExpect(jsonPath("$.eventName",is("第二条事件")))
//                .andExpect(jsonPath("$.keyWord",is("无标签")))
//                .andExpect(jsonPath("$",not(hasKey("user"))))
//                .andExpect(status().isOk());
//        mockMvc.perform(get("/rs/3"))
//                .andExpect(jsonPath("$.eventName",is("第三条事件")))
//                .andExpect(jsonPath("$.keyWord",is("无标签")))
//                .andExpect(jsonPath("$",not(hasKey("user"))))
//                .andExpect(status().isOk());
//    }
//
//    @DirtiesContext
//    @Test
//    public void should_get_rs_event_between() throws Exception {
//        mockMvc.perform(get("/rs/list?start=1&end=2"))
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].eventName",is("第一条事件")))
//                .andExpect(jsonPath("$[0].keyWord",is("无标签")))
//                .andExpect(jsonPath("$[0]",not(hasKey("user"))))
//                .andExpect(jsonPath("$[1].eventName",is("第二条事件")))
//                .andExpect(jsonPath("$[1].keyWord",is("无标签")))
//                .andExpect(jsonPath("$[1]",not(hasKey("user"))))
//                .andExpect(status().isOk());
//        mockMvc.perform(get("/rs/list?start=2&end=3"))
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].eventName",is("第二条事件")))
//                .andExpect(jsonPath("$[0].keyWord",is("无标签")))
//                .andExpect(jsonPath("$[0]",not(hasKey("user"))))
//                .andExpect(jsonPath("$[1].eventName",is("第三条事件")))
//                .andExpect(jsonPath("$[1].keyWord",is("无标签")))
//                .andExpect(jsonPath("$[1]",not(hasKey("user"))))
//                .andExpect(status().isOk());
//         mockMvc.perform(get("/rs/list?start=1&end=3"))
//                .andExpect(jsonPath("$", hasSize(3)))
//                .andExpect(jsonPath("$[0].eventName",is("第一条事件")))
//                .andExpect(jsonPath("$[0].keyWord",is("无标签")))
//                .andExpect(jsonPath("$[0]",not(hasKey("user"))))
//                .andExpect(jsonPath("$[1].eventName",is("第二条事件")))
//                .andExpect(jsonPath("$[1].keyWord",is("无标签")))
//                .andExpect(jsonPath("$[1]",not(hasKey("user"))))
//                .andExpect(jsonPath("$[2].eventName",is("第三条事件")))
//                .andExpect(jsonPath("$[2].keyWord",is("无标签")))
//                .andExpect(jsonPath("$[2]",not(hasKey("user"))))
//                .andExpect(status().isOk());
//    }
//
//    @DirtiesContext
//    @Test
//    public void should_add_rs_event() throws Exception {
//        User user =new User("pyq", "female",18,"a@b.com","12345678912");
//        RsEvent rsEvent = new RsEvent("猪肉涨价了","经济",user);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonString = objectMapper.writeValueAsString(rsEvent);
//        mockMvc.perform(post("/rs/event").content(jsonString).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated());
//        mockMvc.perform(get("/rs/list"))
//                .andExpect(jsonPath("$", hasSize(4)))
//                .andExpect(jsonPath("$[0].eventName", is("第一条事件")))
//                .andExpect(jsonPath("$[0].keyWord", is("无标签")))
//                .andExpect(jsonPath("$[0]",not(hasKey("user"))))
//                .andExpect(jsonPath("$[1].eventName", is("第二条事件")))
//                .andExpect(jsonPath("$[1].keyWord", is("无标签")))
//                .andExpect(jsonPath("$[1]",not(hasKey("user"))))
//                .andExpect(jsonPath("$[2].eventName", is("第三条事件")))
//                .andExpect(jsonPath("$[2].keyWord", is("无标签")))
//                .andExpect(jsonPath("$[2]",not(hasKey("user"))))
//                .andExpect(jsonPath("$[3].eventName", is("猪肉涨价了")))
//                .andExpect(jsonPath("$[3].keyWord", is("经济")))
//                .andExpect(jsonPath("$[3]",not(hasKey("user"))))
//                .andExpect(status().isOk());
//    }
//
//    @DirtiesContext
//    @Test
//    public void should_patch_rs_event() throws Exception {
//        User user =new User("pyq", "female",18,"a@b.com","12345678912");
//        RsEvent rsEvent = new RsEvent("第三条事件patch","实时",user);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonString = objectMapper.writeValueAsString(rsEvent);
//        mockMvc.perform(patch("/rs/3").content(jsonString).contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(status().isCreated());
//        mockMvc.perform(get("/rs/list"))
//                .andExpect(jsonPath("$", hasSize(3)))
//                .andExpect(jsonPath("$[0].eventName", is("第一条事件")))
//                .andExpect(jsonPath("$[0].keyWord", is("无标签")))
//                .andExpect(jsonPath("$[0]",not(hasKey("user"))))
//                .andExpect(jsonPath("$[1].eventName", is("第二条事件")))
//                .andExpect(jsonPath("$[1].keyWord", is("无标签")))
//                .andExpect(jsonPath("$[1]",not(hasKey("user"))))
//                .andExpect(jsonPath("$[2].eventName", is("第三条事件patch")))
//                .andExpect(jsonPath("$[2].keyWord", is("实时")))
//                .andExpect(jsonPath("$[2]",not(hasKey("user"))))
//                .andExpect(status().isOk());
//    }
//
//    @DirtiesContext
//    @Test
//    public void should_delete_rs_event() throws Exception {
//        mockMvc.perform(delete("/rs/3")).andExpect(status().isCreated());
//        mockMvc.perform(get("/rs/list"))
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].eventName", is("第一条事件")))
//                .andExpect(jsonPath("$[0].keyWord", is("无标签")))
//                .andExpect(jsonPath("$[0]",not(hasKey("user"))))
//                .andExpect(jsonPath("$[1].eventName", is("第二条事件")))
//                .andExpect(jsonPath("$[1].keyWord", is("无标签")))
//                .andExpect(jsonPath("$[1]",not(hasKey("user"))))
//                .andExpect(status().isOk());
//    }
//
//    @DirtiesContext
//    @Test
//    public void should_add_rs_with_userName_not_exist_event() throws Exception {
//        User user =new User("xiaowang", "female",19,"a@thoughtworks.com","18888888888");
//        RsEvent rsEvent = new RsEvent("添加一条热搜","娱乐",user);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonString = objectMapper.writeValueAsString(rsEvent);
//        mockMvc.perform(post("/rs/event").content(jsonString).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated());
//        mockMvc.perform(get("/rs/list"))
//                .andExpect(jsonPath("$", hasSize(4)))
//                .andExpect(jsonPath("$[0].eventName", is("第一条事件")))
//                .andExpect(jsonPath("$[0].keyWord", is("无标签")))
//                .andExpect(jsonPath("$[0]",not(hasKey("user"))))
//                .andExpect(jsonPath("$[1].eventName", is("第二条事件")))
//                .andExpect(jsonPath("$[1].keyWord", is("无标签")))
//                .andExpect(jsonPath("$[1]",not(hasKey("user"))))
//                .andExpect(jsonPath("$[2].eventName", is("第三条事件")))
//                .andExpect(jsonPath("$[2].keyWord", is("无标签")))
//                .andExpect(jsonPath("$[2]",not(hasKey("user"))))
//                .andExpect(jsonPath("$[3].eventName", is("添加一条热搜")))
//                .andExpect(jsonPath("$[3].keyWord", is("娱乐")))
//                .andExpect(jsonPath("$[3]",not(hasKey("user"))))
//                .andExpect(status().isOk());
//    }
//
//    @DirtiesContext
//    @Test
//    public void eventName_should_not_null() throws Exception {
//        User user =new User("xiaowang", "female",19,"a@thoughtworks.com","18888888888");
//        RsEvent rsEvent = new RsEvent(null,"娱乐",user);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonString = objectMapper.writeValueAsString(rsEvent);
//        mockMvc.perform(post("/rs/list").content(jsonString).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//    }
//
//    @DirtiesContext
//    @Test
//    public void keyWord_should_not_null() throws Exception {
//        User user =new User("xiaowang", "female",19,"a@thoughtworks.com","18888888888");
//        RsEvent rsEvent = new RsEvent("添加一条热搜",null,user);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonString = objectMapper.writeValueAsString(rsEvent);
//        mockMvc.perform(post("/rs/list").content(jsonString).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//    }


}
