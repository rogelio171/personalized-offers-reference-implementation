package com.mastercard.developer.controller;

import com.mastercard.api.model.AdminOfferFilter;
import com.mastercard.api.model.OfferFilter;
import com.mastercard.api.model.ResponseWrapperUserSavingsOutputWrapper;
import com.mastercard.api.model.ResponseWrapperUserTokenOutputWrapper;
import com.mastercard.api.model.UserSavings;
import com.mastercard.developer.service.domain.GenericOffersCriterion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mastercard.ApiException;
import com.mastercard.api.model.UserAccessToken;
import com.mastercard.api.model.ActivateSCOfferInputStatementCreditOfferActivation;
import com.mastercard.api.model.Activations;
import com.mastercard.api.model.BrowseOffers;
import com.mastercard.api.model.OfferDetails;
import com.mastercard.api.model.RequestedAccessToken;
import com.mastercard.api.model.RequestedActivation;
import com.mastercard.api.model.RequestedOfferRating;
import com.mastercard.api.model.ResponseWrapperDetailedOffersResponseDetailedOffers;
import com.mastercard.api.model.ResponseWrapperDetailedRedeemedOfferListResponseRedeemedOffers;
import com.mastercard.api.model.ResponseWrapperMatchedOfferDetailsResponseMatchedOffers;
import com.mastercard.api.model.ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivation;
import com.mastercard.api.model.ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivationDetail;
import com.mastercard.api.model.ResponseWrapperUserFeedbackOutputList;
import com.mastercard.api.model.ResponseWrapperUserFeedbackOutputWrapper;
import com.mastercard.api.model.UserAdjustment;
import com.mastercard.api.model.UserAdjustments;
import com.mastercard.api.model.UserFeedbackInput;
import com.mastercard.api.model.UserOfferRating;
import com.mastercard.api.model.UserOfferRatings;
import com.mastercard.api.model.UserOffers;
import com.mastercard.developer.service.PersonalizedOffersService;

import java.util.List;

/**
 * This controller is used to execute use-cases by REST API based Client such as Insomnia or Postman.
 */
@RestController
public class PersonalizedOffersController {

  private final PersonalizedOffersService referenceApplicationGateway;

  public PersonalizedOffersController(final PersonalizedOffersService referenceApplicationGateway) {
    this.referenceApplicationGateway = referenceApplicationGateway;
  }

  @GetMapping("/redeem-offers")
  public ResponseEntity<ResponseWrapperDetailedRedeemedOfferListResponseRedeemedOffers>
      redeemOffers(@RequestParam("f_id") final String fId) throws ApiException {
    return ResponseEntity.ok(referenceApplicationGateway.getRedeemedOffers(fId));
  }

  @PostMapping("/activate-offers")
  public ResponseEntity<
          ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivation>
      activateOffer(
          @RequestBody
              final ActivateSCOfferInputStatementCreditOfferActivation
                  statementCreditOfferActivation)
          throws ApiException {
    return ResponseEntity.ok(
        referenceApplicationGateway.activateStatementCreditOffer(statementCreditOfferActivation));
  }

  @GetMapping("/activation-details")
  public ResponseEntity<
          ResponseWrapperStatementCreditOfferDetailsResponseStatementCreditOfferActivationDetail>
      activationDetails(
          @RequestParam("f_id") final String fId,
          @RequestParam("activation_id") final String activationId)
          throws ApiException {
    return ResponseEntity.ok(
        referenceApplicationGateway.getStatementCreditActivationDetail(fId, activationId));
  }

  @PostMapping("/user-feedbacks")
  public ResponseEntity<ResponseWrapperUserFeedbackOutputWrapper> sendUserFeedback(
      @RequestBody final UserFeedbackInput userFeedback) throws ApiException {
    return ResponseEntity.ok(referenceApplicationGateway.sendUserFeedback(userFeedback));
  }

  @GetMapping("/user-feedbacks")
  public ResponseEntity<ResponseWrapperUserFeedbackOutputList> userFeedbacks(
      @RequestParam("f_id") final String fId) throws ApiException {
    return ResponseEntity.ok(referenceApplicationGateway.getUserFeedback(fId));
  }

  @GetMapping("/matched-offers")
  public ResponseEntity<ResponseWrapperMatchedOfferDetailsResponseMatchedOffers> matchedOffers(
      @RequestParam("f_id") final String fId) throws ApiException {
    return ResponseEntity.ok(referenceApplicationGateway.getMatchedOffers(fId));
  }

  @GetMapping("/user-savings")
  public ResponseEntity<ResponseWrapperUserSavingsOutputWrapper> userSavings(
      @RequestParam("f_id") final String fId) throws ApiException {
    return ResponseEntity.ok(referenceApplicationGateway.getUserSavings(fId));
  }

