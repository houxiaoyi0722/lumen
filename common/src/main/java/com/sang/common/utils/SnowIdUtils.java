package com.sang.common.utils;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * 目前的实现中,如果ip数值之和与hostname两者都重复,则有概率产生重复id
 */
public class SnowIdUtils {
    /**
     * 私有的 静态内部类
     */
    private static class SnowFlake {

        /**
         * 内部类对象（单例模式）
         */
        private static final SnowFlake SNOW_FLAKE = new SnowFlake();
        /**
         * 起始的时间戳
         */
        private static final long START_TIMESTAMP = 1557489395327L;
        /**
         * 序列号占用位数
         */
        private static final long SEQUENCE_BIT = 12;
        /**
         * 数据中心id所占长度
         */
        private static final int DATA_LEN = 5;
        /**
         * 机器id所占长度
         */
        private static final int WORK_LEN = 5;
        /**
         * 机器标识占用位数
         */
        private static final long MACHINE_BIT = DATA_LEN+WORK_LEN;
        /**
         * 时间戳位移位数
         */
        private static final long TIMESTAMP_LEFT = SEQUENCE_BIT + MACHINE_BIT;
        /**
         * 最大序列号  （4095）
         */
        private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);
        /**
         * 最大机器编号 （1023）
         */
        private static final long MAX_MACHINE_ID = ~(-1L << MACHINE_BIT);
        /**
         * 数据中心id最大值 31
         */
        private static final int DATA_MAX_NUM = ~(-1 << DATA_LEN);
        /**
         * 机器id最大值 31
         */
        private static final int WORK_MAX_NUM = ~(-1 << WORK_LEN);
        /**
         * 随机获取数据中心id的参数 32
         */
        private static final int DATA_RANDOM = DATA_MAX_NUM + 1;
        /**
         * 随机获取机器id的参数 32
         */
        private static final int WORK_RANDOM = WORK_MAX_NUM + 1;
        /**
         * 生成id机器标识部分
         */
        private static long machineIdPart;
        /**
         * 序列号
         */
        private long sequence = 0L;
        /**
         * 上一次时间戳
         */
        private long lastStamp = -1L;

        /**
         * 构造函数初始化机器编码
         */
        private SnowFlake() {
            //hostName + ip,在左位移12位
            machineIdPart = (getDataId() << WORK_LEN | getWorkId()) << SEQUENCE_BIT;
        }

        /**
         * 获取数据中心名称id(机器名代替)
         * @return 返回HostName生成的5位二进制码
         */
        private static int getDataId() {
            try {
                return getHostId(Inet4Address.getLocalHost().getHostName(), DATA_MAX_NUM);
            } catch (UnknownHostException e) {
                return new Random().nextInt(DATA_RANDOM);
            }
        }

        /**
         * 获取机器ip
         * @return 返回机器ip生成的5位二进制码
         */
        private static int getWorkId() {
            try {
                return getHostId(Inet4Address.getLocalHost().getHostAddress(), WORK_MAX_NUM);
            } catch (UnknownHostException e) {
                return new Random().nextInt(WORK_RANDOM);
            }
        }

        /**
         * 加起来取模
         * @param str 字符串
         * @param max 最大长度
         * @return 二进制码
         */
        private static int getHostId(String str, int max) {
            byte[] bytes = str.getBytes();
            int sums = 0;
            for (int b : bytes) {
                sums += b;
            }
            return sums % (max + 1);
        }

        /**
         * 获取雪花ID
         */
        public synchronized long nextId() {
            long currentStamp = timeGen();
            //避免机器时钟回拨
            while (currentStamp < lastStamp) {
                // //服务器时钟被调整了,ID生成器停止服务.
                throw new RuntimeException(String.format("时钟已经回拨.  Refusing to generate id for %d milliseconds", lastStamp - currentStamp));
            }
            if (currentStamp == lastStamp) {
                // 每次+1
                sequence = (sequence + 1) & MAX_SEQUENCE;
                // 毫秒内序列溢出
                if (sequence == 0) {
                    // 阻塞到下一个毫秒,获得新的时间戳
                    currentStamp = getNextMill();
                }
            } else {
                //不同毫秒内，序列号置0
                sequence = 0L;
            }
            lastStamp = currentStamp;
            //时间戳部分+机器标识部分+序列号部分
            return (currentStamp - START_TIMESTAMP) << TIMESTAMP_LEFT | machineIdPart | sequence;
        }
        /**
         * 阻塞到下一个毫秒，直到获得新的时间戳
         */
        private long getNextMill() {
            long mill = timeGen();
            //
            while (mill <= lastStamp) {
                mill = timeGen();
            }
            return mill;
        }
        /**
         * 返回以毫秒为单位的当前时间
         */
        protected long timeGen() {
            return System.currentTimeMillis();
        }
    }

    /**
     * 获取long类型雪花ID
     */
    public static Long uniqueLong() {
        return SnowFlake.SNOW_FLAKE.nextId();
    }
    /**
     * 获取String类型雪花ID
     */
    public static String uniqueLongHex() {
        return String.format("%016x", uniqueLong());
    }
}
