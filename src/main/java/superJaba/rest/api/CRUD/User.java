package superJaba.rest.api.CRUD;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Date;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String pass;
    @Lob
    private Blob image;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_DATE", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false, updatable = false)
    private Date createdDate;
    @Version
    private java.sql.Timestamp myTimestamp;


    public User() {
    }

    public User(String firstName, String lastName, String email, String pass) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.pass = pass;
    }

}
