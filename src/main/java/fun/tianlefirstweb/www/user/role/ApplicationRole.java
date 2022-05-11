package fun.tianlefirstweb.www.user.role;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fun.tianlefirstweb.www.user.ApplicationUser;
import fun.tianlefirstweb.www.user.enums.Role;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.EnumType.STRING;

@Entity
@Data
@Table(name = "role")
public class ApplicationRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @Enumerated(STRING)
    private Role role;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonBackReference
    private List<ApplicationUser> users;
}
