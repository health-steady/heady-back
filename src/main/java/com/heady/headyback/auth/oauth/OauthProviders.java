package com.heady.headyback.auth.oauth;

import static com.heady.headyback.auth.exception.AuthExceptionCode.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.heady.headyback.common.exception.CustomException;
import com.heady.headyback.member.domain.enumerated.SocialProvider;

@Component
public class OauthProviders {

	private final Map<SocialProvider, OauthProvider> providerMap;

	public OauthProviders(List<OauthProvider> providers) {
		this.providerMap = providers.stream()
				.collect(Collectors.toMap(
						OauthProvider::getProvider,
						Function.identity()
				));
	}

	public OauthProvider getProvider(SocialProvider provider) {
		OauthProvider found = providerMap.get(provider);
		if (found == null) {
			throw new CustomException(AUTH_NOT_SUPPORTED_OAUTH_PROVIDER);
		}
		return found;
	}
}
