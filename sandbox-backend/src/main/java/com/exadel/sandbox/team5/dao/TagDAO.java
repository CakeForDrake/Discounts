package com.exadel.sandbox.team5.dao;

import com.exadel.sandbox.team5.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TagDAO extends JpaRepository<Tag, Long> {
}
