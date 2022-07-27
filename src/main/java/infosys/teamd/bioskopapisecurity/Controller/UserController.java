package infosys.teamd.bioskopapisecurity.Controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import infosys.teamd.bioskopapisecurity.Model.*;
import infosys.teamd.bioskopapisecurity.Exception.*;
import infosys.teamd.bioskopapisecurity.Response.*;
import infosys.teamd.bioskopapisecurity.Service.*;
import infosys.teamd.bioskopapisecurity.Repository.*;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/***
 * Edited By Rendra
 */
@RestController
@RequestMapping("teamD/v1")
@AllArgsConstructor
@SecurityRequirement(name = "bearer-key")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);
//    private final UserServiceImplements userServiceImplements;
    private final UserRepository userRepository;
    private final UserService userService;

//    @PostMapping("/login")
//    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginRequest loginRequest){
//        return authServiceImpl.authenticateUser(loginRequest);
//    }

    @PostMapping("/user/save")
    public ResponseEntity<User>createUser(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/teamD/v1/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.createUser(user));
    }
    @PostMapping("/role/save")
    public ResponseEntity<Role>saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("teamD/v1/role/save").toUriString());
        return ResponseEntity.ok().body(userService.saveRole(role));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<?>addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser (form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Missing Refresh Token");
        }
    }

    /***
     * Get All Users, Logger And Response DONE
     * @return
     */
    @GetMapping("/users")
    public ResponseEntity <Object> getAllUser() {
        try {
            List <User> result = userService.getAll();
            List<Map<String, Object>> maps = new ArrayList<>();
            logger.info("==================== Logger Start Get All Users     ====================");
            for(User userData : result){
                Map<String, Object> user = new HashMap<>();

                logger.info("-------------------------");
                logger.info("ID       : " + userData.getUserId());
                logger.info("Username : " + userData.getUsername());
                logger.info("Email    : " + userData.getEmail());
                logger.info("Password : " + userData.getPassword());

                user.put("ID            ", userData.getUserId());
                user.put("Username      ", userData.getUsername());
                user.put("Email         ", userData.getEmail());
                user.put("Password      ", userData.getPassword());
                maps.add(user);
            }
            logger.info("==================== Logger End Get All Users     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("Successfully Get All User!", HttpStatus.OK, result);
        } catch (Exception e) {
            logger.info("==================== Logger Start Get All Users     ====================");
            logger.error(ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Table has no value"));
            logger.info("==================== Logger End Get All Users     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, "Table Has No Value!");
        }
    }

    /***
     * Get User By Id, Logger and Response DONE
     * @param users_Id
     * @return
     */
    @GetMapping("/users/{users_Id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long users_Id) {
        try {
//            User userResult = userRepository.getReferenceById(users_Id);
            User userResult = userService.getUserById(users_Id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not exist with user_Id :" + users_Id));
            Map<String, Object> user = new HashMap<>();
            List<Map<String, Object>> maps = new ArrayList<>();

            logger.info("==================== Logger Start Find By ID Users ====================");
            logger.info("ID       : " + userResult.getUserId());
            logger.info("Username : " + userResult.getUsername());
            logger.info("Email    : " + userResult.getEmail());
            logger.info("Password : " + userResult.getPassword());

            user.put("ID             ", userResult.getUserId());
            user.put("Username       ", userResult.getUsername());
            user.put("Email          ", userResult.getEmail());
            user.put("Password       ", userResult.getPassword());
            maps.add(user);

            logger.info("==================== Logger End Find By ID Users   ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("Successfully Get User By ID!", HttpStatus.OK, maps);
        } catch (Exception e) {
            logger.info("==================== Logger Start Get By ID Users     ====================");
            logger.error(ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data Not Found!"));
            logger.info("==================== Logger End Get By ID Users     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data Not Found!" );
        }

    }

    /***
     * Create User, Logger and Response DONE
     * @param user
     * @return
     */
//    @PostMapping("/users")
//    public ResponseEntity <Object> createUser(@RequestBody User user) {
//
//        try {
//            userService.createUser(user);
//            User userResult = userService.createUser(user);
//            Map<String, Object> userMap = new HashMap<>();
//            List<Map<String, Object>> maps = new ArrayList<>();
//
//            logger.info("==================== Logger Start Create Users ====================");
//            logger.info("User Successfully Created !");
//            logger.info("ID       : " + userResult.getUserId());
//            logger.info("Username : " + userResult.getUsername());
//            logger.info("Email    : " + userResult.getEmail());
//            logger.info("Password : " + userResult.getPassword());
//
//            userMap.put("ID             ", userResult.getUserId());
//            userMap.put("Username       ", userResult.getUsername());
//            userMap.put("Email          ", userResult.getEmail());
//            userMap.put("Password       ", userResult.getPassword());
//            maps.add(userMap);
//            logger.info("==================== Logger End Create Users   ====================");
//            logger.info(" ");
//            return ResponseHandler.generateResponse("Successfully Created User!", HttpStatus.CREATED, maps);
//        } catch (Exception e) {
//            logger.info("==================== Logger Start Create Users     ====================");
//            logger.error(ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,"User Already Exist!"));
//            logger.info("==================== Logger End Create Users     ====================");
//            logger.info(" ");
//            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "User Already Exist!");
//        }
//
//    }

    /***
     * Update User, Logger and Response DONE
     * @param users_Id
     * @param userDetails
     * @return
     */
    @PutMapping("/users/{users_Id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long users_Id, @RequestBody User userDetails){
        try {
            User user = userService.getUserById(users_Id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not exist with user_Id :" + users_Id));

            user.setName(userDetails.getName());
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            User updatedUser = userRepository.save(user);

            logger.info("==================== Logger Start Update Users ====================");
            logger.info("User Data Successfully Updated !");
            logger.info("ID       : " + user.getUserId());
            logger.info("Username : " + user.getUsername());
            logger.info("Email    : " + user.getEmail());
            logger.info("Password : " + user.getPassword());
            logger.info("==================== Logger End Update Users   ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("Successfully Updated User!",HttpStatus.OK, user);
        }catch(Exception e){
            logger.info("==================== Logger Start Update Users     ====================");
            logger.error(ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data Not Found!"));
            logger.info("==================== Logger End Update Users     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data Not Found!");
        }

    }

    /***
     * Delete User,Logger and Response DONE
     * @param users_Id
     * @return
     */
    @DeleteMapping("/users/{users_Id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long users_Id, Principal principal, User users){
        try {
            userService.deleteUserById(users_Id,  principal, users);
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            logger.info("==================== Logger Start Delete Users ====================");
            logger.info("User Data Successfully Deleted! :" + response.put("deleted", Boolean.TRUE));
            logger.info("==================== Logger End Delete Users   ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("Successfully Delete User! ", HttpStatus.OK, response);
        } catch (ResourceNotFoundException e){
            logger.info("==================== Logger Start Delete Users     ====================");
            logger.error(ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data Not Found!"));
            logger.info("==================== Logger End Delete Users     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data Not Found!" );
        }
    }
}
@Data
class RoleToUserForm{
    private String username;
    private String roleName;
}