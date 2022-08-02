package nl.davebeerensdesigns.hosting_management.model;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class AuthorityKey implements Serializable {
    private String username;
    private String authority;
}