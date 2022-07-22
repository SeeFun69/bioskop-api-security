package infosys.teamd.bioskopapisecurity.Service;

import infosys.teamd.bioskopapisecurity.Exception.ResourceNotFoundException;
import infosys.teamd.bioskopapisecurity.Model.Role;
import infosys.teamd.bioskopapisecurity.Model.User;
import infosys.teamd.bioskopapisecurity.Repository.RoleRepository;
import infosys.teamd.bioskopapisecurity.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

;

@Service
@AllArgsConstructor
//@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImplements implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("User not Found");
            throw new UsernameNotFoundException("User not found");
        } else {
            log.info("User found in the database: {}", username);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        }
    }

    @Override
    public User saveUser(User user) {
        log.info("saving new user {} to database", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("saving new role {} to database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("adding role {} to user{}", roleName, username);
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        log.info("fetching user{}", username);
        return userRepository.findByUsername(username);
    }

    /***
     * Get All User
     * @return
     */
    public List<User> getAll(){
        log.info("fetching user{}");
        List<User> user = userRepository.findAll();
        return this.userRepository.findAll();

    }

    /***
     * Get User By ID
     * @param users_Id
     * @return
     */
    public Optional<User> getUserById(Long users_Id){
        Optional<User> optionalUser = userRepository.findById(users_Id);
        if(optionalUser.isEmpty()){
            throw new ResourceNotFoundException("User not exist with id :" + users_Id);
        }
        return this.userRepository.findById(users_Id);
    }

    /***
     * Create User
     * @param user
     * @return
     */
    public User createUser(User user){

        return this.userRepository.save(user);
    }

    /***
     * Delete User
     * @param users_Id
     */
    @Override
    public void deleteUserById(Long users_Id) {
        Optional<User> optionalUser = userRepository.findById(users_Id);
        if(optionalUser.isEmpty()){
            throw new ResourceNotFoundException("User not exist with id :" + users_Id);
        }
        User user = userRepository.getReferenceById(users_Id);
        this.userRepository.delete(user);
    }

    /***
     * Update User
     * @param user
     * @return
     * @throws Exception
     */
    public User updateUser(User user) throws Exception{
        Optional<User> optionalUser = userRepository.findById(user.getUserId());
        if(optionalUser.isEmpty()){
            throw new ResourceNotFoundException("User not exist with id :" + user.getUserId());
        }
        return this.userRepository.save(user);
    }

    @Override
    public User getReferenceById(Long users_Id) {
        Optional <User> optional = userRepository.findById(users_Id);
        User user = null;
        if (optional.isPresent()) {
            user = optional.get();
        } else {
            throw new ResourceNotFoundException("User Not Found!" + users_Id);
        }
        return user;

    }

    @Override
    public User getUserId(Long users_Id) {
        Optional <User> optional = userRepository.findById(users_Id);
        User user = null;
        if (optional.isPresent()) {
            user = optional.get();
        } else {
            throw new ResourceNotFoundException("User Not Found!" + users_Id);
        }
        return user;
    }

    @Override
    public Page<User> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo -1, pageSize);
        return this.userRepository.findAll(pageable);
    }

//    @Override
//    public List<User> getUserByNameLike(String name) {
//        List<User> getUserByNameLike = this.userRepository.getUserNameLike(name);
//        if (getUserByNameLike.isEmpty()){
//            throw new ResourceNotFoundException("Username " + name + " is not exist!");
//        }
//        return this.userRepository.getUserNameLike(name);
//    }

    @Override
    public List<User> getByKeyword(String keyword) {
        List<User> getUserByName = this.userRepository.findByKeyword(keyword);
        if (getUserByName.isEmpty()){
            throw new ResourceNotFoundException("Username " + keyword + " is not exist!");
        }
        return this.userRepository.findByKeyword(keyword);
    }
}