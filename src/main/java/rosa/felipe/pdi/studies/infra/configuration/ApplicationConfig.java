package rosa.felipe.pdi.studies.infra.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import rosa.felipe.pdi.studies.infra.mapper.UserMapper;
import rosa.felipe.pdi.studies.infra.repository.data.UserRepository;
import rosa.felipe.pdi.studies.infra.repository.data.models.UserEntity;

import java.util.Optional;

@Configuration
public class ApplicationConfig {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public ApplicationConfig(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            UserEntity userEntity = userRepository.findByUserName(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return userMapper.repositoryToSpringConfig(userEntity);
        };
    }
    }