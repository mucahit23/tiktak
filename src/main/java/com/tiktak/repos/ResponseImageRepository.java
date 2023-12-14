package com.tiktak.repos;

import com.tiktak.entity.ResponseImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseImageRepository extends JpaRepository<ResponseImage, Long> {
}