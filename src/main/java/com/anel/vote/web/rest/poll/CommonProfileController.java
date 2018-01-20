package com.anel.vote.web.rest.poll;

import com.anel.vote.model.Restaurant;
import com.anel.vote.service.RestaurantService;
import com.anel.vote.AuthorizedUser;
import com.anel.vote.service.VoteService;
import com.anel.vote.to.ResultTo;
import com.anel.vote.to.VoteTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by PeucT on 14.01.2018.
 */
@RestController
@RequestMapping(CommonProfileController.REST_URL)
public class CommonProfileController {
    static final String REST_URL = "/rest/v1/profile";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private VoteService voteService;


    private static LocalDate getDate(LocalDate date){
        if (date == null) {
            date = LocalDate.now();
        }
        return date;
    }

    @GetMapping(value = "/results", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<ResultTo>> getResults(@AuthenticationPrincipal AuthorizedUser authorizedUser,
                                                      @RequestParam(value = "date", required = false) LocalDate date){
        int authUserId = authorizedUser.getUserTo().getId();
        log.info("getResults for user {} for date {}", authUserId, date);

        return new ResponseEntity<>(voteService.getResults(getDate(date)), HttpStatus.OK);
    }

    @GetMapping(value = "/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<Restaurant>> getAll(@AuthenticationPrincipal AuthorizedUser authorizedUser,
                                                    @RequestParam(value = "date", required = false) LocalDate date){
        int authUserId = authorizedUser.getUserTo().getId();
        log.info("getAll for user {} for date {}", authUserId, date);

        return new ResponseEntity<>(restaurantService.getAll(getDate(date)), HttpStatus.OK);
    }

    @GetMapping(value = "/restaurants/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<Restaurant>> getAllForId(@AuthenticationPrincipal AuthorizedUser authorizedUser,
                                                         @PathVariable("id") int id,
                                                         @RequestParam(value = "date", required = false) LocalDate date){
        int authUserId = authorizedUser.getUserTo().getId();
        log.info("getAll for user {} for date {} from restaurant {}", authUserId,date, id);
        return new ResponseEntity<>(restaurantService.getAllForId(getDate(date), id), HttpStatus.OK);
    }

    @GetMapping(value = "/vote", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<VoteTo> getMyVote(
            @AuthenticationPrincipal AuthorizedUser authorizedUser,
            @RequestParam(value = "date", required = false) LocalDate date){

        int authUserId = authorizedUser.getUserTo().getId();
        log.info("getMyVote for user {} for date {}", authUserId,date);
        return new ResponseEntity<>(voteService.getMyVote(getDate(date), authUserId),HttpStatus.OK);
    }

    @DeleteMapping(value = "/vote")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteVote(@AuthenticationPrincipal AuthorizedUser authorizedUser) {
        int authUserId = authorizedUser.getUserTo().getId();
        log.info("deleteVote for user {}", authUserId);
        voteService.delete(authUserId, LocalDateTime.now());
    }

    @PostMapping(value = "/vote", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTo> saveVote(@AuthenticationPrincipal AuthorizedUser authorizedUser,
                                           @Valid @RequestBody VoteTo voteTo) {
        int authUserId = authorizedUser.getUserTo().getId();
        log.info("saveVote for user {}", authUserId);

        VoteTo created = voteService.saveOrUpdate(authUserId, LocalDateTime.now(), voteTo);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/vote")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }


    @PutMapping(value = "/vote/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTo> updateVote(@AuthenticationPrincipal AuthorizedUser authorizedUser,
                                             @Valid @RequestBody VoteTo voteTo,
                                             @PathVariable("id") int voteId) {
        int authUserId = authorizedUser.getUserTo().getId();
        log.info("updateVote for user {} with id {}", authUserId, voteId);
        voteTo.setId(voteId);

        return new ResponseEntity<>(voteService.saveOrUpdate(authUserId, LocalDateTime.now(), voteTo), HttpStatus.OK);
    }
}
