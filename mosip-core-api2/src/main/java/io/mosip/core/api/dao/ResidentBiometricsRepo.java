package io.mosip.core.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import io.mosip.core.api.model.ResidentBiometrics;

public interface ResidentBiometricsRepo extends JpaRepository<ResidentBiometrics, String> {

}
