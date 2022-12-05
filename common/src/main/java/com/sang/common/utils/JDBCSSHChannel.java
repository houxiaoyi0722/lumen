package com.sang.common.utils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class JDBCSSHChannel {
    private Session session;
    private Channel channel;

    /**
     *
     * @param localPort  本地host 建议mysql 3306 redis 6379
     * @param sshHost   ssh host
     * @param sshPort   ssh port
     * @param sshUsername   ssh 用户名
     * @param sshPassWord   ssh密码
     * @param remotoHost   远程机器地址
     * @param remotoPort	远程机器端口
     */
    public void goSSH(int localPort, String sshHost, int sshPort,
                      String sshUsername, String sshPassWord,
                      String remotoHost, int remotoPort) {
        try {
            JSch jsch = new JSch();
            // 私钥登录时
//            jsch.addIdentity();
            //登陆跳板机
            session = jsch.getSession(sshUsername, sshHost, sshPort);
            session.setPassword(sshPassWord);
            session.setConfig("StrictHostKeyChecking", "no"); // 对public_key的检查等级设置，默认值StrictHostKeyChecking=ask

            //最不安全的级别，相对安全的内网测试时建议使用。如果连接server的key在本地不存在，那么就自动添加到文件中（默认是known_hosts），并且给出一个警告。
//            session.setConfig("StrictHostKeyChecking", "no");

            //默认的级别,如果连接和key不匹配，给出提示，并拒绝登录
//            session.setConfig("StrictHostKeyChecking", "ask");

            //最安全的级别，如果连接与key不匹配，就拒绝连接，不会提示详细信息
//            session.setConfig("StrictHostKeyChecking", "yes");

            session.connect();
            //建立通道
            channel = session.openChannel("session");
            channel.connect();
            //通过ssh连接到mysql机器
            int assinged_port = session.setPortForwardingL(localPort, remotoHost, remotoPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭
     */
    public void close() {
        if (session != null && session.isConnected() ) {
            session.disconnect();
        }

        if (channel != null && session.isConnected() ) {
            channel.disconnect();
        }
    }
}