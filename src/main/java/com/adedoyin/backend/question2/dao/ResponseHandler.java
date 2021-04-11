package com.adedoyin.backend.question2.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.adedoyin.backend.question2.models.CardScheme;

public class ResponseHandler {
	public static ResponseEntity<Object> getCardDetails(HttpStatus status, boolean success,
			Optional<CardScheme> responseObj) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> payload = new HashMap<String, String>();

		try {

			map.put("success", success);

			if (!success) {
				map.put("status", status.value());
			}

			// create payload values
			if (responseObj.isPresent()) {
				payload.put("scheme", responseObj.get().getScheme());
				payload.put("type", responseObj.get().getType());
				String bank = responseObj.get().getBank();
				// check if bank is valid
				if (!bank.equals("")) {
					payload.put("bank", bank);
				}
				map.put("payload", payload);
			}

			return new ResponseEntity<Object>(map, status);
		} catch (Exception e) {
			map.clear();
			map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("success", false);
			map.put("message", e.getMessage());
			map.put("payload", null);
			return new ResponseEntity<Object>(map, status);
		}
	}

	public static ResponseEntity<Object> getStats(HttpStatus status, boolean success, int start, int limit, long size,
			List<CardScheme> responseObj) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Integer> payload = new HashMap<String, Integer>();

		try {
			map.put("success", success);
			map.put("start", start);
			map.put("limit", limit);
			map.put("size", size);

			// create payload values
			if (!responseObj.isEmpty()) {
				for (int i = 0; i < responseObj.size(); i++) {
					CardScheme currentObj = responseObj.get(i);
					payload.put(currentObj.getCardNo(), currentObj.getCount());
				}
				map.put("payload", payload);

			}

			return new ResponseEntity<Object>(map, status);
		} catch (Exception e) {
			map.clear();
			map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			map.put("success", false);
			map.put("message", e.getMessage());
			map.put("payload", null);
			return new ResponseEntity<Object>(map, status);
		}
	}

	public static ResponseEntity<Object> invalidEntry(HttpStatus status) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("Message", "Invalid Parameters");
		map.put("status", status.value());

		return new ResponseEntity<Object>(map, status);

	}
}
