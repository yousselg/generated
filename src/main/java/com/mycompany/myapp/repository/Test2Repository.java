package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Test2;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Test2 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Test2Repository extends JpaRepository<Test2, Long> {

}
