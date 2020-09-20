package com.thoughtworks.rslist.api;


import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.entity.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
class UserController {
    List<User> userList = new ArrayList<>();
    private final UserRepository userRepository;
    //    @Autowired
//    UserRepository userRepository;
    private final RsEventRepository rsEventRepository;


    UserController(UserRepository userRepository, RsEventRepository rsEventRepository) {
        this.userRepository = userRepository;
        this.rsEventRepository = rsEventRepository;
    }

//   @PostMapping("/user")
//   public  void addUser(@RequestBody  @Valid User user){
//       userList.add(user);
//   }

//   @GetMapping("/user")
//   public List<User> getUserList() {
//       return userList;
//    }

    @PostMapping("/user")
    public void register(@RequestBody @Valid User user) {

        UserEntity userEntity = UserEntity.builder()
                .userName(user.getName())
                .email(user.getEmail())
                .age(user.getAge())
                .gender(user.getGender())
                .phone(user.getPhone())
                .build();
        userRepository.save(userEntity);
    }
    @Transactional
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/user/{id}")
    public void delete(@PathVariable int id) {
        userRepository.deleteById(id);
    }
//    @GetMapping("/user/{index}")
//    public ResponseEntity search(@Valid @PathVariable Integer index) {
//
//        User user=UserEntity.builder
//    }
}
