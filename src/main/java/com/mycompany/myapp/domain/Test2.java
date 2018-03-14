package com.mycompany.myapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Test2.
 */
@Entity
@Table(name = "test_2")
@Document(indexName = "test2")
public class Test2 implements Serializable {

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

    @ManyToOne
    private Test1 test1;

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

    public Test2 prop1(Integer prop1) {
        this.prop1 = prop1;
        return this;
    }

    public void setProp1(Integer prop1) {
        this.prop1 = prop1;
    }

    public String getProp2() {
        return prop2;
    }

    public Test2 prop2(String prop2) {
        this.prop2 = prop2;
        return this;
    }

    public void setProp2(String prop2) {
        this.prop2 = prop2;
    }

    public Test1 getTest1() {
        return test1;
    }

    public Test2 test1(Test1 test1) {
        this.test1 = test1;
        return this;
    }

    public void setTest1(Test1 test1) {
        this.test1 = test1;
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
        Test2 test2 = (Test2) o;
        if (test2.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), test2.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Test2{" +
            "id=" + getId() +
            ", prop1=" + getProp1() +
            ", prop2='" + getProp2() + "'" +
            "}";
    }
}
