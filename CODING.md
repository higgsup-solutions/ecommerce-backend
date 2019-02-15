#Coding guide
A step by step series of examples that tell you how to coding basically

##Getting started

We will take care mainly in 5 packages to complete a full flow

* com.higgsup.base.dto 
* com.higgsup.base.entity 
* com.higgsup.base.repository
* com.higgsup.base.service
* com.higgsup.base.controller

In this documentation, we will follow managing user flow

###First step

We must defined entity (model) User to mapping with table in database

Entity classes will define in package **com.higgsup.base.entity**

```Java
package com.higgsup.base.entity;
import lombok.Data;

@Entity // mark that this is an entity class
@Table(name="APP_USER") // mapping to table in database
@Data // mark that using Lombok
public class User {
    @Id // mark this is a primary key of table
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.AUTO) // auto generate value when create new 
    private Long id;
    
    @Column(name="username") // mapping with columm in table
    private String username;
    
    @Column(name="password")
    private String password;
    
    @OneToMany // define relationship to other tables (One to many)
    @JoinColumn(name="APP_USER_ID", referencedColumnName="ID")
    private List<UserRole> roles;
    
    public User() { }
    
    public User(Long id, String username, String password, List<UserRole> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
```

###Second step

DTO classes (Data Transfer Object) were defined in **com.higgsup.base.dto**

```Java 
//Purpose of this DTO is map with attributes in request from client
 
package com.higgsup.base.dto;

import lombok.Data;

@Data // mark that using lombok
public class UserDTO {
  private Long id;
  private String userName;
  private String password;
}
```

```Java 
//Purpose of this DTO is map with attributes will be returned from server after business logic execution 

package com.higgsup.base.dto;

import com.higgsup.base.dto.base.IPagedResponse;
import com.higgsup.base.entity.User;

import java.util.List;

public class DemoDTO extends IPagedResponse<List<User>> {

}
```

* **Note**

All response DTO classes will be extended from abstract class `IPagedReponse` in package **com.higgsup.base.dto.base**
###Third step
Define Repository in package **com.higgsup.base.repository**

All files are Interface and extend from JpaRepository<T, ID>

```Java
package com.higgsup.base.repository;

public interface UserRepository extends JpaRepository<User, Long> {

  // Using JPQL for query data
  @Query("select u from User u left join fetch u.roles r where u.username=:username")
  User findByUsername(@Param("username") String username);
    
  // Using native query for query data
  @Query(value = "SELECT * FROM APP_USER WHERE UPPER(username) like 'N%' ", nativeQuery = true)
  List<User> findAllUsernameStartWithN();

  // JPA query
  List<User> findUserById(Long id);
}
```
**References**

https://www.baeldung.com/spring-data-jpa-query

https://spring.io/guides/gs/accessing-data-jpa/

https://dzone.com/articles/spring-boot-jpa-hibernate-oracle

###Fourth step
Define Service to execute business logic

We will define Interface for service in package **com.higgsup.base.service**

```Java
package com.higgsup.base.service;

import com.higgsup.base.dto.UserDTO;
import com.higgsup.base.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
  User getByUsername(String username);

  List<User> getUser();

  void createUser(UserDTO userDTO);
}
```

Service classes will put into sub-package **impl** and implement from corresponding interfaces which had been defined
```Java

package com.higgsup.base.service.impl;

...

@Service
@Transactional(readOnly = true) //Transaction management for all method in service 
public class UserService implements IUserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final UserRoleRepository userRoleRepository;

  // Inject bean throught constructor
  public UserService(UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      UserRoleRepository userRoleRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.userRoleRepository = userRoleRepository;
  }

  @Override
  public User getByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public List<User> getUser() {
    return userRepository.findAll();
  }

  @Override
  @Transactional(rollbackFor = Exception.class) //Transaction management for specify method
  public void createUser(UserDTO userDTO) {
    User user = new User();
    user.setId(userDTO.getId());
    user.setUsername(userDTO.getUserName());
    user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    List<UserRole> roles = new ArrayList<>();
    userRepository.save(user);
    UserRole userRole = new UserRole();
    userRole.setRole(Role.MEMBER);
    userRole.setUserId(user.getId());
    roles.add(userRole);
    userRoleRepository.saveAll(roles);
    throw new BusinessException(ErrorCode.GLOBAL, "Test business exception...");
  }
}
```

###Fifth step
Controller classes must be defined in package **com.higgsup.base.controller**

```Java
package com.higgsup.base.controller;

import ...

@RestController //mark this Restful controller 
@RequestMapping("/api")
@Api(value="helloController", description="Hello controller description") //Swagger define for class
public class HelloController {

  private final IUserService userService;
  
  // Inject bean throught constructor
  public HelloController(IUserService userService) {
    this.userService = userService;
  }

  @GetMapping("/ping")
  @ApiOperation(value = "Description method", response = ResponseEntity.class) //Swagger define for controller
  public ResponseEntity ping() {
    return ResponseEntity.ok(Translator.toLocale("hello"));
  }

  @PostMapping("/create")
  public ResponseEntity<UserDTO> create(
      @RequestBody UserDTO userDTO) {
    userService.createUser(userDTO);
    return ResponseEntity.ok(userDTO);
  }

  @GetMapping("/user")
  @RequestLogger // mark controller will be logged (using when needed), if we use this annotation, we must declare HttpServletRequest as parameter in method 
  public DemoDTO getUser(HttpServletRequest request) {
    DemoDTO demoDTO = new DemoDTO();
    ResponseMessage<List<User>> responseMessage = new ResponseMessage<>();
    responseMessage.setData(userService.getUser());
    responseMessage.setStatus(HttpStatus.OK.getReasonPhrase());
    demoDTO.setResponseMessage(responseMessage);
    return demoDTO;
  }

  @GetMapping("/exception")
  @RequestLogger
  public ResponseEntity getException(HttpServletRequest request) {
    throw new BusinessException(ErrorCode.GLOBAL, "RuntimeException...");
  }

}

```