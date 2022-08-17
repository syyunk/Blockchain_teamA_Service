package com.ateam.popserver.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "member")
@ToString(exclude = {"wallet"})
public class Member extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mnum;
   
    @OneToOne//무조건 lazy로
    @JoinColumn(name = "wnum", nullable=false)
    private Wallet wallet;

    @Column(name = "mid", nullable = false)
    private String mid;

    @Column(name = "pw", nullable = false)
    private String pw;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "tel", nullable = false)
    private String tel;

    @Column(name = "addr", nullable = false)
    private String addr;

    public void changePw(String pw) {
        this.pw=pw;
    }
    public void changeNickname(String nickname) {
        this.nickname=nickname;
    }
    public void changeTel(String tel) {
        this.tel=tel;
    }
    public void changeAddr(String addr) {
        this.addr=addr;
    }

    @Transient
    private List<SimpleGrantedAuthority> authorities;

    public void setAuthorities(List<SimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.pw;
    }

    @Override
    public String getUsername() {
        return this.mid;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}