package dev.farhan.movieist.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private String password;
    private String role = "USER";

    public User() {}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getId()              { return id; }
    public String getEmail()           { return email; }
    public void   setEmail(String e)   { this.email = e; }
    public String getPassword()        { return password; }
    public void   setPassword(String p){ this.password = p; }
    public String getRole()            { return role; }
    public void   setRole(String r)    { this.role = r; }
}