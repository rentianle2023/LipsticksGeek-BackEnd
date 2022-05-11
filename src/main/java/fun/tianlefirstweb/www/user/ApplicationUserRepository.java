package fun.tianlefirstweb.www.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ApplicationUserRepository extends CrudRepository<ApplicationUser, Integer> {

    Optional<ApplicationUser> findByUsername(String username);

    Optional<ApplicationUser> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
