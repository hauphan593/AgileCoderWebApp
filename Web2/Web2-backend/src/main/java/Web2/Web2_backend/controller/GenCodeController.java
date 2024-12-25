package Web2.Web2_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Web2.Web2_backend.dto.GenCodeDto;
import Web2.Web2_backend.service.GenCodeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<GenCodeDto> createProject(@RequestBody GenCodeDto genCodeDto) {

            // Tạo DTO
                GenCodeDto savedGenCode = genCodeService.createGenCode(genCodeDto);    
                System.out.println(" Gencode complete" ); 

                return new ResponseEntity<>(savedGenCode,HttpStatus.CREATED);
            
    }

    //build get GenCode REST API
    @GetMapping("{id}")
    public ResponseEntity<GenCodeDto> getProjectbyId(@PathVariable("id") Long projectId) {
        GenCodeDto genCodeDto = genCodeService.getGenCodebyId(projectId);
        return ResponseEntity.ok(genCodeDto);
    }   
    
    //build get all Gencode Rest API
    @GetMapping
    public ResponseEntity<List<GenCodeDto>> getAllProject() {
        List<GenCodeDto> genCodes = genCodeService.getAllGenCode();
        return ResponseEntity.ok(genCodes);
    }

    //build Update Gencode REST API
    @PutMapping("{id}")
    public ResponseEntity<GenCodeDto> updateProject(@PathVariable("id") Long projectId, @RequestBody GenCodeDto updatedGenCode) {
        GenCodeDto genCodeDto = genCodeService.updateGenCode(projectId, updatedGenCode);
        return ResponseEntity.ok(genCodeDto);
    }

    //Build Delete Gencode REST API
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteGenCode(@PathVariable("id") Long projectId) {
        genCodeService.deleteGenCode(projectId);
        return ResponseEntity.ok("Project deleted successfully!.");
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<String> downloadGenCode(@PathVariable("id") Long id) {
        genCodeService.DownloadedGenCode(id);
        return ResponseEntity.ok("Project downloaded successfully!");
    }

}
