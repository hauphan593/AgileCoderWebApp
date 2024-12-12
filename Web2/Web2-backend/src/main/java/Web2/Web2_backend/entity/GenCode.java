package Web2.Web2_backend.entity;

import java.sql.Timestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="gen_codes")
public class GenCode {  
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long projectId;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "project_require")
    private String projectRequire;


    @Lob
    @Column(name = "file_data", columnDefinition = "LONGBLOB", nullable = true)
    private byte[] fileData;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    private User user;
    
    //Date info
    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;

    @Column(name = "created_by", nullable = true)
    private String createdBy;

    @Column(name = "updated_at", nullable = true)
    private Timestamp updatedAt;

    @Column(name = "updated_by", nullable = true)
    private String updatedBy;
    
}
