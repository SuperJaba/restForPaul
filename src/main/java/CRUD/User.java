package CRUD;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Lob;
import java.sql.Blob;
import java.sql.Date;

@Data
@Entity
public class User {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String pass;
    @Lob
    private Blob image;
    private Date created;

    public User() {
    }
}
