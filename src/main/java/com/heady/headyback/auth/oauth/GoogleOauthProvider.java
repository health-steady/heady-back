// package com.heady.headyback.auth.oauth;
//
// import static com.heady.headyback.auth.constant.GoogleOauthConstants.*;
// import static com.heady.headyback.auth.exception.AuthExceptionCode.*;
//
// import java.util.Map;
// import java.util.concurrent.ConcurrentHashMap;
//
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpStatusCode;
// import org.springframework.http.MediaType;
// import org.springframework.stereotype.Component;
// import org.springframework.util.LinkedMultiValueMap;
// import org.springframework.util.MultiValueMap;
// import org.springframework.web.reactive.function.BodyInserters;
// import org.springframework.web.reactive.function.client.ClientResponse;
// import org.springframework.web.reactive.function.client.WebClient;
//
// import com.heady.headyback.auth.dto.response.GoogleAccessTokenResponse;
// import com.heady.headyback.auth.dto.response.GoogleUserInfoResponse;
// import com.heady.headyback.auth.oauth.userInfo.GoogleUserInfo;
// import com.heady.headyback.auth.oauth.userInfo.OauthUserInfo;
// import com.heady.headyback.common.exception.CustomException;
// import com.heady.headyback.member.domain.enumerated.SocialProvider;
//
// import lombok.extern.slf4j.Slf4j;
// import reactor.core.publisher.Mono;
//
// @Slf4j
// @Component
// public class GoogleOauthProvider implements OauthProvider {
//
// 	private final WebClient webClient;
// 	private final String clientId;
// 	private final String clientSecret;
// 	private final String redirectUri;
// 	private final String tokenUri;
// 	private final String userInfoUri;
// 	private final Map<String, String> tokenCache = new ConcurrentHashMap<>();
//
// 	public GoogleOauthProvider(
// 			WebClient webClient,
// 			@Value("${oauth.google.client-id}") String clientId,
// 			@Value("${oauth.google.client-secret}") String clientSecret,
// 			@Value("${oauth.google.redirect-uri}") String redirectUri,
// 			@Value("${oauth.google.token-uri}") String tokenUri,
// 			@Value("${oauth.google.user-info-uri}") String userInfoUri
// 	) {
// 		this.webClient = webClient;
// 		this.clientId = clientId;
// 		this.clientSecret = clientSecret;
// 		this.redirectUri = redirectUri;
// 		this.tokenUri = tokenUri;
// 		this.userInfoUri = userInfoUri;
// 	}
//
// 	@Override
// 	public String getAccessToken(String code) {
// 		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
// 		formData.add(GRANT_TYPE_KEY, GRANT_TYPE);
// 		formData.add(CODE_KEY, code);
// 		formData.add(CLIENT_ID_KEY, clientId);
// 		formData.add(CLIENT_SECRET_KEY, clientSecret);
// 		formData.add(REDIRECT_URI_KEY, redirectUri);
//
// 		return webClient.post()
// 				.uri(tokenUri)
// 				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
// 				.body(BodyInserters.fromFormData(formData))
// 				.retrieve()
// 				.onStatus(
// 						HttpStatusCode::isError,
// 						this::handleOauthError
// 				)
// 				.bodyToMono(GoogleAccessTokenResponse.class)
// 				.map(GoogleAccessTokenResponse::accessToken)
// 				.block();
// 	}
//
// 	@Override
// 	public SocialProvider getProvider() {
// 		return SocialProvider.GOOGLE;
// 	}
//
// 	@Override
// 	public OauthUserInfo getUserInfo(String accessToken) {
// 		GoogleUserInfoResponse response = fetchUserInfo(accessToken);
// 		return new GoogleUserInfo(response);
// 	}
//
// 	private GoogleUserInfoResponse fetchUserInfo(String accessToken) {
// 		return webClient
// 				.get()
// 				.uri(userInfoUri)
// 				.header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + accessToken)
// 				.retrieve()
// 				.onStatus(
// 						HttpStatusCode::isError,
// 						this::handleOauthError
// 				)
// 				.bodyToMono(GoogleUserInfoResponse.class)
// 				.blockOptional()
// 				.orElseThrow(() -> new CustomException(OAUTH_USERINFO_RESPONSE_EMPTY));
// 	}
//
// 	private Mono<? extends Throwable> handleOauthError(ClientResponse response) {
// 		return response.bodyToMono(String.class)
// 				.flatMap(errorBody ->
// 						Mono.error(new CustomException(OAUTH_TOKEN_REQUEST_FAILED))
// 				);
// 	}
// }
