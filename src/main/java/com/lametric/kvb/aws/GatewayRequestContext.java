package com.lametric.kvb.aws;

import java.util.Collections;
import java.util.Map;

public class GatewayRequestContext {

    private String accountId;
    private String resourceId;
    private String stage;
    private String requestId;
    private GatewayIdentity identity;
    private String resourcePath;
    private String httpMethod;
    private String apiId;
    private Map<String, Object> authorizer = Collections.emptyMap();

    public GatewayRequestContext() {}

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public GatewayIdentity getIdentity() {
        return identity;
    }

    public void setIdentity(GatewayIdentity identity) {
        this.identity = identity;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public Map<String, Object> getAuthorizer() {
        return authorizer;
    }

    public void setAuthorizer(Map<String, Object> authorizer) {
        if (authorizer == null || authorizer.isEmpty()) {
            this.authorizer = Collections.emptyMap();
        } else {
            this.authorizer = toUnmodifiableMap(authorizer);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static <T> Map<T, Object> toUnmodifiableMap(Map<T, Object> map) {
        for (Map.Entry<T, Object> entry : map.entrySet()) {
            T key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Map) {
                map.replace(key, toUnmodifiableMap((Map) value));
            }
        }
        return Collections.unmodifiableMap(map);
    }

    @Override
    public String toString() {
        return "DefaultGatewayRequestContext [accountId=" + accountId + ", resourceId=" + resourceId + ", stage="
                + stage + ", requestId=" + requestId + ", identity=" + identity + ", resourcePath=" + resourcePath
                + ", httpMethod=" + httpMethod + ", apiId=" + apiId + ", authorizer=" + authorizer + "]";
    }
}
