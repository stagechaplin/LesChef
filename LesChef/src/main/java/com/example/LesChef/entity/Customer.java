package com.example.LesChef.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Blob;
import java.util.Collection;
import java.util.List;

@Table(name="Customer") //user 테이블 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity // 현재 클래스를 엔티티로 선언
@Getter // 엔티티를 받고
@Setter //        수정

public class Customer implements UserDetails { //UserDetails를
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "password")
    private String password;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "tel", nullable = false)
    private String tel;
//    @Column(name = "profileImg") //null 가능
//    private Blob profileImg;

    @Builder
    public Customer(String id, String password, String name, String nickname, String tel){
        this.id = id;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.tel = tel;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
    }

    @Override
    public String getUsername() {
        return id;
    }
    @Override
    public String getPassword() {
        return password;
    }


    // 계정 만료 여뷰
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 패스워드 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 사용 가능 여부
    @Override
    public boolean isEnabled() {
        return true;
    }
}
