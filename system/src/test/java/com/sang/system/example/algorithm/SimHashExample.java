package com.sang.system.example.algorithm;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SimHashExample {

    /**
     * Function: 注：该示例程序暂不支持中文
     * <p>
     * Date:     2013-8-4 下午11:01:45
     *
     * @author june: decli@qq.com
     */


    class SimHash {

        public SimHash(String tokens) {
            this.tokens = tokens;
            this.intSimHash = this.simHash();
        }

        public SimHash(String tokens, int hashBits) {
            this.tokens = tokens;
            this.hashBits = hashBits;
            this.intSimHash = this.simHash();
        }


        private final String tokens;

        private final BigInteger intSimHash;

        private String strSimHash;

        private int hashBits = 64;


        public BigInteger simHash() {
            // 定义特征向量数组
            int[] v = new int[this.hashBits];
            // 1、分词
            StringTokenizer stringTokens = new StringTokenizer(this.tokens);
            while (stringTokens.hasMoreTokens()) {
                String temp = stringTokens.nextToken();
                // 2、将每一个分词hash
                BigInteger t = this.hash(temp);
                for (int i = 0; i < this.hashBits; i++) {
                    BigInteger bitmask = new BigInteger("1").shiftLeft(i);
                    // 3、加权
                    if (t.and(bitmask).signum() != 0) {
                        // 这里是计算整个文档的所有特征的向量和
                        // 这里实际使用中需要 +- 权重，而不是简单的 +1/-1，
                        v[i] += 1;
                    } else {
                        v[i] -= 1;
                    }
                }
            }
            BigInteger fingerprint = new BigInteger("0");
            StringBuilder simHashBuffer = new StringBuilder();
            // 4、合并, 5、降维
            for (int i = 0; i < this.hashBits; i++) {
                if (v[i] >= 0) {
                    fingerprint = fingerprint.add(new BigInteger("1").shiftLeft(i));
                    simHashBuffer.append("1");
                } else {
                    simHashBuffer.append("0");
                }
            }
            this.strSimHash = simHashBuffer.toString();
            System.out.println(this.strSimHash + " length " + this.strSimHash.length());
            return fingerprint;
        }

        private BigInteger hash(String source) {
            if (source == null || source.length() == 0) {
                return new BigInteger("0");
            } else {
                char[] sourceArray = source.toCharArray();
                BigInteger x = BigInteger.valueOf(((long) sourceArray[0]) << 7);
                BigInteger m = new BigInteger("1000003");
                BigInteger mask = new BigInteger("2").pow(this.hashBits).subtract(new BigInteger("1"));
                for (char item : sourceArray) {
                    BigInteger temp = BigInteger.valueOf(item);
                    x = x.multiply(m).xor(temp).and(mask);
                }
                x = x.xor(new BigInteger(String.valueOf(source.length())));
                if (x.equals(new BigInteger("-1"))) {
                    x = new BigInteger("-2");
                }
                return x;
            }
        }

        public int hammingDistance(SimHash other) {

            BigInteger x = this.intSimHash.xor(other.intSimHash);
            int tot = 0;

            // 统计x中二进制位数为1的个数
            // 我们想想，一个二进制数减去1，那么，从最后那个1（包括那个1）后面的数字全都反了，对吧，然后，n&(n-1)就相当于把后面的数字清0，
            // 我们看n能做多少次这样的操作就OK了。

            while (x.signum() != 0) {
                tot += 1;
                x = x.and(x.subtract(new BigInteger("1")));
            }
            return tot;
        }

        public int getDistance(String str1, String str2) {
            int distance;
            if (str1.length() != str2.length()) {
                distance = -1;
            } else {
                distance = 0;
                for (int i = 0; i < str1.length(); i++) {
                    if (str1.charAt(i) != str2.charAt(i)) {
                        distance++;
                    }
                }
            }
            return distance;
        }

        public List<BigInteger> subByDistance(SimHash simHash, int distance) {

            int numEach = this.hashBits / (distance + 1);
            List<BigInteger> characters = new ArrayList<>();

            StringBuilder buffer = new StringBuilder();

            for (int i = 0; i < this.intSimHash.bitLength(); i++) {

                boolean sr = simHash.intSimHash.testBit(i);

                if (sr) {
                    buffer.append("1");
                } else {
                    buffer.append("0");
                }

                if ((i + 1) % numEach == 0) {
                    BigInteger eachValue = new BigInteger(buffer.toString(), 2);
                    System.out.println("----" + eachValue);
                    buffer.delete(0, buffer.length());
                    characters.add(eachValue);
                }
            }

            return characters;
        }

    }


//    @Test
    void simHashTest() {
        String s = "This is a test string for testing";
        SimHash hash1 = new SimHash(s, 64);
        System.out.println(hash1.intSimHash + "  " + hash1.intSimHash.bitLength());
        hash1.subByDistance(hash1, 3);

        s = "This is a test string for testing, This is a test string for testing abcdef";
        SimHash hash2 = new SimHash(s, 64);
        System.out.println(hash2.intSimHash + "  " + hash2.intSimHash.bitCount());
        hash1.subByDistance(hash2, 3);

        s = "This is a test string for testing als";
        SimHash hash3 = new SimHash(s, 64);
        System.out.println(hash3.intSimHash + "  " + hash3.intSimHash.bitCount());
        hash1.subByDistance(hash3, 4);

        System.out.println("============================");

        int dis = hash1.getDistance(hash1.strSimHash, hash2.strSimHash);
        System.out.println(hash1.hammingDistance(hash2) + " " + dis);

        int dis2 = hash1.getDistance(hash1.strSimHash, hash3.strSimHash);
        System.out.println(hash1.hammingDistance(hash3) + " " + dis2);

        //通过Unicode编码来判断中文
        /*String str = "中国chinese";
        for (int i = 0; i < str.length(); i++) {
            System.out.println(str.substring(i, i + 1).matches("[\\u4e00-\\u9fbb]+"));
        }*/

    }
}
