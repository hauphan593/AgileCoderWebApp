package Web2.Web2_backend.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenCodeDto {
    
    private Long projectId;
    private String projectName;
    private String projectRequire;
    private byte[] fileData;
    private Long userid;
    //Date info
    private Timestamp createdAt;
    private String createdBy;
    private Timestamp updatedAt;
    private String updatedBy;

}
