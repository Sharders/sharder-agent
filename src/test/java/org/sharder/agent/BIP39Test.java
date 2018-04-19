package org.sharder.agent;

import io.github.novacrypto.bip39.MnemonicGenerator;
import io.github.novacrypto.bip39.SeedCalculator;
import io.github.novacrypto.bip39.Words;
import io.github.novacrypto.bip39.wordlists.English;
import org.junit.Assert;
import org.junit.Test;

import java.security.SecureRandom;
/**
 * <DESC HERE>
 *
 * @author bubai
 * @date 2018/4/9
 */
public class BIP39Test {

    @Test
    public void generate() {
        StringBuilder sb = new StringBuilder();
        byte[] entropy = new byte[Words.TWELVE.byteLength()];
        new SecureRandom().nextBytes(entropy);
        new MnemonicGenerator(English.INSTANCE)
                .createMnemonic(entropy, sb::append);
        final String mnemonic = sb.toString();
        System.out.println("Mnemonic: " + mnemonic);

        byte[] seed = new SeedCalculator()
                .calculateSeed(mnemonic, "");

        Assert.assertEquals(64,seed.length);
    }
}
