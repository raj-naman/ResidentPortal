package io.mosip.core.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import io.mosip.core.api.response.VINGeneration;

public interface VINRepo extends JpaRepository<VINGeneration, String> {

}
