package Web2.Web2_backend.service;

import java.util.List;

import Web2.Web2_backend.dto.AccountRoleDto;

public interface AccountRoleService {
    AccountRoleDto createAccountRole(AccountRoleDto accountRole);

    AccountRoleDto getAccountRole(Long roleId);

    List<AccountRoleDto> getAllAccountRoles();

    AccountRoleDto updateAccountRole(Long roleId, AccountRoleDto updatedRole);

    void deleteRole(Long roleId);

}
