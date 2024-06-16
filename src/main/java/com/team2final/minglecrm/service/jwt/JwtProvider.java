package com.team2final.minglecrm.service.jwt;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.team2final.minglecrm.controller.employee.response.SignInResponse;
import com.team2final.minglecrm.controller.employee.response.TokenResponse;
import com.team2final.minglecrm.entity.employee.Employee;
import com.team2final.minglecrm.persistence.repository.employee.EmployeeRepository;
import com.team2final.minglecrm.persistence.dao.RedisDao;
import com.team2final.minglecrm.controller.employee.vo.Subject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final ObjectMapper objectMapper;
    private final RedisDao redisDao;
    private final EmployeeRepository employeeRepository;

    @Value("${spring.jwt.key}")
    private String key;

    @Value("${spring.jwt.live.atk}")
    private Long atkLive;

    @Value("${spring.jwt.live.rtk}")
    private Long rtkLive;


    public TokenResponse createTokensBySignIn(String email) throws JsonProcessingException {
        Employee employee = employeeRepository.findByEmail(email).get();

        Subject atkSubject = Subject.atk(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getAuthority()
        );

        Subject rtkSubject = Subject.rtk(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getAuthority()
        );

        String atk = createToken(atkSubject, atkLive);
        String rtk = createToken(rtkSubject, rtkLive);

        redisDao.setValues(employee.getEmail(), rtk, Duration.ofMillis(rtkLive));

        return TokenResponse.builder()
                .atk(atk)
                .rtk(rtk)
                .atkExpiration(getTokenExpiration(atk))
                .rtkExpiration(getTokenExpiration(rtk))
                .build();
    }

    // 토큰 생성 로직
    private String createToken(Subject subject, Long tokenLive) throws JsonProcessingException {
        SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        String subjectStr = objectMapper.writeValueAsString(subject);
        Claims claims = Jwts.claims()
                .setSubject(subjectStr);
        Date date = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + tokenLive))
                .signWith(secretKey)
                .compact();
    }

    // 토큰에 담긴 유저 정보(Subject)를 추출하는 함수
    public Subject getSubject(String atk) throws JsonProcessingException{
        SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        String subjectStr = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(atk)
                .getBody()
                .getSubject();
        return objectMapper.readValue(subjectStr, Subject.class);
    }

    // HttpServeltRequest에 담긴 atk를 사용해 현재 로그인한 유저 정보 반환
    public Employee getEmployeeFromHttpServletRequest(HttpServletRequest request) throws Exception {
        String atk = request.getHeader("Authorization").substring(7);
        Subject subject = this.getSubject(atk);
        Employee employee = employeeRepository.findByEmail(subject.getEmail()).orElseThrow(Exception::new);
        return employee;

    }


    public Date getTokenExpiration(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody().getExpiration();
        return expiration;

    }

    /**
     * 토큰 재발급 함수
     * AccessToken 만료시 AccessToken과 RefreshToken을 재발급(갱신)함
     *
     */
    public TokenResponse renewToken(String rtk) throws JsonProcessingException {
        Subject subject = getSubject(rtk);
        String rtkInRedis = redisDao.getValues(subject.getEmail());

        if (Objects.isNull(rtkInRedis) || !subject.getType().equals("RTK")) throw new BadCredentialsException("만료된 RefreshToken입니다.");

        redisDao.deleteValues(subject.getEmail()); // 갱신을 위해 RefreshToken 제거

        Subject atkSubject = Subject.atk(
                subject.getId(),
                subject.getName(),
                subject.getEmail(),
                subject.getAuthority());

        Subject rtkSubject = Subject.rtk(
                subject.getId(),
                subject.getName(),
                subject.getEmail(),
                subject.getAuthority());

        String newAtk = createToken(atkSubject, atkLive);
        String newRtk = createToken(rtkSubject, rtkLive);

        // RefreshToken 갱신
        redisDao.setValues(subject.getEmail(), newRtk, Duration.ofMillis(rtkLive));
        return TokenResponse.builder()
                .atk(newAtk)
                .rtk(newRtk)
                .atkExpiration(getTokenExpiration(newAtk))
                .rtkExpiration(getTokenExpiration(newRtk))
                .build();
    }


}