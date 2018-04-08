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
    private String accountID;
    private String accountRS;
    private String publicKey;
    private String passPhrase;

    @JsonProperty(value = "accountId")
    public String getAccountID() {
        return accountID;
    }

    @JsonProperty(value = "account")
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

    public String getPassPhrase() {
        return passPhrase;
    }

    public void setPassPhrase(String passPhrase) {
        this.passPhrase = passPhrase;
    }

}
