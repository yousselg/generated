package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhispterApp;

import com.mycompany.myapp.domain.Test1;
import com.mycompany.myapp.repository.Test1Repository;
import com.mycompany.myapp.service.Test1Service;
import com.mycompany.myapp.repository.search.Test1SearchRepository;
import com.mycompany.myapp.service.dto.Test1DTO;
import com.mycompany.myapp.service.mapper.Test1Mapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

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

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Test1Resource REST controller.
 *
 * @see Test1Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhispterApp.class)
public class Test1ResourceIntTest {

    private static final Integer DEFAULT_PROP_1 = 0;
    private static final Integer UPDATED_PROP_1 = 1;

    private static final String DEFAULT_PROP_2 = "AAAAAAAAAA";
    private static final String UPDATED_PROP_2 = "BBBBBBBBBB";

    @Autowired
    private Test1Repository test1Repository;

    @Autowired
    private Test1Mapper test1Mapper;

    @Autowired
    private Test1Service test1Service;

    @Autowired
    private Test1SearchRepository test1SearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTest1MockMvc;

    private Test1 test1;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Test1Resource test1Resource = new Test1Resource(test1Service);
        this.restTest1MockMvc = MockMvcBuilders.standaloneSetup(test1Resource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Test1 createEntity(EntityManager em) {
        Test1 test1 = new Test1()
            .prop1(DEFAULT_PROP_1)
            .prop2(DEFAULT_PROP_2);
        return test1;
    }

    @Before
    public void initTest() {
        test1SearchRepository.deleteAll();
        test1 = createEntity(em);
    }

    @Test
    @Transactional
    public void createTest1() throws Exception {
        int databaseSizeBeforeCreate = test1Repository.findAll().size();

        // Create the Test1
        Test1DTO test1DTO = test1Mapper.toDto(test1);
        restTest1MockMvc.perform(post("/api/test-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(test1DTO)))
            .andExpect(status().isCreated());

        // Validate the Test1 in the database
        List<Test1> test1List = test1Repository.findAll();
        assertThat(test1List).hasSize(databaseSizeBeforeCreate + 1);
        Test1 testTest1 = test1List.get(test1List.size() - 1);
        assertThat(testTest1.getProp1()).isEqualTo(DEFAULT_PROP_1);
        assertThat(testTest1.getProp2()).isEqualTo(DEFAULT_PROP_2);

        // Validate the Test1 in Elasticsearch
        Test1 test1Es = test1SearchRepository.findOne(testTest1.getId());
        assertThat(test1Es).isEqualToIgnoringGivenFields(testTest1);
    }

    @Test
    @Transactional
    public void createTest1WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = test1Repository.findAll().size();

        // Create the Test1 with an existing ID
        test1.setId(1L);
        Test1DTO test1DTO = test1Mapper.toDto(test1);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTest1MockMvc.perform(post("/api/test-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(test1DTO)))
            .andExpect(status().isBadRequest());

        // Validate the Test1 in the database
        List<Test1> test1List = test1Repository.findAll();
        assertThat(test1List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkProp1IsRequired() throws Exception {
        int databaseSizeBeforeTest = test1Repository.findAll().size();
        // set the field null
        test1.setProp1(null);

        // Create the Test1, which fails.
        Test1DTO test1DTO = test1Mapper.toDto(test1);

        restTest1MockMvc.perform(post("/api/test-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(test1DTO)))
            .andExpect(status().isBadRequest());

        List<Test1> test1List = test1Repository.findAll();
        assertThat(test1List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTest1S() throws Exception {
        // Initialize the database
        test1Repository.saveAndFlush(test1);

        // Get all the test1List
        restTest1MockMvc.perform(get("/api/test-1-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(test1.getId().intValue())))
            .andExpect(jsonPath("$.[*].prop1").value(hasItem(DEFAULT_PROP_1)))
            .andExpect(jsonPath("$.[*].prop2").value(hasItem(DEFAULT_PROP_2.toString())));
    }

    @Test
    @Transactional
    public void getTest1() throws Exception {
        // Initialize the database
        test1Repository.saveAndFlush(test1);

        // Get the test1
        restTest1MockMvc.perform(get("/api/test-1-s/{id}", test1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(test1.getId().intValue()))
            .andExpect(jsonPath("$.prop1").value(DEFAULT_PROP_1))
            .andExpect(jsonPath("$.prop2").value(DEFAULT_PROP_2.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTest1() throws Exception {
        // Get the test1
        restTest1MockMvc.perform(get("/api/test-1-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTest1() throws Exception {
        // Initialize the database
        test1Repository.saveAndFlush(test1);
        test1SearchRepository.save(test1);
        int databaseSizeBeforeUpdate = test1Repository.findAll().size();

        // Update the test1
        Test1 updatedTest1 = test1Repository.findOne(test1.getId());
        // Disconnect from session so that the updates on updatedTest1 are not directly saved in db
        em.detach(updatedTest1);
        updatedTest1
            .prop1(UPDATED_PROP_1)
            .prop2(UPDATED_PROP_2);
        Test1DTO test1DTO = test1Mapper.toDto(updatedTest1);

        restTest1MockMvc.perform(put("/api/test-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(test1DTO)))
            .andExpect(status().isOk());

        // Validate the Test1 in the database
        List<Test1> test1List = test1Repository.findAll();
        assertThat(test1List).hasSize(databaseSizeBeforeUpdate);
        Test1 testTest1 = test1List.get(test1List.size() - 1);
        assertThat(testTest1.getProp1()).isEqualTo(UPDATED_PROP_1);
        assertThat(testTest1.getProp2()).isEqualTo(UPDATED_PROP_2);

        // Validate the Test1 in Elasticsearch
        Test1 test1Es = test1SearchRepository.findOne(testTest1.getId());
        assertThat(test1Es).isEqualToIgnoringGivenFields(testTest1);
    }

    @Test
    @Transactional
    public void updateNonExistingTest1() throws Exception {
        int databaseSizeBeforeUpdate = test1Repository.findAll().size();

        // Create the Test1
        Test1DTO test1DTO = test1Mapper.toDto(test1);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTest1MockMvc.perform(put("/api/test-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(test1DTO)))
            .andExpect(status().isCreated());

        // Validate the Test1 in the database
        List<Test1> test1List = test1Repository.findAll();
        assertThat(test1List).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTest1() throws Exception {
        // Initialize the database
        test1Repository.saveAndFlush(test1);
        test1SearchRepository.save(test1);
        int databaseSizeBeforeDelete = test1Repository.findAll().size();

        // Get the test1
        restTest1MockMvc.perform(delete("/api/test-1-s/{id}", test1.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean test1ExistsInEs = test1SearchRepository.exists(test1.getId());
        assertThat(test1ExistsInEs).isFalse();

        // Validate the database is empty
        List<Test1> test1List = test1Repository.findAll();
        assertThat(test1List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTest1() throws Exception {
        // Initialize the database
        test1Repository.saveAndFlush(test1);
        test1SearchRepository.save(test1);

        // Search the test1
        restTest1MockMvc.perform(get("/api/_search/test-1-s?query=id:" + test1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(test1.getId().intValue())))
            .andExpect(jsonPath("$.[*].prop1").value(hasItem(DEFAULT_PROP_1)))
            .andExpect(jsonPath("$.[*].prop2").value(hasItem(DEFAULT_PROP_2.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Test1.class);
        Test1 test11 = new Test1();
        test11.setId(1L);
        Test1 test12 = new Test1();
        test12.setId(test11.getId());
        assertThat(test11).isEqualTo(test12);
        test12.setId(2L);
        assertThat(test11).isNotEqualTo(test12);
        test11.setId(null);
        assertThat(test11).isNotEqualTo(test12);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Test1DTO.class);
        Test1DTO test1DTO1 = new Test1DTO();
        test1DTO1.setId(1L);
        Test1DTO test1DTO2 = new Test1DTO();
        assertThat(test1DTO1).isNotEqualTo(test1DTO2);
        test1DTO2.setId(test1DTO1.getId());
        assertThat(test1DTO1).isEqualTo(test1DTO2);
        test1DTO2.setId(2L);
        assertThat(test1DTO1).isNotEqualTo(test1DTO2);
        test1DTO1.setId(null);
        assertThat(test1DTO1).isNotEqualTo(test1DTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(test1Mapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(test1Mapper.fromId(null)).isNull();
    }
}
