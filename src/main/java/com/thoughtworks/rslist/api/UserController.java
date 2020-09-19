package com.thoughtworks.rslist.api;


import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.entity.UserEntity;
import org.springframework.http.ResponseEntity;
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

    UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
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
                .name(user.getName())
                .email(user.getEmail())
                .age(user.getAge())
                .gender(user.getGender())
                .phone(user.getPhone())
                .build();
        userRepository.save(userEntity);
    }

//    @GetMapping("/user/{index}")
//    public ResponseEntity search(@Valid @PathVariable Integer index) {
//
//        User user=UserEntity.builder
//    }
}
