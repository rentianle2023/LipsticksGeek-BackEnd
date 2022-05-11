package fun.tianlefirstweb.www.user.role;

import fun.tianlefirstweb.www.exception.EntityNotExistException;
import fun.tianlefirstweb.www.user.enums.Role;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final ApplicationRoleRepository roleRepository;

    public RoleService(ApplicationRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public ApplicationRole findByRole(Role role){
        return roleRepository.findByRole(role)
                .orElseThrow(() -> new EntityNotExistException(String.format("名称为%s的role不存在",role.name())));
    }
}
