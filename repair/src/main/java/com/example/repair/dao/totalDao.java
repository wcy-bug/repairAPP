package com.example.repair.dao;

import com.example.repair.entity.RepairMan;
import com.example.repair.entity.totalCount;
import org.hibernate.sql.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface totalDao extends JpaRepository<totalCount,Long> {

    @Query("Select t from totalCount t where t.valid=1")
    Page<totalCount> findByQuery(Pageable pageable);

    totalCount findTotalCountById(Long id);

}