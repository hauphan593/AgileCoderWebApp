package Web2.Web2_backend.mapper;

import Web2.Web2_backend.dto.AccountRoleDto;
import Web2.Web2_backend.entity.AccountRole;

public class AccountRoleMapper {

    public static AccountRoleDto mapToAccountRoleDto(AccountRole accountRole) {
        return new AccountRoleDto (
            accountRole.getRoleId(),
            accountRole.getRoleName(),
            accountRole.getCreatedAt(),
            accountRole.getCreatedBy(), 
            accountRole.getUpdatedAt(), 
            accountRole.getUpdatedBy());
    }

    public static AccountRole mapToAccountRole(AccountRoleDto accountRoleDto) {
        return new AccountRole (
            accountRoleDto.getRoleId(),
            accountRoleDto.getRoleName(),
            accountRoleDto.getCreatedAt(),
            accountRoleDto.getCreatedBy(), 
            accountRoleDto.getUpdatedAt(), 
            accountRoleDto.getUpdatedBy());
    }

}
