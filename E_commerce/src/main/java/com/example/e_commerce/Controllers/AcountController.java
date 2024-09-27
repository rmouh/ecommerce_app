package com.example.e_commerce.Controllers;
import com.example.e_commerce.Models.LoginDto;
import com.example.e_commerce.Models.RegisterDto;
import com.example.e_commerce.Models.UserApp;
import com.example.e_commerce.Repositories.UserRepository;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import static com.example.e_commerce.Configs.SecurityConfig.SECRET_KEY;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;


@RestController
@RequestMapping("/account")
public class AcountController {

    @Autowired
    private AuthenticationManager authenticationManager;

    //reading the jeson web token params from properties
    //@Value("$security.jwt.secret-key")
    //public static final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";
    private String jwtSecretKey=SECRET_KEY;

    @Value("$security.jwt.issuer")
    private String jwtIssuer;

    @Autowired
    private UserRepository repo;
    @GetMapping("/")
    public  String home(){
        return "Home page";
    }
    @PostMapping("/register")
    //RegistDto ibject contains the submited data
    //result used to check if data is valid

    //Methode to register a new user
    public ResponseEntity<Object> register(
            @Valid @RequestBody RegisterDto registerDto, BindingResult result){

        if(result.hasErrors()){
            var errorsList = result.getAllErrors();
            var errorsMap = new HashMap<String, String>();

            for (int i=0; i< errorsList.size(); i++){
                var error = (FieldError) errorsList.get(i);
                errorsMap.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errorsMap);

        }
        var bCryptEncoder = new BCryptPasswordEncoder();
        UserApp user = new UserApp();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setRole("client");
        user.setCreatedAt(new Date());
        user.setPassword(bCryptEncoder.encode((registerDto.getPassword())));

        // save the user detail in the database
        try {
            var otherUser = repo.findByUsername(registerDto.getUsername());
            if (otherUser!=null){
                return ResponseEntity.badRequest().body("Username alrady used");
            }

            otherUser = repo.findByEmail(registerDto.getEmail());
            if(otherUser != null) {
                return ResponseEntity.badRequest().body("Email address alrady used");
            }
            repo.save(user);
            String jwtToken = createJwtToken(user);

            var response = new HashMap<String, Object>();
            response.put("token", jwtToken);
            response.put("user", user);
            return ResponseEntity.ok(response);
        }catch (Exception ex){
            System.out.println("There is an Exception :");
            ex.printStackTrace();

        }
        return ResponseEntity.badRequest().body("Error");
    }
    // Methode to login using password and username
    @PostMapping("/login")
    public ResponseEntity<Object> login(
            @Valid @RequestBody LoginDto loginDto, BindingResult result){

        if(result.hasErrors()){
            var errorsList =result.getAllErrors();
            var errorsMap =new HashMap<String, String>();

            for (int i=0; i< errorsList.size(); i++){
                var error = (FieldError) errorsList.get(i);
                errorsMap.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errorsMap);
        }
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()
                    )
            );
            UserApp user = repo.findByUsername(loginDto.getUsername());
            String jwtToken = createJwtToken(user);

            var response = new HashMap<String, Object>();
            response.put("token", jwtToken);
            response.put("user", user);
            return ResponseEntity.ok(response);

        }catch (Exception ex){
            System.out.println("There is an Exception :");
            ex.printStackTrace();

        }
        return ResponseEntity.badRequest().body("Bad username or password");

    }
    //Methode to login using the JWT
    @GetMapping("/profil")
    public ResponseEntity<Object> profile(Authentication auth){
        var response = new HashMap<String, Object>();
        response.put("Username",auth.getName() );
        response.put("Authorities", auth.getAuthorities());

        var user = repo.findByUsername(auth.getName());
        response.put("User", user);
        return ResponseEntity.ok(response);
    }
    //Methode to create a JWT
    private String createJwtToken(UserApp user){
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(jwtIssuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(24*3600))
                .subject(user.getUsername())
                .claim("role", user.getRole())
                .build();

        var encoder = new NimbusJwtEncoder(
                new ImmutableSecret<>(jwtSecretKey.getBytes()));
        var params = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(), claims);
        return encoder.encode(params).getTokenValue();
    }



}
