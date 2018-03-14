package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Test1.
 */
@Entity
@Table(name = "test_1")
@Document(indexName = "test1")
public class Test1 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    @Column(name = "prop_1", nullable = false)
    private Integer prop1;

    @Column(name = "prop_2")
    private String prop2;

    @OneToMany(mappedBy = "test1")
    @JsonIgnore
    private Set<Test2> test2S = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProp1() {
        return prop1;
    }

    public Test1 prop1(Integer prop1) {
        this.prop1 = prop1;
        return this;
    }

    public void setProp1(Integer prop1) {
        this.prop1 = prop1;
    }

    public String getProp2() {
        return prop2;
    }

    public Test1 prop2(String prop2) {
        this.prop2 = prop2;
        return this;
    }

    public void setProp2(String prop2) {
        this.prop2 = prop2;
    }

    public Set<Test2> getTest2S() {
        return test2S;
    }

    public Test1 test2S(Set<Test2> test2S) {
        this.test2S = test2S;
        return this;
    }

    public Test1 addTest2(Test2 test2) {
        this.test2S.add(test2);
        test2.setTest1(this);
        return this;
    }

    public Test1 removeTest2(Test2 test2) {
        this.test2S.remove(test2);
        test2.setTest1(null);
        return this;
    }

    public void setTest2S(Set<Test2> test2S) {
        this.test2S = test2S;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Test1 test1 = (Test1) o;
        if (test1.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), test1.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Test1{" +
            "id=" + getId() +
            ", prop1=" + getProp1() +
            ", prop2='" + getProp2() + "'" +
            "}";
    }
}
