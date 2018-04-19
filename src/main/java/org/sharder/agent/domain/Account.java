package org.sharder.agent.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Account
 *
 * @author bubai
 * @date 2018/3/23
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Account {
    private String accountID;
    private String accountRS;
    private String publicKey;
    private String passPhrase;
    private BigDecimal forgedBalance;
    private BigDecimal balance;

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

    @JsonProperty(value = "forgedBalance")
    public BigDecimal getForgedBalance() {
        return forgedBalance;
    }

    @JsonProperty(value = "forgedBalanceNQT")
    public void setForgedBalance(BigDecimal forgedBalance) {
        this.forgedBalance = forgedBalance.divide(BigDecimal.valueOf(100000000L));
    }

    @JsonProperty(value = "balance")
    public BigDecimal getBalance() {
        return balance;
    }

    @JsonProperty(value = "balanceNQT")
    public void setBalance(BigDecimal balance) {
        this.balance = balance.divide(BigDecimal.valueOf(100000000L));
    }
}
