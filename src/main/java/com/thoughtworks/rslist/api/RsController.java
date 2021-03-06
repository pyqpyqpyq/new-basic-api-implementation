package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.exception.RequestNotValidException;
import com.thoughtworks.rslist.exception.wrongInfomationException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
public class RsController {

    //  @Autowired
//  UserRepository userRepository;
    private final UserRepository userRepository;
    //  @Autowired
//  RsEventRepository rsEventRepository;
    private final RsEventRepository rsEventRepository;

    private List<RsEvent> rsList = initRsEventList();

    public RsController(UserRepository userRepository, RsEventRepository rsEventRepository) {
        this.userRepository = userRepository;
        this.rsEventRepository = rsEventRepository;
    }

    private static List<RsEvent> initRsEventList() {
        return new ArrayList<>();
    }

//    @PostMapping("/rs/event")
//    public ResponseEntity addRsEvent(@RequestBody @Valid RsEvent rsEvent) {
//        RsEventEntity entity=RsEventEntity.builder()
//                .eventName(rsEvent.getEventName())
//                .keyWord(rsEvent.getKeyWord())
//                .userId(rsEvent.getUserId())
//                .build();
//        rsEventRepository.save(entity);
//        return ResponseEntity.created(null).build();
//
//    }

    @PostMapping("/rs/event")
    public ResponseEntity addRsEvent(@RequestBody @Valid RsEvent rsEvent) {
        if (!userRepository.existsById(rsEvent.getUserId())) {
            return ResponseEntity.badRequest().build();
        }
        RsEventEntity entity = RsEventEntity.builder()
                .eventName(rsEvent.getEventName())
                .keyWord(rsEvent.getKeyWord())
                .user(UserEntity.builder()
                        .id(rsEvent.getUserId())
                        .build())
                .build();
        rsEventRepository.save(entity);
        return ResponseEntity.created(null).build();
    }

    @GetMapping("/rs/{id}")
    public ResponseEntity getOneRsEvent(@PathVariable int id) throws Exception {
        Optional<RsEventEntity> result = rsEventRepository.findById(id);
        if (!result.isPresent()) {
            throw new RequestNotValidException("invalid id");
        }
        RsEventEntity rsEvent = result.get();
        UserEntity user = rsEvent.getUser();

        return ResponseEntity.ok(RsEvent.builder()
                .eventName(rsEvent.getEventName())
                .keyWord(rsEvent.getKeyWord())
                .user(new User(user.getUserName(),
                        user.getGender(),
                        user.getAge(),
                        user.getEmail(),
                        user.getPhone()))
                .build());
    }

      @PatchMapping("/rs/{index}")
  public ResponseEntity patchRsEvent (@RequestBody RsEvent rsEvent, @PathVariable int index) throws wrongInfomationException {
    if (rsEvent.getUserId()!=rsEventRepository.findById(index).get().getUser().getId()){
        throw new wrongInfomationException();
    }
          RsEventEntity rsEventEntity = RsEventEntity.from(rsEvent);
        RsEventEntity oldRsEventEntity = rsEventRepository.findById(rsEventEntity.getId()).get();
          RsEventEntity change=oldRsEventEntity.from(rsEvent);
          RsEventEntity newRsEventEntity=rsEventEntity.merge(change);
          rsEventRepository.save(newRsEventEntity);
          return ResponseEntity.created(null).build();

  }

//  private List<RsEvent> rsList = initRsEventList();
//  private List<RsEvent> initRsEventList() {
//    List<RsEvent> rsEventList = new ArrayList<>();
//    User user =new User("pyq", "female",18,"a@b.com","12345678912");
//    rsEventList.add(new RsEvent("第一条事件","无标签",user));
//    rsEventList.add(new RsEvent("第二条事件","无标签",user));
//    rsEventList.add(new RsEvent("第三条事件","无标签",user));
//    return rsEventList;
//  }

//  @GetMapping("/rs/{index}")
//  public ResponseEntity getOneRsEvent(@PathVariable int index) throws Exception {
//    if(index<1 || index > rsList.size()){
//      throw new IndexOutOfBoundsException();
//    }
//    return ResponseEntity.ok(rsList.get(index - 1));
//  }
//
//  @GetMapping("/rs/list")
//  public ResponseEntity getOneRsEvent(@RequestParam (required = false) Integer start, @RequestParam (required = false)  Integer end){
//    if (start == null || end == null) {
//      return ResponseEntity.ok(rsList);
//    }
//    return ResponseEntity.ok(rsList.subList(start - 1, end));
//  }
//
//  @PostMapping("/rs/event")
//  public ResponseEntity addRsEvent(@RequestBody @Validated String rsEvent) throws JsonProcessingException {
//    ObjectMapper objectMapper = new ObjectMapper();
//    RsEvent event = objectMapper.readValue(rsEvent, RsEvent.class);
//    rsList.add(event);
//    return ResponseEntity.created(null).build();
//  }
//
//  @PostMapping("/rs/list")
//  public ResponseEntity addRsEvent(@RequestBody @Validated  RsEvent rsEvent) throws JsonProcessingException {
//    rsList.add(rsEvent);
//    return ResponseEntity.created(null).build();
//
//  }
//
//  @PatchMapping("/rs/{index}")
//  public ResponseEntity patchRsEvent (@RequestBody RsEvent rsEvent, @PathVariable int index)  {
//    if(index < 1 || index > rsList.size()){
//      throw new IndexOutOfBoundsException();
//    }
//     rsList.set(index-1,rsEvent);
//     return ResponseEntity.created(null).build();
//  }
//
//  @DeleteMapping("/rs/{index}")
//  public ResponseEntity deleteRsEvent(@PathVariable int index)  {
//    if(index < 1 || index > rsList.size()){
//      throw new IndexOutOfBoundsException();
//    }
//    rsList.remove(index-1);
//    return ResponseEntity.created(null).build();
//  }

    }
