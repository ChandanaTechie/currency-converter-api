package com.currency.converter.repository;

import com.currency.converter.entity.ConversionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversionHistoryRepository extends JpaRepository<ConversionHistory, Long> {

    List<ConversionHistory> findByApiKeyOwnerOrderByCreatedAtDesc(String apiKeyOwner);
}
