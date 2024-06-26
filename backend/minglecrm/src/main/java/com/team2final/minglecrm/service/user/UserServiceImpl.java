package com.team2final.minglecrm.service.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.team2final.minglecrm.controller.user.dto.*;
import com.team2final.minglecrm.domain.User;
import com.team2final.minglecrm.dto.UserDetailResponse;
import com.team2final.minglecrm.dto.UserResponse;
import com.team2final.minglecrm.repository.UserRepository;
import com.team2final.minglecrm.service.jwt.JwtProvider;
import com.team2final.minglecrm.util.redis.RedisDao;
import com.team2final.minglecrm.vo.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RedisDao redisDao;

    @Override
    @Transactional
    public SignUpResponseDTO signUp(SignUpRequestDTO signUpRequestDTO) {

        if (userRepository.existsByEmail(signUpRequestDTO.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // 비밀번호 유효성 검사
        String password = signUpRequestDTO.getPassword();
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("비밀번호는 최소 8자 이상이어야 합니다.");
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = User.builder()
                .email(signUpRequestDTO.getEmail())
                .password(encodedPassword)
                .name(signUpRequestDTO.getName())
                .authority("ROLE_STAFF")
                .build();

        userRepository.save(user);
        return SignUpResponseDTO.of(user);
    }

    @Override
    @Transactional(readOnly = true)
    public SignInResponseDTO signIn(SignInRequestDTO signInRequestDTO) {
        User user = userRepository.findByEmail(signInRequestDTO.getEmail()).get();

        boolean matches = passwordEncoder.matches(signInRequestDTO.getPassword(), user.getPassword());
        if (!matches) {
            return null;
        }
        return SignInResponseDTO.of(user);
    }

    @Override
    @Transactional
    public Void logout(String atk) throws JsonProcessingException {
        Subject subject = jwtProvider.getSubject(atk);
        redisDao.getValues(subject.getEmail());
        redisDao.deleteValues(subject.getEmail());
        System.out.println(subject.getEmail() + "로그아웃이요");
        return null;
    }

    @Override
    public List<UserResponse> findAll() {
        List<UserResponse> userResponses = new ArrayList<>();
        List<User> users = userRepository.findAll();

        for (User user : users) {
            UserResponse userResponse = UserResponse.builder()
                    .id(user.getId())
                    .authority(user.getAuthority())
                    .name(user.getName())
                    .email(user.getEmail())
                    .build();
            userResponses.add(userResponse);
        }
        return userResponses;
    }

    @Override
    public void updateUserDetail(Long userId, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(userId).orElseThrow();
        String encodedPassword = passwordEncoder.encode(userUpdateRequest.getPassword());
        user.updateUser(userUpdateRequest, encodedPassword);
    }

    @Override
    public UserDetailResponse findUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();

        return UserDetailResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .password(user.getPassword())
                .build();
    }
}
