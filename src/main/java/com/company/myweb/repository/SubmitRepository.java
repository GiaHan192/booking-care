package com.company.myweb.repository;

import com.company.myweb.entity.Submition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmitRepository extends JpaRepository<Submition, Long> {
}
