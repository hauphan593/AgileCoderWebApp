package Web2.Web2_backend.mapper;


import Web2.Web2_backend.dto.UserDto;
import Web2.Web2_backend.entity.AccountRole;
import Web2.Web2_backend.entity.User;
import Web2.Web2_backend.exception.ResourceNotFoundException;
import Web2.Web2_backend.repository.AccountRoleRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserMapper {
        
        public static UserDto mapToUserDto(User user) {
            return new UserDto (
                user.getUserid(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getAccountRole() != null ? user.getAccountRole().getRoleId():null ,
                user.getCreatedAt(),
                user.getCreatedBy(),
                user.getUpdatedAt(),
                user.getUpdatedBy()
            );
        }
    
        public static User mapToUser(UserDto userDto, AccountRoleRepository accountRoleRepository) {
            
            AccountRole accountRole = null;
            if(userDto.getRoleId() != null) {
                accountRole = accountRoleRepository.findById(userDto.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role is not exists with given id:" + userDto.getRoleId()));

        }
        return new User (
            userDto.getUserId(),
            userDto.getUsername(),
            userDto.getPassword(),
            userDto.getEmail(),
            accountRole,
            userDto.getCreatedAt(),
            userDto.getCreatedBy(),
            userDto.getUpdatedAt(),
            userDto.getUpdatedBy()
        );
    }
}
