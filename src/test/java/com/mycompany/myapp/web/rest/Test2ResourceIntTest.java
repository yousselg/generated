package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhispterApp;

import com.mycompany.myapp.domain.Test2;
import com.mycompany.myapp.repository.Test2Repository;
import com.mycompany.myapp.service.Test2Service;
import com.mycompany.myapp.repository.search.Test2SearchRepository;
import com.mycompany.myapp.service.dto.Test2DTO;
import com.mycompany.myapp.service.mapper.Test2Mapper;
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
 * Test class for the Test2Resource REST controller.
 *
 * @see Test2Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhispterApp.class)
public class Test2ResourceIntTest {

    private static final Integer DEFAULT_PROP_1 = 0;
    private static final Integer UPDATED_PROP_1 = 1;

    private static final String DEFAULT_PROP_2 = "AAAAAAAAAA";
    private static final String UPDATED_PROP_2 = "BBBBBBBBBB";

    @Autowired
    private Test2Repository test2Repository;

    @Autowired
    private Test2Mapper test2Mapper;

    @Autowired
    private Test2Service test2Service;

    @Autowired
    private Test2SearchRepository test2SearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTest2MockMvc;

    private Test2 test2;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Test2Resource test2Resource = new Test2Resource(test2Service);
        this.restTest2MockMvc = MockMvcBuilders.standaloneSetup(test2Resource)
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
    public static Test2 createEntity(EntityManager em) {
        Test2 test2 = new Test2()
            .prop1(DEFAULT_PROP_1)
            .prop2(DEFAULT_PROP_2);
        return test2;
    }

    @Before
    public void initTest() {
        test2SearchRepository.deleteAll();
        test2 = createEntity(em);
    }

