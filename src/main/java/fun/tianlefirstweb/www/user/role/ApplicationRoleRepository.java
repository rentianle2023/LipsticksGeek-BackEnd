package fun.tianlefirstweb.www.user.role;

import fun.tianlefirstweb.www.user.enums.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ApplicationRoleRepository extends CrudRepository<ApplicationRole,Integer> {

    @Query(
            value = "SELECT * FROM role r WHERE r.name = :name",
            nativeQuery = true)
    Optional<ApplicationRole> findByName(@Param("name") String name);

    Optional<ApplicationRole> findByRole(Role role);

}
