package com.jhipster.service;

import com.jhipster.domain.UserSetting;
import com.jhipster.repository.UserSettingRepository;
import com.jhipster.service.dto.UserSettingDTO;
import com.jhipster.service.mapper.UserSettingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing UserSetting.
 */
@Service
@Transactional
public class UserSettingService {

    private final Logger log = LoggerFactory.getLogger(UserSettingService.class);

    private final UserSettingRepository userSettingRepository;

    private final UserSettingMapper userSettingMapper;

    public UserSettingService(UserSettingRepository userSettingRepository, UserSettingMapper userSettingMapper) {
        this.userSettingRepository = userSettingRepository;
        this.userSettingMapper = userSettingMapper;
    }

    /**
     * Save a userSetting.
     *
     * @param userSettingDTO the entity to save
     * @return the persisted entity
     */
    public UserSettingDTO save(UserSettingDTO userSettingDTO) {
        log.debug("Request to save UserSetting : {}", userSettingDTO);
        UserSetting userSetting = userSettingMapper.toEntity(userSettingDTO);
        userSetting = userSettingRepository.save(userSetting);
        return userSettingMapper.toDto(userSetting);
    }

    /**
     *  Get all the userSettings.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<UserSettingDTO> findAll() {
        log.debug("Request to get all UserSettings");
        return userSettingRepository.findAll().stream()
            .map(userSettingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one userSetting by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public UserSettingDTO findOne(Long id) {
        log.debug("Request to get UserSetting : {}", id);
        UserSetting userSetting = userSettingRepository.findOne(id);
        return userSettingMapper.toDto(userSetting);
    }

    /**
     *  Delete the  userSetting by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserSetting : {}", id);
        userSettingRepository.delete(id);
    }
}
