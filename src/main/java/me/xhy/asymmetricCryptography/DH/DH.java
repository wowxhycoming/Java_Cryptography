package me.xhy.asymmetricCryptography.DH;

import javax.crypto.*;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

/**
 * Created by xuhuaiyu on 2016/9/7.
 */
public class DH {

	private static String src = "security dh";

	public static void main(String[] args) {

		jdkDH();

	}

	public static void jdkDH() {
		try {

			// 1.初始化发送方密钥
			KeyPairGenerator senderKeyPairGenerator = KeyPairGenerator.getInstance("DH");
			// 初始化确定密钥大小的密钥对生成器，使用默认的参数集合，并使用以最高优先级安装的提供者的 SecureRandom 实现作为随机源。
			senderKeyPairGenerator.initialize(512);
			// 实例化发送方KeyPair
			KeyPair senderKeyPair = senderKeyPairGenerator.generateKeyPair();
			// 发送方公钥载体。需要把这个公钥发给接收方
			byte[] senderPublicKeyEnc = senderKeyPair.getPublic().getEncoded();

			// 2.初始化接收方密钥
			KeyFactory receiverKeyFactory = KeyFactory.getInstance("DH");
			// 使用发送方公钥
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(senderPublicKeyEnc);
			// 接收方 PublicKey
			PublicKey receiverPublicKey = receiverKeyFactory.generatePublic(x509EncodedKeySpec);
			// 接收方要使用和发送方一样的参数来初始化KeyPair对，所以要从发送方公钥中读取参数，再用这个参数生成密钥
			DHParameterSpec receiverDHParameterSpec = ((DHPublicKey)receiverPublicKey).getParams();
			//
			KeyPairGenerator receiverKeyPairGenerator = KeyPairGenerator.getInstance("DH");
			// 初始化
			receiverKeyPairGenerator.initialize(receiverDHParameterSpec);
			// 生成接收方KeyPair
			KeyPair receiverKeyPair = receiverKeyPairGenerator.generateKeyPair();
			// 接收方 PrivateKey
			PrivateKey receiverPrivateKey = receiverKeyPair.getPrivate();
			// 接收方公钥载体，需要发给发送方
			byte[] receiverPublicKeyEnc = receiverKeyPair.getPublic().getEncoded();

			// 3.接收方密钥构建
			KeyAgreement receiverKeyAgreement = KeyAgreement.getInstance("DH");
			// 使用接收方私钥 初始化接收方的 receiverKeyAgreement
			receiverKeyAgreement.init(receiverPrivateKey);
			receiverKeyAgreement.doPhase(receiverPublicKey, true);
			// 用发送方公钥，生成本地密钥
			SecretKey receiverDesKey = receiverKeyAgreement.generateSecret("DES");

			// 4.发送方密钥构建
			KeyFactory senderKeyFactory = KeyFactory.getInstance("DH");
			// 重新定义x509
			x509EncodedKeySpec = new X509EncodedKeySpec(receiverPublicKeyEnc);
			PublicKey senderPublicKey = senderKeyFactory.generatePublic(x509EncodedKeySpec);
			KeyAgreement senderKeyAgreement = KeyAgreement.getInstance("DH");
			senderKeyAgreement.init(senderKeyPair.getPrivate());
			senderKeyAgreement.doPhase(senderPublicKey, true);
			// 发送方本地密钥
			SecretKey senderDesKey = senderKeyAgreement.generateSecret("DES");

			if(Objects.equals(receiverDesKey, senderDesKey)) {
				System.out.println("密钥相同");
			}

			// 加密
			Cipher cipher =  Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, senderDesKey);
			byte[] result = cipher.doFinal(src.getBytes());

			System.out.println(new String(result));

			// 解密
			cipher =  Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, receiverDesKey);
			result = cipher.doFinal(result);

			System.out.println(new String(result));

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		}

	}
}
