package com.example.test.util;


import android.content.Context;
import android.widget.Toast;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import me.noip.adimur.smstele2kz.R;

public class MyRsa {

	private static byte[] getPubKey(Context cnt) {
		return CryptoUtil.hex2Byte(cnt.getString(R.string.pub_key));
	}

	private static PublicKey getPublicKey(Context cnt) throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeyFactory keyFactory = KeyFactory.getInstance(CryptoUtil.ALGORITHM);
		EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(getPubKey(cnt));
		return keyFactory.generatePublic(publicKeySpec);
	}

	public static String encrypt(Context cnt, String text) {
		if (text.length() > 240)
			text = text.substring(0, 240);
		try {
			return CryptoUtil.byte2Hex(CryptoUtil.encrypt(text, getPublicKey(cnt)));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			Toast.makeText(cnt, e.toString(), Toast.LENGTH_SHORT).show();
			return text+e.toString();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			Toast.makeText(cnt, e.toString(), Toast.LENGTH_SHORT).show();
			return text+e.toString();
		}
	}
	
	public static void main() throws Exception {
		// Security.addProvider(new
		// org.bouncycastle.jce.provider.BouncyCastleProvider());

		byte[] input = new byte[] { (byte) 0x45, (byte) 0x55 };
		System.out.println("input: " + new String(input));
		Cipher cipher = Cipher.getInstance("RSA/None/NoPadding", "BC");
		/*
		 * KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
		 * RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(
		 * "12345678", 16), new BigInteger("11", 16)); RSAPrivateKeySpec
		 * privKeySpec = new RSAPrivateKeySpec(new BigInteger( "12345678", 16),
		 * new BigInteger("12345678", 16));
		 * 
		 * RSAPublicKey pubKey = (RSAPublicKey) keyFactory
		 * .generatePublic(pubKeySpec); RSAPrivateKey privKey = (RSAPrivateKey)
		 * keyFactory .generatePrivate(privKeySpec);
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