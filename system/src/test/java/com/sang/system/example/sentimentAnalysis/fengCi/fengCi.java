/*
 * Bob
 * 1163802968@qq.com
 * South China Normal University
 * 2015-12-16
 * 功能：实现分词
 * */
package com.sang.system.example.sentimentAnalysis.fengCi;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class fengCi {

    //停用词词表
    InputStream jdCommemt;

    String setCode;


    public fengCi(InputStream Commemt, String setCode) {
        // TODO Auto-generated constructor stub
        this.jdCommemt = Commemt;
        this.setCode = setCode;
    }


    public String getFengci() throws Exception {

        //读入停用词文件

//        BufferedReader StopWordFileBr = new BufferedReader(new InputStreamReader(new FileInputStream(stopWordTable)));
        BufferedReader StopWordFileBr = new BufferedReader(new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream("/data/stopWord/stopWord.txt"))));

        //用来存放停用词的集合
        Set<String> stopWordSet = new HashSet<>();

        //初如化停用词集
        String stopWord = null;
        for (; (stopWord = StopWordFileBr.readLine()) != null; ) {
            stopWordSet.add(stopWord);
        }

        //导入我们之前抓取的京东评论,注意编码格式
        BufferedReader jdComFileBr = new BufferedReader(new InputStreamReader(jdCommemt, setCode));

        //创建分词对象
        String text = null;
        StringBuffer FT = new StringBuffer();
        try {
            while ((text = jdComFileBr.readLine()) != null) {
                StringReader sr = new StringReader(text);
                IKSegmenter ik = new IKSegmenter(sr, true);
                Lexeme lex = null;

                //分词
                while ((lex = ik.next()) != null) {
                    //去除停用词   自己添加了\n,p8
                    if (stopWordSet.contains(lex.getLexemeText())) {
                        continue;
                    }
                    //	            System.out.print(lex.getLexemeText()+"\t");
                    FT.append(lex.getLexemeText() + " ");
                }
                FT.append("\r\n");
            }
            StopWordFileBr.close();//关闭流
            jdComFileBr.close();

        } catch (NullPointerException e) {
//            saveFengci(path, FT.toString());//保存
            System.out.println("\r\n" + "分词完成！(^.^)");
        }

        return FT.toString();
    }


    //保存分词
    public void saveFengci(String path, String ci) {
        File file = new File(path);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fwriter = new FileWriter(path, true);
            fwriter.write(ci);
            fwriter.flush();
            fwriter.close();
        } catch (Exception e) {
            System.out.print("分词在保存数据时候出现IO错误：");
            e.printStackTrace();
        }
    }


}
