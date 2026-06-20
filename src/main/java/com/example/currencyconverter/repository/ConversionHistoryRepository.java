package com.example.currencyconverter.repository;

import com.example.currencyconverter.entity.ConversionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversionHistoryRepository extends JpaRepository<ConversionHistory, Long> {

    List<ConversionHistory> findByApiKeyOwnerOrderByCreatedAtDesc(String apiKeyOwner);
}
