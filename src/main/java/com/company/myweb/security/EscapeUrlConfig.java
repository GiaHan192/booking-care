package com.company.myweb.security;

import lombok.Getter;
import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.List;

public class EscapeUrlConfig {
    @Getter
    private static final List<EscapeUrl> escapeUrls = new ArrayList<>();

    static {
        escapeUrls.add(new EscapeUrl("/api/bookings/test-email", HttpMethod.POST));
        escapeUrls.add(new EscapeUrl("/auth/signin", HttpMethod.POST));
        escapeUrls.add(new EscapeUrl("/questions", HttpMethod.GET));
        escapeUrls.add(new EscapeUrl("/questions/submit", HttpMethod.POST));
        escapeUrls.add(new EscapeUrl("/api/bookings/doctor", HttpMethod.GET));
        escapeUrls.add(new EscapeUrl("/questions/submit", HttpMethod.POST));
        escapeUrls.add(new EscapeUrl("/api/posts/**", HttpMethod.GET));
        escapeUrls.add(new EscapeUrl("/api/doctors/**", HttpMethod.GET));
        escapeUrls.add(new EscapeUrl("/auth/**", HttpMethod.POST));
        escapeUrls.add(new EscapeUrl("api-docs/swagger-config", HttpMethod.GET));
        escapeUrls.add(new EscapeUrl("/swagger-ui/**", HttpMethod.GET));
        escapeUrls.add(new EscapeUrl("api-docs", HttpMethod.GET));
        escapeUrls.add(new EscapeUrl("/v3/api-docs/**", HttpMethod.GET));
    }

    @Getter
    public static class EscapeUrl {
        private final String url;
        private final HttpMethod method;

        public EscapeUrl(String url, HttpMethod method) {
            this.url = url;
            this.method = method;
        }

    }

    /**
     * This method is used in case we want to control user route by own function (custom)
     */
    public static boolean shouldBypassAuthentication(AntPathMatcher pathMatcher, String requestUri, String requestMethod) {
        return EscapeUrlConfig.getEscapeUrls().stream()
                .anyMatch(escapeUrl -> pathMatcher.match(escapeUrl.getUrl(), requestUri) && escapeUrl.getMethod().name().equalsIgnoreCase(requestMethod));
    }
}
