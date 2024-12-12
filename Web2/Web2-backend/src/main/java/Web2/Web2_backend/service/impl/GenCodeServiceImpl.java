package Web2.Web2_backend.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

import Web2.Web2_backend.dto.GenCodeDto;
import Web2.Web2_backend.entity.GenCode;
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGenCodebyId'");
    }

    @Override
    public List<GenCodeDto> getAllGenCode() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllGenCode'");
    }

    @Override
    public GenCodeDto updateGenCode(Long projectId, GenCodeDto updatedGenCode) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateGenCode'");
    }

    @Override
    public void deleteGenCode(Long projectId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteGenCode'");
    }

}
