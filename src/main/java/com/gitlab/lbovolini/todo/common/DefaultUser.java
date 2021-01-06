package com.gitlab.lbovolini.todo.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Pattern;
import java.util.Collection;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

public class DefaultUser implements UserDetails {

    @Id
    protected String id;
    @Pattern(regexp = "^(\\w){3,20}\\b")
    @Indexed(name = "user_username_index_unique", unique = true)
    protected String username;
    @Pattern(regexp = "^(?=.*[\\d])(?=.*[a-z])[\\w!@#$%^&*()-=+,.;:]{8,}$")
    @JsonProperty(access = WRITE_ONLY)
    protected String password;
    @JsonIgnore
    protected Collection<? extends GrantedAuthority> authorities;
    @JsonIgnore
    protected Boolean accountNonLocked;

    public DefaultUser() {}

    public String getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

}
