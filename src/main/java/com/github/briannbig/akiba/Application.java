package com.github.briannbig.akiba;

import com.github.briannbig.akiba.api.dto.RoleDTO;
import com.github.briannbig.akiba.api.request.UserCreateRequest;
import com.github.briannbig.akiba.config.AkibaConfigProperties;
import com.github.briannbig.akiba.config.JWTConfigProperties;
import com.github.briannbig.akiba.entities.Role;
import com.github.briannbig.akiba.entities.enums.RoleName;
import com.github.briannbig.akiba.repository.RoleRepository;
import com.github.briannbig.akiba.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.List;

@EnableConfigurationProperties({AkibaConfigProperties.class, JWTConfigProperties.class})
@SpringBootApplication
public class Application {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    public CommandLineRunner initRoles(UserService userService, RoleRepository roleRepository) {
        return args -> {
            var optionalSuperAdminRole = roleRepository.findByRoleName(RoleName.SUPER_ADMIN);

            if (optionalSuperAdminRole.isEmpty()) {
                log.info("Creating Super Admin Role ....");
                userService.saveRole(new Role(RoleName.SUPER_ADMIN));
            }

            var optionalAdminRole = roleRepository.findByRoleName(RoleName.ADMIN);
            if (optionalAdminRole.isEmpty()) {
                log.info("Creating Admin role...");
                userService.saveRole(new Role(RoleName.ADMIN));
            }

            var optionalCashierRole = roleRepository.findByRoleName(RoleName.CUSTOMER);
            if (optionalCashierRole.isEmpty()) {
                log.info("Creating Customer role...");
                userService.saveRole(new Role(RoleName.CUSTOMER));
            }

            var optionalDevRole = roleRepository.findByRoleName(RoleName.DEVELOPER);
            if (optionalDevRole.isEmpty()) {
                log.info("Creating Developer role...");
                userService.saveRole(new Role(RoleName.DEVELOPER));
            }
        };
    }

    @Bean
    public CommandLineRunner createDevAccount(UserService userService, AkibaConfigProperties props) {
        return args -> {
            var optionalDevUser = userService.findUsersByRoleName(RoleName.ADMIN).stream().findFirst();

            if (optionalDevUser.isEmpty()) {
                var optionalRole = userService.findRoleByRoleName(RoleName.ADMIN);
                if (optionalRole.isPresent()) {
                    log.info("creating admin role...");
                    var role = optionalRole.get();
                    List<String> roles = List.of(role.getRoleName().name());
                    var request = new UserCreateRequest(props.defaultAdminUsername(), props.defaultAdminEmail(), "Akiba admin", props.defaultAdminPassword(), props.defaultAdminPassword(), roles);
                    userService.registerUser(request);

                }
                log.error("Could not find admin role");

            }

        };
    }
}
