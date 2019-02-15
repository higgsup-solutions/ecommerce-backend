# Spring Demo

This project has been belong to [Higgsup Co., Ltd.](https://web.higgsup.com/?lang=en) , it serves for many purpose such as training, demo for clients, etc.

![Higgsup Co., Ltd.](https://web.higgsup.com/mediacenter/media/images/1881/logo/607bb23c-fe17-45f4-ade9-b134381d1c91-1519792738.png)
## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

Anyone whose will join to develop this production must have basic knowledge about Java, Spring Framework

### Technologies Used

JDK 11

Maven 3.6

Spring boot 2.0.5

References:

https://docs.oracle.com/en/java/javase/11/

https://maven.apache.org/guides/index.html

https://maven.apache.org/docs/3.6.0/release-notes.html

https://spring.io/docs

### Installing

A step by step series of examples that tell you how to get a development env running

( You can use some IDE such as Netbean, IntelliJ, Eclipse, Spring Tool Suite but we recommend using IntelliJ )

**Running**

https://www.jetbrains.com/help/idea/creating-and-running-your-first-java-application.html

Making sure your project SDK is JDK 11 (https://www.jetbrains.com/help/idea/sdk.html)

**Debugging**

https://www.jetbrains.com/help/idea/debugging-code.html

**Note**

In this project, we use Lombok to generate getter/setter method for time saving during coding

https://www.baeldung.com/intro-to-project-lombok

For running project, you must install plugin lombok in your IDE

For Lombok plugin installing: https://projectlombok.org/setup/intellij

## Project Structure

In this project, we divided source code into 8 packages.

* **com.higgsup.base.common**

    Defined global enumeration, utilizes, etc. for application  

* **com.higgsup.base.controller**

    Defined controllers base on RESTful for application

* **com.higgsup.base.dto**
    
    Defined Data Transfer Object for controllers.
    
    In this package, we had sub-package **com.higgsup.base.dto.base** to defined interfaces for DTO, all of DTO classes will implement interfaces in this package.  

* **com.higgsup.base.entity**
    
    Defined POJO, which mapping to tables in database 

* **com.higgsup.base.locale**

    Defined utility for internationalization.
    
    References:
    
    https://blog.usejournal.com/spring-boot-rest-internationalization-9ab3fce2489 

* **com.higgsup.base.repository**

    Defined interfaces which manipulate with database, interfaces extended from JpaRepository 
    
    References:
    
    https://spring.io/guides/gs/accessing-data-jpa/
    
    https://dzone.com/articles/spring-boot-jpa-hibernate-oracle

* **com.higgsup.base.security**

    Application security configuration base on JWT standard (JSON Web Token)
    
    References: 
    
    https://www.baeldung.com/spring-security-oauth-jwt
    
    https://github.com/Baeldung/spring-security-oauth
    
    http://svlada.com/jwt-token-authentication-with-spring-boot/
    
    https://github.com/svlada/springboot-security-jwt

* **com.higgsup.base.service**
    
    Defined services to executed business logic 

## Coding Convention
   
Do not use any plural noun for naming.

Just import necessary libraries or packages in class, do not import any libraries or packages like this:`import java.util.*` 

Variables in class should be private variables.


All of utilizes classes should have suffix *Util and all variables and methods must be static const and static method.
  

    Class name: WebUtil.java
        
    Method:
         
    public static boolean isAjax(HttpServletRequest request) {
       return XML_HTTP_REQUEST.equals(request.getHeader(X_REQUESTED_WITH));
    }
        
    Variable:
        
    private static final String CONTENT_TYPE = "Content-type";
   
All of controller classes must have suffix *Controller.
   
   `Example: HelloController.java`

Interfaces name should start with prefix I* except Repository interfaces.
   
   `Example: IPagedResponse.java`

All of DTO class must have suffix *DTO.
    
   `Example: DemoDTO.java`

All of Repository interfaces must have suffix *Repository and extend from JpaRepository interface.

   `Example: UserRepository.java`
   
For package **com.higgsup.base.service**

All interface will follow interface naming rule and implementation classes must put into **com.higgsup.base.service.impl** package with suffix *Service.

## Version Control 

* **Prerequisites**

    Install GIT flow extension `git flow init`

* **Version Control Structure**

    In this project, we had been divided into 2 main branches for development:
    
    * Master: contain all of project's code when deployed on production
    * Develop: contain latest code were merged from other branches during the development progress 

* **Branching**

    We will use GIT flow extension (default option) for Branching 
    
    Creating a feature branch: all feature branches will have prefix feature/* 
        
        git checkout develop
                
        git flow feature start [feature name] OR git checkout -b feature/[feature name]
   
    Creating a bug-fix branch: all feature branches will have prefix bugfix/*
    
        git checkout develop
            
        git flow bugfix start [feature name] OR git checkout -b bugfix/[feature name]
    
* **NOTE**
    
    Do not push any thing to master and develop branch
        
    Always update latest code from develop branch before start new feature or bug fixing to avoid conflict of source code
        
    Creating merge request and assign to technical leader for reviewing code

## Documentation

### Controller Definition
Project was configured to use Springfox implementation of the Swagger specification as a tool for testing/documentation REST API
If this is the first time you get to know about swagger, please refer to their [official website](https://springfox.github.io/springfox/docs/current/#overriding-descriptions-via-properties) for full details reference of list annotation
See HelloController.java for example of usage
```java
@RestController
@RequestMapping("/api")
@Api(value="helloController", description="Hello controller description") //mark this controller with name & description for swagger document
public class HelloController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final IUserService userService;

  public HelloController(IUserService userService) {
    this.userService = userService;
  }

  @GetMapping("/ping")
  @ApiOperation(value = "Description method", response = ResponseEntity.class) // short description, input (if have any) and output of the API for swagger document
  public ResponseEntity ping() {
    return ResponseEntity.ok(Translator.toLocale("hello"));
  }

}
```

### Generate getter,setter & constructor and more
With the aim of reducing time for generating getter/setter & constructors for most of entities (DTOs) we chose lombok plugin to do that work.
If this is the first time you work with that, please spend some minutes to read [here.](https://www.baeldung.com/intro-to-project-lombok)
Also you may want to install **_lombok_** Intellij's plugin to avoid red warnings in your editor screens (you probably don't want to disable all warnings except the ones for missing getter/setter & constructors function declaration in those classes)

###Transaction Management

Spring framework provided annotation `@Transactional` for managing transaction when manipulated with JDBC

We will use this annotation in Service classes

```Java

@Service
@Transactional(readOnly = true) /*This service just can read data from*/ 
public class UserService implements IUserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final UserRoleRepository userRoleRepository;

  public UserService(UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      UserRoleRepository userRoleRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.userRoleRepository = userRoleRepository;
  }

  
  @Override
  @Transactional(rollbackFor = Exception.class) /*mark that all manipulation with database will rollback if Exception throw out*/
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

Following are the possible values for isolation level

| Isolation | Description |
| --- | --- |
| ISOLATION_DEFAULT | This is the default isolation level. |
| ISOLATION_READ_COMMITTED | Indicates that dirty reads are prevented; non-repeatable reads and phantom reads can occur.
| ISOLATION_READ_UNCOMMITTED | Indicates that dirty reads, non-repeatable reads, and phantom reads can occur.
| ISOLATION_REPEATABLE_READ | Indicates that dirty reads and non-repeatable reads are prevented; phantom reads can occur.
| ISOLATION_SERIALIZABLE | Indicates that dirty reads, non-repeatable reads, and phantom reads are prevented.
 

Following are the possible values for propagation types:

| Propagation  | Description |
| --- | --- |
| MANDATORY | Supports a current transaction; throws an exception if no current transaction exists.
| NESTED | Executes within a nested transaction if a current transaction exists.
| NEVER | Does not support a current transaction; throws an exception if a current transaction exists.
| NOT_SUPPORTED | Does not support a current transaction; rather always execute non-transactionally.
| REQUIRED | Supports a current transaction; creates a new one if none exists.
| REQUIRES_NEW | Creates a new transaction, suspending the current transaction if one exists.
| SUPPORTS | Supports a current transaction; executes non-transactionally if none exists.
| TIMEOUT_DEFAULT | Uses the default timeout of the underlying transaction system, or none if timeouts are not supported.
 

```java
//Example use propagation Propagation.REQUIRES_NEW

@Service
@Transactional(readOnly = true)
public class UserService implements IUserService {

  private final IUserRoleService userRoleService;

  public UserService(IUserRoleService userRoleService) {
    this.userRoleService = userRoleService;
  }
  
 @Override
   @Transactional(rollbackFor = Exception.class)
   public void createUserDemoTransaction(UserDTO userDTO) {
     
    //Some code...
    
     userRoleService.create(userRole);
     
   }
   
}

```

```java
//Example use propagation Propagation.REQUIRES_NEW

@Service
@Transactional(readOnly = true)
public class UserRoleService implements IUserRoleService {

 @Override
 @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW) //New transaction
 public void create(UserRole userRole) {
   userRoleRepository.save(userRole);
 }
 
}
```

**References**

https://www.baeldung.com/transaction-configuration-with-jpa-and-spring

https://docs.spring.io/spring/docs/4.2.x/spring-framework-reference/html/transaction.html

https://www.byteslounge.com/tutorials/spring-transaction-propagation-tutorial

https://en.wikipedia.org/wiki/Isolation_(database_systems)

### Logging

In this project, we had been used library Log4j 2  for logging purpose.

* **Configuration**

    Adding dependency in pom.xml
        
        //Exclude default logger of Spring
        <exclusions>
           <exclusion>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-logging</artifactId>
           </exclusion>
        </exclusions>

        <dependency>
	        <groupId>org.springframework.boot</groupId>
	        <artifactId>spring-boot-starter-log4j2</artifactId>
        </dependency>

    Configuration file for Log4j can be one of log4j2.xml, log4j2.json, log4j2.yaml
    
    ```
    Basic configuration
    
    <?xml version="1.0" encoding="UTF-8"?>
    <Configuration status="WARN" monitorInterval="30">
        <Properties>
            <Property name="LOG_PATTERN">
                %d{yyyy-MM-dd HH:mm:ss.SSS} %5p ${hostName} --- [%15.15t] %-40.40c{1.} : %m%n%ex
            </Property>
        </Properties>
        <Appenders>
            <Console name="ConsoleAppender" target="SYSTEM_OUT" follow="true">
                <PatternLayout pattern="${LOG_PATTERN}"/>
            </Console>
        </Appenders>
        <Loggers>
            <Logger name="com.example.log4j2demo" level="debug" additivity="false">
                <AppenderRef ref="ConsoleAppender" />
            </Logger>
    
            <Root level="info">
                <AppenderRef ref="ConsoleAppender" />
            </Root>
        </Loggers>
    </Configuration>
    ```
    
    References:
    
    https://www.callicoder.com/spring-boot-log4j-2-example/
    
* **Using**

    To using logging purpose, we must declare variable in each class like this:
    ```
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    ```

    There are 5 logging types we can use:
    
    ```
    logger.debug("Debugging log"); \* using for debugging purpose *\
    logger.info("Info log"); \* using for info logging *\ 
    logger.warn("Hey, This is a warning!"); \* using for warning purpose *\
    logger.error("Oops! We have an Error. OK"); \* using for error logging *\
    logger.fatal("Damn! Fatal error. Please fix me."); \* using for fatal logging *\
    ```

* **Note**

    Just use logging in Service class (where execute business logic)
    
### Handling Exception
In this project, we used `@ControllerAdvice` as a solution for handling exception

We defined execution logic in package **com.higgsup.base.exception**

We had declared class `BusinessException` to defined exceptions related to business.

In class `GlobalExceptionHandler`, we declared some method to handler exception

```java
@ControllerAdvice
public class GlobalExceptionHandler {
@ExceptionHandler(value = { BusinessException.class }) //Handling exception belong to Business Exception
  public ResponseEntity<IPagedResponse> handleBusinessException(
      HttpServletRequest request, BusinessException exception) {
    // Some code
  }

  @ExceptionHandler(value = { Exception.class }) //Handling exception belong to Runtime Exception
  public ResponseEntity<IPagedResponse> handleException(
      HttpServletRequest request, Exception exception) {
    // Some code
   
  }
}

```
* **Note**

If you want to define another exceptions and handle them, please follow this steps:

First step: Define new class like BusinessException

```java

public class BusinessException extends RuntimeException {
  //Some code
}

```

Second step: Define new method to handle that exception  

```java
@ControllerAdvice
public class GlobalExceptionHandler {
@ExceptionHandler(value = { BusinessException.class }) //Handling exception belong to Business Exception
  public ResponseEntity<IPagedResponse> handleBusinessException(
      HttpServletRequest request, BusinessException exception) {
  
    // Some code
    
  }

}

```

* **References:**
    
https://www.baeldung.com/exception-handling-for-rest-with-spring
https://www.journaldev.com/2651/spring-mvc-exception-handling-controlleradvice-exceptionhandler-handlerexceptionresolver

##Swagger 

###Using

Swagger is an open-source framework help developer can design, build, document and testing API follow RESTful Web Services.

In this project, we has been integrated Swagger for documentation and testing purpose.

For using Swagger-UI, you can access address on your local: `http://localhost:8080/swagger-ui.html`

###Definition

####Controller

For describe controller in Swagger, you can use list of annotations

```@Api(value="helloController", description="Hello controller description") \\ Declare on top of classes to describe group of controllers  ```

```@ApiOperation(value = "View a list of available products",response = Iterable.class) \\ Annotation to describe the endpoint and its response type```

```@ApiResponse(code = 200, message = "Successfully retrieved list") \\ @ApiResponse annotation to document other responses```

```Java
@RestController
@RequestMapping("/product")
@Api(value="onlinestore", description="Operations pertaining to products in Online Store")
public class ProductController {
    private ProductService productService;
    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
    @ApiOperation(value = "View a list of available products",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping(value = "/list", method= RequestMethod.GET, produces = "application/json")
    public Iterable<Product> list(Model model){
        Iterable<Product> productList = productService.listAllProducts();
        return productList;
    }
    @ApiOperation(value = "Search a product with an ID",response = Product.class)
    @RequestMapping(value = "/show/{id}", method= RequestMethod.GET, produces = "application/json")
    public Product showProduct(@PathVariable Integer id, Model model){
       Product product = productService.getProductById(id);
        return product;
    }
}
```

####Model

You can use the `@ApiModelProperty` annotation to describe the properties of model

```Java

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated product ID")
    private Integer id;
    @Version
    @ApiModelProperty(notes = "The auto-generated version of the product")
    private Integer version;
    @ApiModelProperty(notes = "The application-specific product ID")
    private String productId;
    @ApiModelProperty(notes = "The product description")
    private String description;
    @ApiModelProperty(notes = "The image URL of the product")
    private String imageUrl;
    @ApiModelProperty(notes = "The price of the product", required = true)
    private BigDecimal price;
    
}
```

####Authentication

For API authentication with JWT, first thing you must get token by using API `/api/auth/login` in **token-endpoint** API group

After that, we will paste token into api-key field with format: `Bearer [token]` 

![](https://nwkab66374.i.lithium.com/t5/image/serverpage/image-id/1877i1C1397F076105DAF?v=1.0)

**References**: 

https://dzone.com/articles/spring-boot-restful-api-documentation-with-swagger

##Coding guide

See the [Coding guide](CODING.md) file for details

## Deployment

We will add additional notes about how to deploy this at the next time.

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Team members

* **Dung Pham Xuan** - *Project manager*
* **Tu Le Ha** - *Technical leader*
* **Anh Vu Vuong** - *Developer*
* **Thanh Do Trung** - *Developer*

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

