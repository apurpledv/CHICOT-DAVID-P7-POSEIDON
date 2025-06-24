package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.DBUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<DBUser, Integer>, JpaSpecificationExecutor<DBUser> {
    public DBUser findByUsername(String username);
}
