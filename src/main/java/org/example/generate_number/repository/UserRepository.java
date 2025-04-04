package org.example.generate_number.repository;

import org.example.generate_number.entity.User;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {
    private final JdbcClient jdbcClient;

    public UserRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Optional<User> findByEmail(String email) {
        String sql = """
                    SELECT id, email, password, created_at, role
                    FROM public.users
                    WHERE email = ?;
                """;

        return jdbcClient.sql(sql).param(email).query(User.class).optional();
    }

    public void save(User user) {
        String sql = """
                INSERT INTO public.users (email, password, role)
                VALUES (?, ?,  CAST(? AS user_role))
                """;

        jdbcClient.sql(sql)
                .params(user.getEmail(), user.getPassword(), user.getRole().name())
                .update();
    }

    public boolean existsByEmail(String email) {
        String sql = """
                SELECT EXISTS(SELECT 1 FROM public.users WHERE email = ?)
                """;

        return jdbcClient.sql(sql)
                .param(email)
                .query(Boolean.class)
                .single();
    }

}
