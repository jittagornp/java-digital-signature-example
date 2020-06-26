package me.jittagornp.example.digitalsignature;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.ECGenParameterSpec;

/**
 *
 * @author jitta
 */
public class DigitalSignatureExample {

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, InvalidAlgorithmParameterException {

        final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");

        keyPairGenerator.initialize(new ECGenParameterSpec("secp256r1"), new SecureRandom());


        final KeyPair keyPair = keyPairGenerator.generateKeyPair();

        final PrivateKey privateKey = keyPair.getPrivate();

        final Signature signature = Signature.getInstance("SHA256withECDSA");

        signature.initSign(privateKey);
        final byte[] dataBytes = "Hello World".getBytes();

        signature.update(dataBytes);

        final byte[] signatureBytes = signature.sign();

        signature.initVerify(keyPair.getPublic());
        signature.update(dataBytes);

        final boolean isValid = signature.verify(signatureBytes);

        if (isValid) {
            System.out.println("Signature is valid");
        } else {
            System.out.println("Signature is not valid");
        }

    }

}
