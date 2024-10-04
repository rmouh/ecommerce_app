package com.example.e_commerce.Controllers;

import com.example.e_commerce.Models.*;
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
import org.slf4j.Logger; // Import pour le logger
import org.slf4j.LoggerFactory; // Import pour le logger

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/account")
public class AcountController {

    @Autowired
    private AuthenticationManager authenticationManager;

    // Déclaration du Logger
    private static final Logger logger = LoggerFactory.getLogger(AcountController.class);


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

    /*
    {
    "firstName": "Rankkissxcfcxswca",
    "lastName": "Manklwsfsvxxcxswxw!l",
    "password": "Myspdsjcxxvsssvfsbvjjjjs123",
    "email": "ranabbFsùvxmksfsjwxDQ@gmail.com",
    "username": "randjlsvxsxnFsFFlfmanel00"
    }
    */
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
    // Méthode pour obtenir un utilisateur spécifique par ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id) {
        try {
            // Récupération de l'utilisateur par ID
            var user = repo.findById(id);

            // Vérification si l'utilisateur existe
            if (user.isPresent()) {
                return ResponseEntity.ok(user.get());
            } else {
                return ResponseEntity.status(404).body("Utilisateur non trouvé");
            }
        } catch (Exception ex) {
            logger.error("Erreur lors de la récupération de l'utilisateur: ", ex);
            return ResponseEntity.status(500).body("Erreur interne du serveur.");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable Long id) {
        try {
            // Vérifier si l'utilisateur existe
            var user = repo.findById(id);
            if (user.isPresent()) {
                // Supprimer l'utilisateur
                repo.deleteById(id);
                return ResponseEntity.ok("Utilisateur supprimé avec succès");
            } else {
                return ResponseEntity.status(404).body("Utilisateur non trouvé");
            }
        } catch (Exception ex) {
            logger.error("Erreur lors de la suppression de l'utilisateur: ", ex);
            return ResponseEntity.status(500).body("Erreur interne du serveur.");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateUserInfo(@Valid @RequestBody UpdateUserDto updateUserDto, Authentication auth) {
        try {
            // Récupérer le nom d'utilisateur à partir du token JWT
            String username = auth.getName();
            UserApp user = repo.findByUsername(username);

            if (user == null) {
                return ResponseEntity.status(404).body("Utilisateur non trouvé");
            }

            // Mise à jour des informations de l'utilisateur
            user.setFirstName(updateUserDto.getFirstName());
            user.setLastName(updateUserDto.getLastName());
            user.setEmail(updateUserDto.getEmail());
            user.setAddress(updateUserDto.getAddress());

            // Mise à jour du mot de passe
            if (updateUserDto.getPassword() != null && !updateUserDto.getPassword().isEmpty()) {
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                user.setPassword(bCryptPasswordEncoder.encode(updateUserDto.getPassword()));
            }

            // Sauvegarder les nouvelles informations
            repo.save(user);

            // Retourner les informations mises à jour
            return ResponseEntity.ok(user);
        } catch (Exception ex) {
            logger.error("Erreur lors de la mise à jour de l'utilisateur: ", ex);
            return ResponseEntity.status(500).body("Erreur interne du serveur.");
        }
    }



    // Methode to login using password and username

    /*
    {
    "password": "Myspdsjcxxvsssvfsbvjjjjs123",
    "username": "randjlsvxsxnFsFFlfmanel00"
    }
     */
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