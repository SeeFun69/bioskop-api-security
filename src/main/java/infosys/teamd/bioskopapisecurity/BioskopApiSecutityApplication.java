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

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.saveRole(new Role(null,"ROLE_USER"));
            userService.saveRole(new Role(null,"ROLE_MANAGER"));
            userService.saveRole(new Role(null,"ROLE_ADMIN"));
            userService.saveRole(new Role(null,"ROLE_SUPER_ADMIN"));

            userService.createUser(new User(null,"BayuAswin","Bayu",new ArrayList<>(),"Bayu@gmail.com","1234"));
            userService.createUser(new User(null,"MRasyid Hidayat","Rasyid",new ArrayList<>(),"Rasyid@gmail.com","2345"));
            userService.createUser(new User(null,"Yohanes Crusz","Crusz",new ArrayList<>(),"Crusz@gmail.com","3456"));
            userService.createUser(new User(null,"Rendra Prames","Prames",new ArrayList<>(),"Prames@gmail.com","4567"));

            userService.addRoleToUser("BayuAswin","ROLE_USER");
            userService.addRoleToUser("MRasyid Hidayat","ROLE_MANAGER");
            userService.addRoleToUser("Yohanes Crusz","ROLE_ADMIN");
            userService.addRoleToUser("Rendra Prames","ROLE_SUPER_ADMIN");
        };
    }
}
