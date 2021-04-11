package com.adedoyin.backend.question2.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adedoyin.backend.question2.interfaces.CardSchemeRepository;
import com.adedoyin.backend.question2.models.CardScheme;

@Service
public class CardSchemeDao {

	@Autowired
	private CardSchemeRepository cardSchemeRepository;

	public Optional<CardScheme> getCardDetails(String cardNo) {
		return cardSchemeRepository.findByCardNumber(cardNo);
	}

	public int getCurrentCount(CardScheme card) {
		return card.getCount();
	}
	
	public void updateCount(CardScheme card) {
		// get current card hit counts
		int currentCount = getCurrentCount(card);
		int newNo = currentCount + 1;
		card.setCount(newNo);
		save(card);
	}

	//get cards based on specified constraints
	public List<CardScheme> getCardsByOffset(int limit, int start) {
		int offset = start - 1;
		if (limit < 0 && start > 0) {
			return cardSchemeRepository.findCardWithinLimit(1, offset);
		} else if (limit > 0 && start < 0) {
			return cardSchemeRepository.findCardWithinLimit(limit, 0);
		} else if (limit > 0 && start > 0) {
			return cardSchemeRepository.findCardWithinLimit(limit, offset);
		} else {
			return cardSchemeRepository.findCardWithinLimit(1, 0);
		}
	}

	public long getTotalCardsCounts() {
		return cardSchemeRepository.count();
	}
	
	public Iterable<CardScheme> getAllCards() {
		return cardSchemeRepository.findAll();
	}
	
	public CardScheme save(CardScheme card) {
		return cardSchemeRepository.save(card);
	}
}
