package org.example.generate_number.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.generate_number.security.JwtTokenProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//Класс-фильтр, который перехватывает каждый HTTP-запрос и извлекает токен.
//Это означает, что фильтр будет срабатывать один раз на каждый запрос.
// В отличие от обычного фильтра, который может вызываться несколько раз в рамках одной обработки запроса,
// OncePerRequestFilter гарантирует однократное выполнение.
public class JwtTokenFilter extends OncePerRequestFilter {
    //Внедряется зависимость JwtTokenProvider, который умеет:
    //Валидацию токена.
    //Доставание имени пользователя из токена.
    //Получение Authentication объекта.
    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    //Этот метод вызывается автоматически при каждом HTTP-запросе.
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String token = resolveToken(request);// Достаёт токен из заголовка Authorization

        //Если токен не null и прошёл валидацию (validateToken() проверяет подпись и срок действия):
        //Получаем объект Authentication, который содержит имя пользователя.
        //Устанавливаем пользователя в SecurityContextHolder — теперь Spring считает, что пользователь авторизован
        if (token != null && jwtTokenProvider.validateToken(token)) {
            var authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        //Эта строка обязательно нужна — она продолжает цепочку фильтров и передаёт управление дальше (например, в контроллер).
        filterChain.doFilter(request, response);
    }

    //Смотрит на заголовок Authorization.
    //Проверяет, начинается ли он с "Bearer " — это стандарт.
    //Если да, отрезает "Bearer " и возвращает только токен.
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
