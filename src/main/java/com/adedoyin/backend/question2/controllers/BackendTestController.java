package com.adedoyin.backend.question2.controllers;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adedoyin.backend.question2.dao.CardSchemeDao;
import com.adedoyin.backend.question2.dao.ResponseHandler;
import com.adedoyin.backend.question2.models.CardScheme;


@RestController
public class BackendTestController {

	@Autowired
	private CardSchemeDao cardSchemeDao;

	@GetMapping("/")
	String sayHello() {

		return "Hello 3Line! from Adedoyin Daniel";
	}
	
	@GetMapping("/authenticate")
	public ResponseEntity<String> verifyHash(
			@RequestHeader("authorization") String authorization,
			@RequestHeader("timeStamp") String timestamp,
			@RequestHeader("appKey") String appKey){
		
		String authMsg="";

		//check auth hash
		String clientHash = authorization.split(" ")[1];
		String serverHash = this.sha512(appKey+timestamp);

        if (Objects.isNull(serverHash)) {
            //throw new InvalidRequestException("Invalid Authorization Header, invalid Hash");
        	authMsg = "Invalid Request, Check credentials";
        	return new ResponseEntity<String>(authMsg, HttpStatus.BAD_REQUEST);

        }
        
        if(!serverHash.equals(clientHash)) {
        	authMsg = "Invalid authorization key";
    	    return new ResponseEntity<String>(authMsg, HttpStatus.UNAUTHORIZED);
        }
        authMsg = "Authenticated";
	    return new ResponseEntity<String>(authMsg, HttpStatus.OK);
		
	}

	// get details of a card number
	// card-scheme/verify/card-number
	@GetMapping("/card-scheme/verify/{number}")
	public ResponseEntity<Object> cardScheme(@PathVariable("number") String cardNumber) {

		Optional<CardScheme> thisCard = cardSchemeDao.getCardDetails(cardNumber);

		if (thisCard.isPresent()) {
			CardScheme card = thisCard.get();
			cardSchemeDao.updateCount(card);
			return ResponseHandler.getCardDetails(HttpStatus.OK, true, thisCard);
		}
		return ResponseHandler.getCardDetails(HttpStatus.NOT_FOUND, false, thisCard);
	}

	// get statistics of limited number of cards based on their searches
	// card-scheme/stats?start={start}&limit={limit}
	@GetMapping("/card-scheme/stats")
	public ResponseEntity<Object> stats(@RequestParam String start, @RequestParam String limit) {
		int startNo, limitNo;
		try {
			startNo = Integer.parseInt(start);
			limitNo = Integer.parseInt(limit);
		} catch (Exception e) {
			return ResponseHandler.invalidEntry(HttpStatus.BAD_REQUEST);
		}

		List<CardScheme> retrievedCards = cardSchemeDao.getCardsByOffset(limitNo, startNo);
		long size = (int) cardSchemeDao.getTotalCardsCounts();

		return ResponseHandler.getStats(HttpStatus.OK, true, startNo, limitNo, size, retrievedCards);

	}

	// get statistics of all cards based on their searches
	// card-scheme/stats/
	@GetMapping("/card-scheme/stats/")
	public ResponseEntity<Object> all() {

		Iterable<CardScheme> retrievedCards = cardSchemeDao.getAllCards();
		long size = (int) cardSchemeDao.getTotalCardsCounts();

		return ResponseHandler.getStats(HttpStatus.OK, true, 1, 0, size, (List<CardScheme>) retrievedCards);

	}
	
	
	public String sha512(String args) {

        try {
            // Create MessageDigest instance for MD5
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-512");
            //Add password bytes to digest
            md.update(args.getBytes(StandardCharsets.UTF_8));
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format

            return java.util.Base64.getEncoder().encodeToString(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }	
	

}
