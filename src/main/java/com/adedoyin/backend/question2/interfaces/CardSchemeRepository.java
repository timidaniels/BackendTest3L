package com.adedoyin.backend.question2.interfaces;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.adedoyin.backend.question2.models.CardScheme;

@Repository
public interface CardSchemeRepository extends CrudRepository<CardScheme, Integer> {
	@Query(value = "select * from CardScheme LIMIT ?1 OFFSET ?2", nativeQuery = true)
	List<CardScheme> findCardWithinLimit(int limit, int offset);

	@Query(value = "select * from CardScheme c where c.cardNo = ?1", nativeQuery = true)
	Optional<CardScheme> findByCardNumber(String cardNumber);
}
