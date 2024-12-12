package Web2.Web2_backend.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import Web2.Web2_backend.dto.GenCodeDto;
import Web2.Web2_backend.entity.GenCode;
import Web2.Web2_backend.exception.ResourceNotFoundException;
import Web2.Web2_backend.mapper.GenCodeMapper;
import Web2.Web2_backend.repository.GenCodeRepository;
import Web2.Web2_backend.repository.UserRepository;
import Web2.Web2_backend.service.GenCodeService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GenCodeServiceImpl implements GenCodeService {

    private GenCodeRepository genCodeRepository;
    private UserRepository userRepository;
    @Override
    public GenCodeDto createGenCode(GenCodeDto genCodeDto) {
        //validate zip file
        if(genCodeDto.getFileData() ==null)
        {
            // String mimeType;
            // try {
            //     mimeType = URLConnection.guessContentTypeFromStream(
            //         new ByteArrayInputStream(genCodeDto.getFileData()) );
            //     if ( !"application/x-zip-compressed".equals(mimeType) ) {
            //         throw new IllegalArgumentException("Invalid file format. Only ZIP files are allowed.");
            //     }
            // } catch (IOException e) {
            //     e.printStackTrace();
            // }
            throw new IllegalArgumentException("Invalid file format. Only ZIP files are allowed.");

            
        }   
        GenCode genCode = GenCodeMapper.mapToGenCode(genCodeDto, userRepository);

        genCode.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        genCode.setCreatedBy("admin");
        genCode.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        genCode.setUpdatedBy("admin");

        GenCode savedGenCode = genCodeRepository.save(genCode);
        return GenCodeMapper.mapToGenCodeDto(savedGenCode);
    }

    @Override
    public GenCodeDto getGenCodebyId(Long projectId) {
        GenCode genCode = genCodeRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project is not exists with given id: " + projectId));
        return GenCodeMapper.mapToGenCodeDto(genCode);
    }

    @Override
    public List<GenCodeDto> getAllGenCode() {
        List<GenCode> genCodes = genCodeRepository.findAll();
        return genCodes.stream().map((genCode) -> GenCodeMapper.mapToGenCodeDto(genCode))
                .collect(Collectors.toList());
    }

    @Override
    public GenCodeDto updateGenCode(Long projectId, GenCodeDto updatedGenCode) {
        GenCode genCode = genCodeRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project is not exists with given id: " + projectId));
        //update data
        genCode.setProjectName(updatedGenCode.getProjectName());

        //update times and author
        genCode.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        genCode.setUpdatedBy("admin");

        GenCode updatedGenCode2 = genCodeRepository.save(genCode);
        return GenCodeMapper.mapToGenCodeDto(updatedGenCode2);
    }

    @Override
    public void deleteGenCode(Long projectId) {
        GenCode genCode = genCodeRepository.findById(projectId)
                            .orElseThrow(() -> new ResourceNotFoundException("Project is not exists with given id: " + projectId));
        genCodeRepository.deleteById(projectId);
    }

}
