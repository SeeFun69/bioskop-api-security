package infosys.teamd.bioskopapisecurity.Service;

import infosys.teamd.bioskopapisecurity.Model.*;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface UserService {

    User createUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);




    List<User> getAll();
    Optional<User> getUserById(Long users_Id);
    void deleteUserById(Long users_Id, Principal principal,  User users);
    User updateUser(User user) throws Exception;
    User getReferenceById(Long users_Id);
    User getUserId(Long users_Id);
    Page<User> findPaginated(int pageNo, int pageSize);
//    List<User> getUserByNameLike(String name);
    List<User> getByKeyword(String keyword);
}
