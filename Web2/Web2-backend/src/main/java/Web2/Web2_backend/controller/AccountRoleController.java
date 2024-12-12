package Web2.Web2_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Web2.Web2_backend.dto.AccountRoleDto;
import Web2.Web2_backend.service.AccountRoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;




@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("api/roles")
public class AccountRoleController {
    
    private AccountRoleService accountRoleService;
    
    //build Add role REST API
    @PostMapping
    public ResponseEntity<AccountRoleDto> createAccountRole(@RequestBody AccountRoleDto accountRoleDto) {
        AccountRoleDto savedAccountRole = accountRoleService.createAccountRole(accountRoleDto);     
        return new ResponseEntity<>(savedAccountRole, HttpStatus.CREATED);
    }

    //build Get role by id REST API
    @GetMapping("{id}")
    public ResponseEntity<AccountRoleDto> getUserbyId(@PathVariable("id") Long roleId) {
        AccountRoleDto accountRoleDto = accountRoleService.getAccountRole(roleId);
        return ResponseEntity.ok(accountRoleDto);
    }

    //build Get all roles Rest API
    @GetMapping
    public ResponseEntity<List<AccountRoleDto>> getAllAccountRoles() {
        List<AccountRoleDto> accountRoleDtos = accountRoleService.getAllAccountRoles();
        return ResponseEntity.ok(accountRoleDtos);
    }

    //build Update role rest API
    @PutMapping("{id}")
    public ResponseEntity<AccountRoleDto> updateAccountRole(@PathVariable ("id") Long roleId , @RequestBody AccountRoleDto updatedAccountRole) {
        AccountRoleDto accountRoleDto = accountRoleService.updateAccountRole(roleId, updatedAccountRole);
        return ResponseEntity.ok(accountRoleDto);
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAccountRole(@PathVariable("id") Long roleId) {
        accountRoleService.deleteRole(roleId);
        return ResponseEntity.ok("Role deleted successfully!.");
    }
    
    

}
