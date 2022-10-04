package step.learning.services;

import javax.inject.Named;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Named("SHA1Service")
public class SHA1Service implements HashService{
    @Override
    public String hash(String data) {
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA1");
            byte[] digestedHash = sha1.digest(data.getBytes());

            return new BigInteger(1, digestedHash).toString(16);
        } catch (NoSuchAlgorithmException ignored) {
            return null;
        }
    }
}