package com.example.Internship.Tracker.API.security.service;

import com.example.Internship.Tracker.API.config.type.AuthProviderType;
import com.example.Internship.Tracker.API.config.type.RoleType;
import com.example.Internship.Tracker.API.dto.auth_dto.*;
import com.example.Internship.Tracker.API.entity.RefreshTokenEntity;
import com.example.Internship.Tracker.API.entity.UserEntity;
import com.example.Internship.Tracker.API.error.RefreshTokenNotFoundException;
import com.example.Internship.Tracker.API.repository.UserRepository;
import com.example.Internship.Tracker.API.security.util.AuthUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final AuthUtil authUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    public LoginResponseDto login(LoginRequestDto body) {
        // 1. AuthenticationManager delegates to AuthenticationProvider
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(body.getEmail(),body.getPassword())
        );
        //Principals() -> Username and details
        //Credentials() -> Password
        //Details() -> session id and ip address

        // Actually, AuthenticationManager → ProviderManager → DaoAuthenticationProvider
        // DaoAuthenticationProvider uses UserDetailsService + PasswordEncoder

        // 2. Principal is the authenticated user (UserDetails implementation)
        UserEntity user = (UserEntity) authentication.getPrincipal();

        // 3. Refresh token & Generate token
        RefreshTokenEntity refreshToken = refreshTokenService.generateRefreshToken(user.getId());
        String token = authUtil.generateAccessToken(user);

       return  LoginResponseDto.builder()
                .jwt(token)
                .refreshToken(refreshToken.getToken())
               .userid(user.getId())
               .build();

    }

    public UserEntity signupInternal(SignupRequestDto body,AuthProviderType authProviderType,String providerId){
        UserEntity user = userRepository.findByEmail(body.getEmail()).orElse(null);

        if(user!=null)
            throw new IllegalArgumentException("User already exists");

        user = UserEntity.builder()
                .email(body.getEmail())
                .providerId(providerId)
                .providerType(authProviderType)
                .roles(Set.of(RoleType.USER))
                .build();

        if(authProviderType == AuthProviderType.EMAIL && body.getPassword() != null){
            user.setPassword(passwordEncoder.encode(body.getPassword()));
        }
        return userRepository.save(user);
    }



    //Controller
    public SignupResponseDto signup(SignupRequestDto body) {
        UserEntity user = signupInternal(body,AuthProviderType.EMAIL,null);
        return modelMapper.map(user,SignupResponseDto.class);
    }

    @Transactional
    public ResponseEntity<LoginResponseDto> handleOauth2LoginRequest(OAuth2User oAuth2User, String registrationId) {
        // Find Provider type and id
        //save the provider type and id Info with user
        //If user has an account -> directly login
        // if not -> signup -> login

        AuthProviderType providerType = authUtil.getProviderTypeFromRegistrationId(registrationId);
        String providerId = authUtil.determineProviderIdFromOauth2User(oAuth2User,registrationId);

        UserEntity user = userRepository.findByProviderIdAndProviderType(providerId,providerType).orElse(null);

        String email = oAuth2User.getAttribute("email");

        UserEntity emailUser = userRepository.findByEmail(email).orElse(null);

        if(user == null && emailUser == null){
            //signup flow:
            String emailSignup = authUtil.determineEmailFromOauth2User(oAuth2User,registrationId,providerId);
            user = signupInternal(new SignupRequestDto(null,emailSignup),providerType,providerId);
        } else if (user!=null) {
            if(email!=null && !email.isBlank() && !email.equals(user.getEmail())){
                user.setEmail(email);
                userRepository.save(user);
            }
        }else {
            throw new BadCredentialsException("This email is already registered with provider : "+emailUser.getProviderType());
        }

        LoginResponseDto loginResponseDto = new LoginResponseDto(authUtil.generateAccessToken(user),refreshTokenService.generateRefreshToken(user.getId()).getToken(), user.getId());

        return ResponseEntity.ok(loginResponseDto);
    }

    @Transactional
    public LoginResponseDto refreshToken(RefreshTokenRequestDto body) {
        RefreshTokenEntity refreshToken = refreshTokenService.findToken(body.getToken())
                .orElseThrow(() -> new RefreshTokenNotFoundException("Refresh token not found: " + body.getToken()));

        refreshToken = refreshTokenService.verifyAndRotate(refreshToken);
        UserEntity user = refreshToken.getUser();
        String accessToken = authUtil.generateAccessToken(user);

        return LoginResponseDto.builder()
                .jwt(accessToken)
                .refreshToken(refreshToken.getToken())
                .userid(user.getId())
                .build();
    }
}
