package org.sharder.agent.domain;

/**
 * TransactionResponse
 *
 * @author bubai
 * @date 2018/3/26
 */
public class TransactionResponse {
    public enum Type{
        PAY("0-0"),
        DATA("6-0");
        private String value;

        private Type(String type) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    private String transaction;
    private boolean broadcasted;
}