    @Test
    @Transactional
    public void createTest2() throws Exception {
        int databaseSizeBeforeCreate = test2Repository.findAll().size();

        // Create the Test2
        Test2DTO test2DTO = test2Mapper.toDto(test2);
        restTest2MockMvc.perform(post("/api/test-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(test2DTO)))
            .andExpect(status().isCreated());

        // Validate the Test2 in the database
        List<Test2> test2List = test2Repository.findAll();
        assertThat(test2List).hasSize(databaseSizeBeforeCreate + 1);
        Test2 testTest2 = test2List.get(test2List.size() - 1);
        assertThat(testTest2.getProp1()).isEqualTo(DEFAULT_PROP_1);
        assertThat(testTest2.getProp2()).isEqualTo(DEFAULT_PROP_2);

        // Validate the Test2 in Elasticsearch
        Test2 test2Es = test2SearchRepository.findOne(testTest2.getId());
        assertThat(test2Es).isEqualToIgnoringGivenFields(testTest2);
    }

    @Test
    @Transactional
    public void createTest2WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = test2Repository.findAll().size();

        // Create the Test2 with an existing ID
        test2.setId(1L);
        Test2DTO test2DTO = test2Mapper.toDto(test2);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTest2MockMvc.perform(post("/api/test-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(test2DTO)))
            .andExpect(status().isBadRequest());

        // Validate the Test2 in the database
        List<Test2> test2List = test2Repository.findAll();
        assertThat(test2List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkProp1IsRequired() throws Exception {
        int databaseSizeBeforeTest = test2Repository.findAll().size();
        // set the field null
        test2.setProp1(null);

        // Create the Test2, which fails.
        Test2DTO test2DTO = test2Mapper.toDto(test2);

        restTest2MockMvc.perform(post("/api/test-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(test2DTO)))
            .andExpect(status().isBadRequest());

        List<Test2> test2List = test2Repository.findAll();
        assertThat(test2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTest2S() throws Exception {
        // Initialize the database
        test2Repository.saveAndFlush(test2);

        // Get all the test2List
        restTest2MockMvc.perform(get("/api/test-2-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(test2.getId().intValue())))
            .andExpect(jsonPath("$.[*].prop1").value(hasItem(DEFAULT_PROP_1)))
            .andExpect(jsonPath("$.[*].prop2").value(hasItem(DEFAULT_PROP_2.toString())));
    }

    @Test
    @Transactional
    public void getTest2() throws Exception {
        // Initialize the database
        test2Repository.saveAndFlush(test2);

        // Get the test2
        restTest2MockMvc.perform(get("/api/test-2-s/{id}", test2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(test2.getId().intValue()))
            .andExpect(jsonPath("$.prop1").value(DEFAULT_PROP_1))
            .andExpect(jsonPath("$.prop2").value(DEFAULT_PROP_2.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTest2() throws Exception {
        // Get the test2
        restTest2MockMvc.perform(get("/api/test-2-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTest2() throws Exception {
        // Initialize the database
        test2Repository.saveAndFlush(test2);
        test2SearchRepository.save(test2);
        int databaseSizeBeforeUpdate = test2Repository.findAll().size();

        // Update the test2
        Test2 updatedTest2 = test2Repository.findOne(test2.getId());
        // Disconnect from session so that the updates on updatedTest2 are not directly saved in db
        em.detach(updatedTest2);
        updatedTest2
            .prop1(UPDATED_PROP_1)
            .prop2(UPDATED_PROP_2);
        Test2DTO test2DTO = test2Mapper.toDto(updatedTest2);

        restTest2MockMvc.perform(put("/api/test-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(test2DTO)))
            .andExpect(status().isOk());

        // Validate the Test2 in the database
        List<Test2> test2List = test2Repository.findAll();
        assertThat(test2List).hasSize(databaseSizeBeforeUpdate);
        Test2 testTest2 = test2List.get(test2List.size() - 1);
        assertThat(testTest2.getProp1()).isEqualTo(UPDATED_PROP_1);
        assertThat(testTest2.getProp2()).isEqualTo(UPDATED_PROP_2);

        // Validate the Test2 in Elasticsearch
        Test2 test2Es = test2SearchRepository.findOne(testTest2.getId());
        assertThat(test2Es).isEqualToIgnoringGivenFields(testTest2);
    }

    @Test
    @Transactional
    public void updateNonExistingTest2() throws Exception {
        int databaseSizeBeforeUpdate = test2Repository.findAll().size();

        // Create the Test2
        Test2DTO test2DTO = test2Mapper.toDto(test2);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTest2MockMvc.perform(put("/api/test-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(test2DTO)))
            .andExpect(status().isCreated());

        // Validate the Test2 in the database
        List<Test2> test2List = test2Repository.findAll();
        assertThat(test2List).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTest2() throws Exception {
        // Initialize the database
        test2Repository.saveAndFlush(test2);
        test2SearchRepository.save(test2);
        int databaseSizeBeforeDelete = test2Repository.findAll().size();

        // Get the test2
        restTest2MockMvc.perform(delete("/api/test-2-s/{id}", test2.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean test2ExistsInEs = test2SearchRepository.exists(test2.getId());
        assertThat(test2ExistsInEs).isFalse();

        // Validate the database is empty
        List<Test2> test2List = test2Repository.findAll();
        assertThat(test2List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTest2() throws Exception {
        // Initialize the database
        test2Repository.saveAndFlush(test2);
        test2SearchRepository.save(test2);

        // Search the test2
        restTest2MockMvc.perform(get("/api/_search/test-2-s?query=id:" + test2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(test2.getId().intValue())))
            .andExpect(jsonPath("$.[*].prop1").value(hasItem(DEFAULT_PROP_1)))
            .andExpect(jsonPath("$.[*].prop2").value(hasItem(DEFAULT_PROP_2.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Test2.class);
        Test2 test21 = new Test2();
        test21.setId(1L);
        Test2 test22 = new Test2();
        test22.setId(test21.getId());
        assertThat(test21).isEqualTo(test22);
        test22.setId(2L);
        assertThat(test21).isNotEqualTo(test22);
        test21.setId(null);
        assertThat(test21).isNotEqualTo(test22);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Test2DTO.class);
        Test2DTO test2DTO1 = new Test2DTO();
        test2DTO1.setId(1L);
        Test2DTO test2DTO2 = new Test2DTO();
        assertThat(test2DTO1).isNotEqualTo(test2DTO2);
        test2DTO2.setId(test2DTO1.getId());
        assertThat(test2DTO1).isEqualTo(test2DTO2);
        test2DTO2.setId(2L);
        assertThat(test2DTO1).isNotEqualTo(test2DTO2);
        test2DTO1.setId(null);
        assertThat(test2DTO1).isNotEqualTo(test2DTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(test2Mapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(test2Mapper.fromId(null)).isNull();
    }
}
