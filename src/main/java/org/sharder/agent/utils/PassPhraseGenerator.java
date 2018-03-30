package org.sharder.agent.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Generate a random, unique password for the user
 * @author bubai
 * @date 2018/3/24
 */
public class PassPhraseGenerator {

    private static Logger logger = LoggerFactory.getLogger(PassPhraseGenerator.class);

    /**
     * @deprecated
     * @return
     */
    public String generate(){
        final String secretPrefix = Vanity.makeRandomSecretPhrase();
        logger.debug("Using a randomly generated secret prefix: " + secretPrefix);
        Vanity.generatePassword(Vanity.vanityFilter, secretPrefix);
        return null;
    }

    /**
     * @deprecated
     * @param accountPrefix
     * @return
     */
    public String generate(String accountPrefix){
        final String secretPrefix = Vanity.makeRandomSecretPhrase();
        logger.debug("Using a randomly generated secret prefix: " + secretPrefix);
        Vanity.generatePassword(new Vanity.PrefixFilter(accountPrefix), secretPrefix);
        return null;
    }

    /**
     * @deprecated failed to load window object
     * invoke the javascript methods in passphrasegenerator.js
     * @return
     */
    public static String generateFromMnemonicWords(){
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        String c = null;
        FileReader reader = null;
        try {
            File file = ResourceUtils.getFile("classpath:passphrasegenerator.js");
            reader = new FileReader(file);
            engine.eval(reader);
            if(engine instanceof Invocable) {
                Invocable invoke = (Invocable)engine;
                c = (String)invoke.invokeFunction("generate");
                logger.debug("generatePassPhrase = " + c);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return c;
    }

    /**
     * makeRandomSecretPhrase from ascii code combination
     * @return String random secretphrase
     */
    public static String makeRandomSecretPhrase() {
        // use all ascii chars and symbols from '!' (33) to '~' (126)
        final byte start = 33;
        final byte end = 127;
        Random random = new SecureRandom();
        byte[] bytes = new byte[64];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte)(random.nextInt(end - start) + start);
        }
        return new String(bytes, Charset.forName("UTF-8"));
    }

//    public static String makeMnemonicWords() throws MnemonicException.MnemonicLengthException {
//        MnemonicCode mnemonicCode = new MnemonicCode();
//        byte[] random = RandomSeed.random(Words.TWELVE);
//        List<String> mnemonicWordsInAList = mnemonicCode.toMnemonic(random);
//        return mnemonicWordsInAList.toString();
//    }

}
