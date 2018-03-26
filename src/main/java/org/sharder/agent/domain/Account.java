package org.sharder.agent.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Account
 *
 * @author bubai
 * @date 2018/3/23
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {
    @JsonProperty(value = "account")
    private String accountID;
    @JsonProperty(value = "accountRS")
    private String accountRS;
    @JsonProperty(value = "publicKey")
    private String publicKey;

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getAccountRS() {
        return accountRS;
    }

    public void setAccountRS(String accountRS) {
        this.accountRS = accountRS;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
