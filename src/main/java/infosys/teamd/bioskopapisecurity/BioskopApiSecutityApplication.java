package infosys.teamd.bioskopapisecurity;

import infosys.teamd.bioskopapisecurity.Model.Role;
import infosys.teamd.bioskopapisecurity.Model.User;
import infosys.teamd.bioskopapisecurity.Service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class BioskopApiSecutityApplication {

    public static void main(String[] args) {
        SpringApplication.run(BioskopApiSecutityApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


//    @Bean
//    CommandLineRunner run(UserService userService){
//        return args -> {
//            userService.saveRole(new Role(null, "ROLE_USER"));
//            userService.saveRole(new Role(null, "ROLE_MANAGER"));
//            userService.saveRole(new Role(null, "ROLE_ADMIN"));
//            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

//            userService.createUser(new User(null, "rasyid hidayat", "rasyid", "rasyid@gmail.com", "1234", new ArrayList<>()));
//            userService.createUser(new User(null, "rasyid bagus", "ragu", "ragu@gmail.com", "1234", new ArrayList<>()));
//            userService.createUser(new User(null, "bagus hidayat", "bagus", "bagus@gmail.com", "1234", new ArrayList<>()));
//            userService.createUser(new User(null, "rasyid will", "will", "will@gmail.com", "1234", new ArrayList<>()));
//
//            userService.addRoleToUser("rasyid", "ROLE_USER");
//            userService.addRoleToUser("bagus", "ROLE_MANAGER");
//            userService.addRoleToUser("rasyid", "ROLE_USER");
//            userService.addRoleToUser("will", "ROLE_ADMIN");
//            userService.addRoleToUser("ragu", "ROLE_SUPER_ADMIN");
//            userService.addRoleToUser("rasyid", "ROLE_ADMIN");
//        };
//    }

}
