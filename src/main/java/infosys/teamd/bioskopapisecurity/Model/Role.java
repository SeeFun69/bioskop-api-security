package infosys.teamd.bioskopapisecurity.Model;


import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role_tbl")
public class Role {
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_name")
    private  String name;

      @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", name='" + name + '\'' +
                '}';
    }
}
