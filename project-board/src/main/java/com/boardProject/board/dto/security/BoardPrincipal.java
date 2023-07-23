package com.boardProject.board.dto.security;

import com.boardProject.board.dto.UserAccountDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record BoardPrincipal(
        String userName,
        String password,
        Collection<? extends GrantedAuthority> authorities,
        String email,
        String nickName,
        String memo
) implements UserDetails {
    public static BoardPrincipal of(String userName, String password, String email, String nickName, String memo) {
        Set<RoleType> roleTypes = Set.of(RoleType.USER);

        return new BoardPrincipal(
                userName,
                password,
                roleTypes.stream()
                        .map(RoleType::getName)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet())
                ,
                email,
                nickName,
                memo
        );
    }

    public static BoardPrincipal from(UserAccountDto dto){
        return BoardPrincipal.of(
                dto.userId(),
                dto.userPassword(),
                dto.email(),
                dto.nickname(),
                dto.memo()
        );
    }

    public UserAccountDto toDto(){
        return UserAccountDto.of(
                userName,
                password,
                email,
                nickName,
                memo
        );
    }

    //스프링 시큐리티에서 인증은 로그인을 했느냐 안했느냐,
    //권한은 로그인 한 사용자가 어떤 권한을 가지고 있느냐 (운영자, 사용자 ...등)

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }


    //권한에 관련된 부분
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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

    public enum RoleType{
        USER("ROLE_USER");

        @Getter
        private final String name;

        RoleType(String name){
            this.name=name;
        }
    }
}
