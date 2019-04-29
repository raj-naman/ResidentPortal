package io.mosip.core.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.mosip.core.api.model.Resident;

public interface ResidentRepo extends JpaRepository<Resident, String> {
}
