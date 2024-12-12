package Web2.Web2_backend.service;

import java.util.List;

import Web2.Web2_backend.dto.GenCodeDto;

public interface GenCodeService {
    GenCodeDto createGenCode(GenCodeDto genCodeDto);

    GenCodeDto getGenCodebyId(Long projectId);

    List<GenCodeDto> getAllGenCode();

    GenCodeDto updateGenCode(Long projectId, GenCodeDto updatedGenCode);

    void deleteGenCode(Long projectId);

}