  @GetMapping("/user-tokens")
  public ResponseEntity<ResponseWrapperUserTokenOutputWrapper> userTokens(
      @RequestParam("f_id") final String fId) throws ApiException {
    return ResponseEntity.ok(referenceApplicationGateway.getUserToken(fId));
  }

  @GetMapping("/offer-details")
  public ResponseEntity<ResponseWrapperDetailedOffersResponseDetailedOffers> offerDetails(
      @RequestParam("f_id") final String fId, @RequestParam("offer_id") final String offerId)
      throws ApiException {
    return ResponseEntity.ok(referenceApplicationGateway.getOfferDetails(fId, offerId));
  }

  @GetMapping("/offers")
  public ResponseEntity<BrowseOffers> offers(@RequestParam("f_id") final String fId)
      throws ApiException {
    return ResponseEntity.ok(referenceApplicationGateway.getOffers(fId));
  }

  @GetMapping("/adjustments")
  public ResponseEntity<UserAdjustment> adjustmentDetails(
      @RequestHeader(name = "X-Openapi-Clientid", defaultValue = "") final String clientId,
      @RequestParam(name = "f_id") final String fId,
      @RequestParam(required = false, name = "offset") final Integer offset,
      @RequestParam(required = false, name = "limit") final Integer limit,
      @RequestParam(required = false, name = "start_date") final String startDate,
      @RequestParam(required = false, name = "end_date") final String endDate,
      @RequestParam(required = false, name = "date_filter") final String dateFilter)
      throws ApiException {
    return ResponseEntity.ok(
        referenceApplicationGateway.getAdjustments(clientId,
            fId, offset, limit, startDate, endDate, dateFilter));
  }

  @PostMapping("/user-presentment/access-tokens")
  public UserAccessToken createAccessToken(@RequestBody RequestedAccessToken accessToken)
      throws ApiException {
    return referenceApplicationGateway.getToken(accessToken);
  }

  @GetMapping("/user-presentment/offers")
  public UserOffers getOffers(
      @RequestHeader(name = "Accept-Language", defaultValue = "", required = false)
          final String language,
      @RequestParam(value = "offer_type", required = false) final String offerType,
      @RequestParam(value = "category", required = false) final String category,
      @RequestParam(value = "offer_country", required = false) final String offerCountry,
      @RequestParam(value = "active", required = false) final Boolean active,
      @RequestParam(value = "offset", defaultValue = "0", required = false) final Integer offset,
      @RequestParam(value = "limit", defaultValue = "10", required = false) final Integer limit,
      @RequestHeader(name = "x-auth-token") final String xAuthToken)
      throws ApiException {
    return referenceApplicationGateway.getOffers(
        language, offerType, category, offerCountry, active, offset, limit, xAuthToken);
  }

  @PostMapping("/user-presentment/offers/filters")
  public UserOffers filterUserOffers(
      @RequestHeader(name = "Accept-Language", defaultValue = "", required = false) final String language,
      @RequestBody OfferFilter offerFilter,
      @RequestHeader(name = "x-auth-token") final String xAuthToken,
      @RequestHeader(name = "X-Openapi-Clientid", defaultValue = "") final String clientId)
      throws ApiException {
    return referenceApplicationGateway.filterOffers(xAuthToken, offerFilter, language, clientId);
  }

  @GetMapping("/user-presentment/offers/{offer_id}")
  public OfferDetails getOfferDetails(
      @PathVariable(value = "offer_id") final String offerId,
      @RequestHeader(name = "Accept-Language", defaultValue = "", required = false)
          final String language,
      @RequestHeader(name = "x-auth-token") final String xAuthToken)
      throws ApiException {
    return referenceApplicationGateway.getOfferDetails(offerId, xAuthToken, language);
  }

  @GetMapping("/user-presentment/offer-ratings")
  public UserOfferRatings getOfferRatings(
      @RequestParam(value = "current", defaultValue = "true", required = false)
          final String current,
      @RequestParam(value = "offset", defaultValue = "0", required = false) final Integer offset,
      @RequestParam(value = "limit", defaultValue = "10", required = false) final Integer limit,
      @RequestHeader(name = "x-auth-token") final String xAuthToken)
      throws ApiException {
    return referenceApplicationGateway.getOfferRatings(current, offset, limit, xAuthToken);
  }

  @GetMapping("/user-presentment/offer-ratings/{offer_id}")
  public UserOfferRating getOfferRating(
      @PathVariable(value = "offer_id") final String offerId,
      @RequestHeader(name = "x-auth-token") final String xAuthToken)
      throws ApiException {
    return referenceApplicationGateway.getOfferRating(offerId, xAuthToken);
  }

