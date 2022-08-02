package nl.davebeerensdesigns.hosting_management.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(nl.davebeerensdesigns.hosting_management.model.AuthorityKey.class)
@Table(name = "authorities")
public class Authority implements Serializable {

    @Id
    @Column(nullable = false, length = 50, updatable = false)
    private String username;

    @Id
    @Column(nullable = false)
    private String authority;

    public enum UserRoles {
        ADMIN("ROLE_ADMIN"),
        DEFAULT("ROLE_USER");

        String value;
        UserRoles(String value){
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }

    }

    public Authority() {}
    public Authority(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getAuthority() {
        return authority;
    }
    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
