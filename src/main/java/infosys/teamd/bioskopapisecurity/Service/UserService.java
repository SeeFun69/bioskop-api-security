package infosys.teamd.bioskopapisecurity.Service;

import infosys.teamd.bioskopapisecurity.Model.*;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAll();
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);



    User createUser(User user);
    Optional<User> getUserById(Long users_Id);
    void deleteUserById(Long users_Id);
    User updateUser(User user) throws Exception;
    User getReferenceById(Long users_Id);
    User getUserId(Long users_Id);
    Page<User> findPaginated(int pageNo, int pageSize);
//    List<User> getUserByNameLike(String name);
    List<User> getByKeyword(String keyword);
}
