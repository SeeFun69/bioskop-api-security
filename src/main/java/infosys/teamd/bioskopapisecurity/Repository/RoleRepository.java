package infosys.teamd.bioskopapisecurity.Repository;

import infosys.teamd.bioskopapisecurity.Model.Role;
import infosys.teamd.bioskopapisecurity.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

   Role findByName(String name);
}
