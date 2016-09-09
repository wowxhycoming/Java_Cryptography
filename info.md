# 机密算法

## 对称加密（Symmetric Cryptography）

## 非对称加密（Asymmetric Cryptography）

### DH算法
密钥交换算法，非对称加密算法的起源。
#### 初始化发送方密钥
1. KeyPair ： 密钥的载体，也被称为密钥对。一般包含两个信息，公钥和私钥。
2. KeyPairGenerator ： 用来生成KeyPair的对象，KeyPairGenerator 是一个抽象类，可以通过其工厂方法 getInstance() 方法获得实例化的对象。
getInstance() 的第一个参数是字符串类型，为加密算法的名称；第二个参数不常用，是加密算法的提供方，可以是字符串也可以是对象。
3. PublicKey

#### 初始化接收方密钥
1. KeyFactory ： 生成密钥，公钥和私钥都可以生成。 
2. X509EncodedKeySpec ： 根据 ASN.1 标准进行密钥的编码。
3. DHPublicKey ： PublicKey 的某种形式。 
4. DHParameterSpec ： DH算法使用的参数集合。
5. KeyPairGenerator
6. PrivateKey

#### 密钥构建
1. KeyAgreement ： 提供密钥一致性（或密钥交换）协议的功能。
2. SecretKey ： 用于生成一个分组的秘密密钥。
3. KeyFactory
4. X509EncodedKeySpec
5. PublicKey

#### 加解密
1. Cipher ： 为加密解密提供功能。

### RSA算法
基于因子分解。
唯一广泛接受的非对称算法。可支持数据加密和数字签名。公钥加密-私钥解密 和 私钥加密-公钥解密。但是效率比较慢。
是基于大数因子分解的非对称密钥加密算法的实现方式。
密钥长度，在512~65536并且必须为64的整数倍。
实现提供，JDK和BC。JDK实现的密钥默认长度为1024，BC实现的密钥默认长度为2048。

### ElGamal算法
基于离散对数。
只提供公钥加密算法。
JDK并没有提供实现，Bouncy Castle 提供了实现。应用比较广泛。
密钥长度。160~16384并且必须为8的整数倍。
实现提供，BC。密钥默认长度1024。
只有构建密钥的时候与RSA有区别，加解密的过程与RSA完全相同。








