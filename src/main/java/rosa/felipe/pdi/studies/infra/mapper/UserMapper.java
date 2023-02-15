package rosa.felipe.pdi.studies.infra.mapper;

import org.mapstruct.Mapper;
import rosa.felipe.pdi.studies.infra.configuration.UserSpring;
import rosa.felipe.pdi.studies.infra.repository.data.models.UserEntity;

@Mapper
public interface UserMapper {
    UserEntity springConfigToRepository(UserSpring user);
    UserSpring repositoryToSpringConfig(UserEntity user);
}
