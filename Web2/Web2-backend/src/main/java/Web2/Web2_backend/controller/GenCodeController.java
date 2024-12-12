package Web2.Web2_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import Web2.Web2_backend.dto.GenCodeDto;
import Web2.Web2_backend.dto.UserDto;
import Web2.Web2_backend.service.GenCodeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/gen-codes")
public class GenCodeController {
    private GenCodeService genCodeService;

    //build Add project Rest API
    @PostMapping
    public ResponseEntity<GenCodeDto> createProject(
        @RequestParam("projectName") String projectName,
        @RequestParam("projectRequire") String projectRequire,
        @RequestParam(value = "fileData", required = false) MultipartFile file,
        @RequestParam("userId") Long userId) {

            try {
                // Chuyển file thành byte[]
                byte[] fileData = null;
                if (file != null) {
                    if(!file.getContentType().equals("applicationzip")) {    
                        if (!file.getContentType().equals("application/x-zip-compressed") ) {
                            return ResponseEntity.badRequest().body(null);
                        }
                    }
                    fileData = file.getBytes();
                }
            // Tạo DTO
                GenCodeDto genCodeDto = new GenCodeDto();
                genCodeDto.setProjectName(projectName);
                genCodeDto.setProjectRequire(projectRequire);
                genCodeDto.setFileData(fileData);
                genCodeDto.setUserid(userId);

                GenCodeDto savedGenCode = genCodeService.createGenCode(genCodeDto);    
                System.out.println("sau khi save Gencode " ); 

                return new ResponseEntity<>(savedGenCode,HttpStatus.CREATED);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
    }

    //build get GenCode REST API
    @GetMapping("{id}")
    public ResponseEntity<GenCodeDto> getProjectbyId(@PathVariable("id") Long projectId) {
        GenCodeDto genCodeDto = genCodeService.getGenCodebyId(projectId);
        return ResponseEntity.ok(genCodeDto);
    }   
    
    
}
