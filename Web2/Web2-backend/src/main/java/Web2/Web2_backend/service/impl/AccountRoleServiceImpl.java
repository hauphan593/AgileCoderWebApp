package Web2.Web2_backend.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import Web2.Web2_backend.dto.AccountRoleDto;
import Web2.Web2_backend.entity.AccountRole;
import Web2.Web2_backend.mapper.AccountRoleMapper;
import Web2.Web2_backend.repository.AccountRoleRepository;
import Web2.Web2_backend.service.AccountRoleService;
import Web2.Web2_backend.exception.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountRoleServiceImpl implements AccountRoleService {

    private AccountRoleRepository accountRoleRepository;

    @Override
    public AccountRoleDto createAccountRole(AccountRoleDto accountRoleDto) {
        AccountRole accountRole = AccountRoleMapper.mapToAccountRole(accountRoleDto);

        accountRole.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        accountRole.setCreatedBy("admin");
        accountRole.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        accountRole.setUpdatedBy("admin");

        AccountRole savedAccountRole = accountRoleRepository.save(accountRole);
        return AccountRoleMapper.mapToAccountRoleDto(savedAccountRole);
    }

    @Override
    public AccountRoleDto getAccountRole(Long roleId) {
        AccountRole accountRole = accountRoleRepository.findById(roleId)
                    .orElseThrow(() -> new 
                    ResourceNotFoundException("Role not exists with given id:" + roleId  ));
        
        return AccountRoleMapper.mapToAccountRoleDto(accountRole);
    }

    @Override
    public List<AccountRoleDto> getAllAccountRoles() {
        List<AccountRole> accountRoles = accountRoleRepository.findAll();
        return accountRoles.stream().map((accountRole)
         -> AccountRoleMapper.mapToAccountRoleDto(accountRole))
         .collect(Collectors.toList());
    }

    @Override
    public AccountRoleDto updateAccountRole(Long roleId, AccountRoleDto updatedRole) {
        AccountRole accountRole = accountRoleRepository.findById(roleId)
                    .orElseThrow(() -> new ResourceNotFoundException("Role not exists with given id:" + roleId ));
        accountRole.setRoleName(updatedRole.getRoleName());
        accountRole.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        accountRole.setUpdatedBy("admin");
        AccountRole updatedAccountRole = accountRoleRepository.save(accountRole);

        return AccountRoleMapper.mapToAccountRoleDto(updatedAccountRole);
    }

    @Override
    public void deleteRole(Long roleId) {
        AccountRole accountRole = accountRoleRepository.findById(roleId)
                                    .orElseThrow(() -> new ResourceNotFoundException("Role not exists with given id:" + roleId ));
        accountRoleRepository.deleteById(roleId);
    }
    
}
