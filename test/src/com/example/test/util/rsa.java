package com.example.test.util;

import android.annotation.SuppressLint;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
//import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

@SuppressLint("TrulyRandom")
public class rsa {

	@SuppressLint("TrulyRandom")
	public static void main() throws Exception {
		//Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		byte[] input = new byte[] { (byte) 0x45, (byte) 0x55 };
		System.out.println("input: " + new String(input));
		Cipher cipher = Cipher.getInstance("RSA/None/NoPadding", "BC");
		/*
		KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
		RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(
				"12345678", 16), new BigInteger("11", 16));
		RSAPrivateKeySpec privKeySpec = new RSAPrivateKeySpec(new BigInteger(
				"12345678", 16), new BigInteger("12345678", 16));

		RSAPublicKey pubKey = (RSAPublicKey) keyFactory
				.generatePublic(pubKeySpec);
		RSAPrivateKey privKey = (RSAPrivateKey) keyFactory
				.generatePrivate(privKeySpec);
		*/
		SecureRandom random = new SecureRandom();
	    KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");

	    generator.initialize(256, random);
	    KeyPair pair = generator.generateKeyPair();
	    Key pubKey = pair.getPublic();
	    Key privKey = pair.getPrivate();
	    
	    System.out.println("pubKey: " + pubKey.toString());
	    System.out.println("privKey: " + privKey.toString());
	    
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);

		byte[] cipherText = cipher.doFinal(input);
		System.out.println("cipher: " + new String(cipherText));

		cipher.init(Cipher.DECRYPT_MODE, privKey);
		byte[] plainText = cipher.doFinal(cipherText);
		System.out.println("plain : " + new String(plainText));
	}
}