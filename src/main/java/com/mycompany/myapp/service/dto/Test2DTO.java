package com.mycompany.myapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Test2 entity.
 */
public class Test2DTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    private Integer prop1;

    private String prop2;

    private Long test1Id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProp1() {
        return prop1;
    }

    public void setProp1(Integer prop1) {
        this.prop1 = prop1;
    }

    public String getProp2() {
        return prop2;
    }

    public void setProp2(String prop2) {
        this.prop2 = prop2;
    }

    public Long getTest1Id() {
        return test1Id;
    }

    public void setTest1Id(Long test1Id) {
        this.test1Id = test1Id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Test2DTO test2DTO = (Test2DTO) o;
        if(test2DTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), test2DTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Test2DTO{" +
            "id=" + getId() +
            ", prop1=" + getProp1() +
            ", prop2='" + getProp2() + "'" +
            "}";
    }
}
