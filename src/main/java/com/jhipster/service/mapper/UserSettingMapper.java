package com.jhipster.service.mapper;

import com.jhipster.domain.*;
import com.jhipster.service.dto.UserSettingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserSetting and its DTO UserSettingDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface UserSettingMapper extends EntityMapper <UserSettingDTO, UserSetting> {

    @Mapping(source = "user.id", target = "userId")
    UserSettingDTO toDto(UserSetting userSetting);

    @Mapping(source = "userId", target = "user")
    UserSetting toEntity(UserSettingDTO userSettingDTO);
    default UserSetting fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserSetting userSetting = new UserSetting();
        userSetting.setId(id);
        return userSetting;
    }
}
