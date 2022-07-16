package com.example.topshiriqfirma.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Hodimlar implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String ism;
    @Column(nullable = false)
    private String familiya;
    @Column(nullable = false)
    private String telefon;
    @Email
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String parol;
    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp hosilqilinganvaqt;
    @UpdateTimestamp
    @Column(nullable = false)
    private Timestamp yangilanganVaqt;

    private String emailcode;

    @Column(nullable = false)
    private boolean type;

    @OneToOne
    Roles rols;

    private Boolean accountNonExpired = true;
    private Boolean accountNonLocked = true;
    private Boolean credentialsNonExpired = true;
    private Boolean enabled = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(rols);
    }

    @Override
    public String getPassword() {
        return parol;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
