package org.sharder.agent.utils;

import com.sun.tools.javac.comp.Check;

/**
 * Generate a random, unique password for the user
 *
 * @author bubai
 * @date 2018/3/24
 */
public class PassPhraseGenerator {

    /**
     * get back generated password with API request to ?requestType=getAccountId&secretPhrase=PASSPHRASE
     */
    public String generate(){

        //TODO Check to see if the generated account is a collision with an existing account by issuing this API call: requestType=getAccountPublicKey&account=GENERATED_ACCOUNT_NUMBER

        //TODO checking to see if any transactions exist on the blockchain for the generated account number: requestType=getAccountTransactionIds&account=ACCOUNT&timestamp=0

        //TODO associate the generated account to the requester
        return null;
    }
}
