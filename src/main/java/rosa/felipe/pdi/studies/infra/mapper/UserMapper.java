package rosa.felipe.pdi.studies.infra.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import rosa.felipe.pdi.studies.infra.configuration.UserSpring;
import rosa.felipe.pdi.studies.infra.repository.data.models.UserEntity;

@Mapper
@Component
public interface UserMapper {
    UserEntity springConfigToRepository(UserSpring user);
    UserSpring repositoryToSpringConfig(UserEntity user);
}
