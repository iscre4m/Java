package step.learning.services;

import javax.inject.Named;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Named("MD5Service")
public class MD5Service implements HashService {
    @Override
    public String hash(String data) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digestedHash = md5.digest(data.getBytes());

            return new BigInteger(1, digestedHash).toString(16);
        } catch (NoSuchAlgorithmException ignored) {
            return null;
        }
    }
}