package me.jittagornp.example.digitalsignature;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.ECGenParameterSpec;

/**
 * @author jitta
 */
public class DigitalSignatureExample {

    private static KeyPair getKeyPair() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        keyPairGenerator.initialize(new ECGenParameterSpec("secp256r1"), new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }

    private static Signature getSignature() throws NoSuchAlgorithmException {
        return Signature.getInstance("SHA256withECDSA");
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, InvalidAlgorithmParameterException {

        //1. Data
        final String document = "Hello World";
        final byte[] documentBytes = document.getBytes();
        //====================================================

        //2. KeyPair
        final KeyPair keyPair = getKeyPair();
        final PublicKey publicKey = keyPair.getPublic();
        final PrivateKey privateKey = keyPair.getPrivate();

        //3. Signature
        final Signature signature = getSignature();
        //====================================================

        //4. Sign Document
        signature.initSign(privateKey);
        signature.update(documentBytes);
        final byte[] signatureBytes = signature.sign();
        //====================================================

        //5. Verify Document
        signature.initVerify(publicKey);

        //Compare Data and Signature
        signature.update(documentBytes);
        final boolean isValid = signature.verify(signatureBytes);
        if (isValid) {
            System.out.println("Signature is valid");
        } else {
            System.out.println("Signature is not valid");
        }

    }

}
