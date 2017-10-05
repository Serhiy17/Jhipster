package com.jhipster.web.rest;

import com.jhipster.JhipsterApp;

import com.jhipster.domain.UserSetting;
import com.jhipster.domain.User;
import com.jhipster.repository.UserSettingRepository;
import com.jhipster.service.UserSettingService;
import com.jhipster.service.dto.UserSettingDTO;
import com.jhipster.service.mapper.UserSettingMapper;
import com.jhipster.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jhipster.domain.enumeration.SettingType;
/**
 * Test class for the UserSettingResource REST controller.
 *
 * @see UserSettingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class UserSettingResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final SettingType DEFAULT_TYPE = SettingType.USER;
    private static final SettingType UPDATED_TYPE = SettingType.SYSTEM;

    @Autowired
    private UserSettingRepository userSettingRepository;

    @Autowired
    private UserSettingMapper userSettingMapper;

    @Autowired
    private UserSettingService userSettingService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserSettingMockMvc;

    private UserSetting userSetting;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserSettingResource userSettingResource = new UserSettingResource(userSettingService);
        this.restUserSettingMockMvc = MockMvcBuilders.standaloneSetup(userSettingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserSetting createEntity(EntityManager em) {
        UserSetting userSetting = new UserSetting()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .type(DEFAULT_TYPE);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        userSetting.setUser(user);
        return userSetting;
    }

    @Before
    public void initTest() {
        userSetting = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserSetting() throws Exception {
        int databaseSizeBeforeCreate = userSettingRepository.findAll().size();

        // Create the UserSetting
        UserSettingDTO userSettingDTO = userSettingMapper.toDto(userSetting);
        restUserSettingMockMvc.perform(post("/api/user-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userSettingDTO)))
            .andExpect(status().isCreated());

        // Validate the UserSetting in the database
        List<UserSetting> userSettingList = userSettingRepository.findAll();
        assertThat(userSettingList).hasSize(databaseSizeBeforeCreate + 1);
        UserSetting testUserSetting = userSettingList.get(userSettingList.size() - 1);
        assertThat(testUserSetting.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUserSetting.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testUserSetting.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createUserSettingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userSettingRepository.findAll().size();

        // Create the UserSetting with an existing ID
        userSetting.setId(1L);
        UserSettingDTO userSettingDTO = userSettingMapper.toDto(userSetting);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserSettingMockMvc.perform(post("/api/user-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userSettingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserSetting in the database
        List<UserSetting> userSettingList = userSettingRepository.findAll();
        assertThat(userSettingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSettingRepository.findAll().size();
        // set the field null
        userSetting.setName(null);

        // Create the UserSetting, which fails.
        UserSettingDTO userSettingDTO = userSettingMapper.toDto(userSetting);

        restUserSettingMockMvc.perform(post("/api/user-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userSettingDTO)))
            .andExpect(status().isBadRequest());

        List<UserSetting> userSettingList = userSettingRepository.findAll();
        assertThat(userSettingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSettingRepository.findAll().size();
        // set the field null
        userSetting.setValue(null);

        // Create the UserSetting, which fails.
        UserSettingDTO userSettingDTO = userSettingMapper.toDto(userSetting);

        restUserSettingMockMvc.perform(post("/api/user-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userSettingDTO)))
            .andExpect(status().isBadRequest());

        List<UserSetting> userSettingList = userSettingRepository.findAll();
        assertThat(userSettingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserSettings() throws Exception {
        // Initialize the database
        userSettingRepository.saveAndFlush(userSetting);

        // Get all the userSettingList
        restUserSettingMockMvc.perform(get("/api/user-settings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getUserSetting() throws Exception {
        // Initialize the database
        userSettingRepository.saveAndFlush(userSetting);

        // Get the userSetting
        restUserSettingMockMvc.perform(get("/api/user-settings/{id}", userSetting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userSetting.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserSetting() throws Exception {
        // Get the userSetting
        restUserSettingMockMvc.perform(get("/api/user-settings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserSetting() throws Exception {
        // Initialize the database
        userSettingRepository.saveAndFlush(userSetting);
        int databaseSizeBeforeUpdate = userSettingRepository.findAll().size();

        // Update the userSetting
        UserSetting updatedUserSetting = userSettingRepository.findOne(userSetting.getId());
        updatedUserSetting
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .type(UPDATED_TYPE);
        UserSettingDTO userSettingDTO = userSettingMapper.toDto(updatedUserSetting);

        restUserSettingMockMvc.perform(put("/api/user-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userSettingDTO)))
            .andExpect(status().isOk());

        // Validate the UserSetting in the database
        List<UserSetting> userSettingList = userSettingRepository.findAll();
        assertThat(userSettingList).hasSize(databaseSizeBeforeUpdate);
        UserSetting testUserSetting = userSettingList.get(userSettingList.size() - 1);
        assertThat(testUserSetting.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserSetting.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testUserSetting.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserSetting() throws Exception {
        int databaseSizeBeforeUpdate = userSettingRepository.findAll().size();

        // Create the UserSetting
        UserSettingDTO userSettingDTO = userSettingMapper.toDto(userSetting);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserSettingMockMvc.perform(put("/api/user-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userSettingDTO)))
            .andExpect(status().isCreated());

        // Validate the UserSetting in the database
        List<UserSetting> userSettingList = userSettingRepository.findAll();
        assertThat(userSettingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserSetting() throws Exception {
        // Initialize the database
        userSettingRepository.saveAndFlush(userSetting);
        int databaseSizeBeforeDelete = userSettingRepository.findAll().size();

        // Get the userSetting
        restUserSettingMockMvc.perform(delete("/api/user-settings/{id}", userSetting.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserSetting> userSettingList = userSettingRepository.findAll();
        assertThat(userSettingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserSetting.class);
        UserSetting userSetting1 = new UserSetting();
        userSetting1.setId(1L);
        UserSetting userSetting2 = new UserSetting();
        userSetting2.setId(userSetting1.getId());
        assertThat(userSetting1).isEqualTo(userSetting2);
        userSetting2.setId(2L);
        assertThat(userSetting1).isNotEqualTo(userSetting2);
        userSetting1.setId(null);
        assertThat(userSetting1).isNotEqualTo(userSetting2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserSettingDTO.class);
        UserSettingDTO userSettingDTO1 = new UserSettingDTO();
        userSettingDTO1.setId(1L);
        UserSettingDTO userSettingDTO2 = new UserSettingDTO();
        assertThat(userSettingDTO1).isNotEqualTo(userSettingDTO2);
        userSettingDTO2.setId(userSettingDTO1.getId());
        assertThat(userSettingDTO1).isEqualTo(userSettingDTO2);
        userSettingDTO2.setId(2L);
        assertThat(userSettingDTO1).isNotEqualTo(userSettingDTO2);
        userSettingDTO1.setId(null);
        assertThat(userSettingDTO1).isNotEqualTo(userSettingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userSettingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userSettingMapper.fromId(null)).isNull();
    }
}
