package org.example.generate_number.security;

import org.example.generate_number.entity.User;
import org.example.generate_number.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;//интерфейс, который описывает пользователя для Spring Security.
import org.springframework.security.core.userdetails.UserDetailsService;//интерфейс от Spring Security, который обязывает нас реализовать метод loadUserByUsername().
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

//Когда пользователь пытается залогиниться, Spring Security вызывает этот класс, чтобы:
//Найти пользователя в базе данных по email (или username).
//Получить его пароль.
//Получить его роли/права (если есть).
//Вернуть объект, который Spring Security сможет использовать для проверки пароля и авторизации.
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Возвращает UserDetails — объект, который Spring Security будет использовать для проверки пароля.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        //Здесь мы создаём объект UserDetails, используя готовую реализацию от Spring (org.springframework.security.core.userdetails.User)
        //user.getEmail() — имя пользователя (оно же subject в токене).
        //user.getPassword() — хешированный пароль из БД.
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
//              Collections.emptyList() // Можно добавить роли и права доступа здесь(список прав доступа )
        );
    }
}
