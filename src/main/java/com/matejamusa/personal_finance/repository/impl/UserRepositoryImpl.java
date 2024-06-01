package com.matejamusa.personal_finance.repository.impl;

import com.matejamusa.personal_finance.exception.ApiException;
import com.matejamusa.personal_finance.model.User;
import com.matejamusa.personal_finance.repository.UserRepository;
import com.matejamusa.personal_finance.rowmapper.UserRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Map;
import static com.matejamusa.personal_finance.query.UserQuery.*;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository<User>, UserDetailsService {
    private final NamedParameterJdbcTemplate jdbc;
    private final BCryptPasswordEncoder encoder;

    @Override
    public User get(Long id) {
        try {
            return jdbc.queryForObject(SELECT_USER_BY_ID_QUERY, Map.of("id", id), new UserRowMapper());
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
            throw new ApiException("No User found by id: " + id);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public User getUserByUsername(String username) {
        try {
            return jdbc.queryForObject(SELECT_USER_BY_USERNAME_QUERY, Map.of("username", username), new UserRowMapper());
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
            throw new ApiException("No User found by username: " + username);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }

    @Override
    public User create(User user) {
        if(getUsernameCount(user.getUsername().trim()) > 0) throw new ApiException("Username is use, please use different username and try again.");
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = getSqlParameterSource(user);
            jdbc.update(INSERT_USER_QUERY, parameters, holder);
            user.setId(requireNonNull(holder.getKey()).longValue());
            return user;
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }
    private Integer getUsernameCount(String username) {
        return jdbc.queryForObject(COUNT_USER_BY_USERNAME_QUERY, Map.of("username", username), Integer.class);
    }

    private SqlParameterSource getSqlParameterSource(User user) {
        return new MapSqlParameterSource()
                .addValue("username", user.getUsername())
                .addValue("password",encoder.encode(user.getPassword()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        if(user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }
        return user;
    }
}
