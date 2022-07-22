package infosys.teamd.bioskopapisecurity.Repository;

import infosys.teamd.bioskopapisecurity.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