  @PostMapping("/user-presentment/offer-ratings/{offer_id}/likes")
  public UserOfferRating createOfferRating(
      @PathVariable(value = "offer_id") final String offerId,
      @RequestBody RequestedOfferRating offerRatingRequest,
      @RequestHeader(name = "x-auth-token") final String xAuthToken)
      throws ApiException {
    return referenceApplicationGateway.createOfferRating(offerId, offerRatingRequest, xAuthToken);
  }

  @PostMapping("/user-presentment/activations")
  public Activations processActivations(
      @RequestBody RequestedActivation requestedActivation,
      @RequestHeader(name = "x-auth-token") final String xAuthToken)
      throws ApiException {
    return referenceApplicationGateway.processActivations(requestedActivation, xAuthToken);
  }

  @GetMapping("/user-presentment/savings")
  public UserSavings getUserPresentmentSavings(
      @RequestHeader(name = "x-auth-token") final String xAuthToken) throws ApiException {
    return referenceApplicationGateway.getUserPresentmentSavings(xAuthToken);
  }

  @GetMapping("/user-presentment/adjustments")
  public UserAdjustments getUserPresentmentAdjustments(
      @RequestParam(value = "start_date", required = false) final String startDate,
      @RequestParam(value = "end_date", required = false) final String endDate,
      @RequestParam(value = "date_filter", required = false) final String dateFilter,
      @RequestParam(value = "offset", defaultValue = "0", required = false) final Integer offset,
      @RequestParam(value = "limit", defaultValue = "10", required = false) final Integer limit,
      @RequestHeader(name = "x-auth-token") final String xAuthToken)
      throws ApiException {
    return referenceApplicationGateway.getUserPresentmentAdjustments(
        startDate, endDate, dateFilter, offset, limit, xAuthToken);
  }

  @GetMapping("/platform-admin/offers")
  public BrowseOffers getOffers(
      @RequestHeader(name = "X-Openapi-Clientid", defaultValue = "") final String clientId,
      @RequestParam(value = "f_id") final String fiName,
      @RequestParam(value = "issuer_ica", required = false) final Integer issuerIca,
      @RequestParam(value = "bank_product_code", required = false) final String bankProductCode,
      @RequestParam(value = "offer_type", required = false) final String offerType,
      @RequestParam(value = "category", required = false) final String category,
      @RequestParam(value = "offer_country", required = false) final List<String> offerCountries,
      @RequestParam(value = "lang", required = false) final List<String> languages,
      @RequestParam(value = "offset", defaultValue = "0", required = false) final Integer offset,
      @RequestParam(value = "limit", defaultValue = "10", required = false) final Integer limit)
      throws ApiException {
    GenericOffersCriterion genericOffersCriterion = GenericOffersCriterion.builder()
        .issuerIca(issuerIca)
        .bankProductCode(bankProductCode)
        .offerType(offerType)
        .category(category)
        .offerCountry(offerCountries)
        .offset(offset)
        .limit(limit)
        .languages(languages)
        .fid(fiName)
        .build();
    return referenceApplicationGateway.getOffers(genericOffersCriterion, clientId);
  }

  @PostMapping("/platform-admin/offers/filters")
  public BrowseOffers filterOffers(
      @RequestHeader(name = "X-Openapi-Clientid", defaultValue = "") final String clientId,
      @RequestBody AdminOfferFilter offerFilter)
      throws ApiException {
    GenericOffersCriterion genericOffersCriterion = GenericOffersCriterion.builder()
        .issuerIca(offerFilter.getIssuerIca())
        .bankProductCode(offerFilter.getBankProductCode())
        .offerType(offerFilter.getOfferType())
        .category(offerFilter.getCategory())
        .offerCountry(offerFilter.getOfferCountries())
        .offset(offerFilter.getOffset())
        .limit(offerFilter.getLimit())
        .languages(offerFilter.getLanguages())
        .fid(offerFilter.getFid())
        .build();
    return referenceApplicationGateway.getOffers(genericOffersCriterion, clientId);
  }

  @GetMapping("/platform-admin/adjustments")
  public UserAdjustment getAdjustments(
      @RequestHeader(name = "X-Openapi-Clientid", defaultValue = "") final String clientId,
      @RequestParam("f_id") final String fid,
      @RequestParam(value = "offset", required = false, defaultValue = "0") final Integer offset,
      @RequestParam(value = "limit", required = false, defaultValue = "10") final Integer limit,
      @RequestParam(value = "start_date", required = false) final String startDate,
      @RequestParam(value = "end_date", required = false) final String endDate,
      @RequestParam(value = "date_filter", required = false) String dateFilter
  ) throws ApiException {
    GenericOffersCriterion genericOffersCriterion = GenericOffersCriterion.builder()
        .fid(fid)
        .offset(offset)
        .limit(limit)
        .startDate(startDate)
        .endDate(endDate)
        .dateFilter(dateFilter)
        .build();
    return referenceApplicationGateway.getAdjustments(genericOffersCriterion, clientId);
  }
}
