package Web2.Web2_backend.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name="account_role")
public class AccountRole {

    @Id
    private Long roleId;

    @Column (name = "Role_Name")
    private String roleName;

    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;

    @Column(name = "created_by", nullable = true)
    private String createdBy;

    @Column(name = "updated_at", nullable = true)
    private Timestamp updatedAt;

    @Column(name = "updated_by", nullable = true)
    private String updatedBy;
    

}
