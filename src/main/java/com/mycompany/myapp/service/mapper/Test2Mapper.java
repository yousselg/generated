package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.Test2DTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Test2 and its DTO Test2DTO.
 */
@Mapper(componentModel = "spring", uses = {Test1Mapper.class})
public interface Test2Mapper extends EntityMapper<Test2DTO, Test2> {

    @Mapping(source = "test1.id", target = "test1Id")
    Test2DTO toDto(Test2 test2);

    @Mapping(source = "test1Id", target = "test1")
    Test2 toEntity(Test2DTO test2DTO);

    default Test2 fromId(Long id) {
        if (id == null) {
            return null;
        }
        Test2 test2 = new Test2();
        test2.setId(id);
        return test2;
    }
}
