package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.Test1DTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Test1 and its DTO Test1DTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Test1Mapper extends EntityMapper<Test1DTO, Test1> {


    @Mapping(target = "test2S", ignore = true)
    Test1 toEntity(Test1DTO test1DTO);

    default Test1 fromId(Long id) {
        if (id == null) {
            return null;
        }
        Test1 test1 = new Test1();
        test1.setId(id);
        return test1;
    }
}
