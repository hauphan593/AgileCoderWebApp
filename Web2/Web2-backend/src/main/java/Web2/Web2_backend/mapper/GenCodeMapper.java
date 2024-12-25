package Web2.Web2_backend.mapper;

import Web2.Web2_backend.dto.GenCodeDto;
import Web2.Web2_backend.entity.GenCode;
import Web2.Web2_backend.entity.User;
import Web2.Web2_backend.exception.ResourceNotFoundException;
import Web2.Web2_backend.repository.UserRepository;

public class GenCodeMapper {

    public static GenCodeDto mapToGenCodeDto(GenCode genCode) {
        return new GenCodeDto(
            genCode.getProjectId(),
            genCode.getCompanyName(),
            genCode.getProjectName(),
            genCode.getProjectRequire(),
            genCode.getFileData(),
            genCode.getUser() != null ? genCode.getUser().getUserid():null,
            genCode.getCreatedAt(),
            genCode.getCreatedBy(),
            genCode.getUpdatedAt(),
            genCode.getUpdatedBy()
        );
    }

    public static GenCode mapToGenCode(GenCodeDto genCodeDto, UserRepository userRepository) {
        User user = null;
        if(genCodeDto.getUserid() != null) {
            user = userRepository.findById(genCodeDto.getUserid())
            .orElseThrow(() -> new ResourceNotFoundException("User does not exist with the given ID:" + genCodeDto.getUserid()));
        }
        return new GenCode(
            genCodeDto.getProjectId(),
            genCodeDto.getCompanyName(),
            genCodeDto.getProjectName(),
            genCodeDto.getProjectRequire(),
            genCodeDto.getFileData(),
            user,
            genCodeDto.getCreatedAt(),
            genCodeDto.getCreatedBy(),
            genCodeDto.getUpdatedAt(),
            genCodeDto.getUpdatedBy()
        );
    }

}
