/*
 * Bob
 * 1163802968@qq.com
 * South China Normal University
 * 2015-12-16
 * ���ܣ�ʵ�ִַ�
 * */
package com.sang.system.example.sentimentAnalysis.fengCi;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class fengCi {

    //ͣ�ôʴʱ�
    InputStream jdCommemt;

    String setCode;


    public fengCi(InputStream Commemt, String setCode) {
        // TODO Auto-generated constructor stub
        this.jdCommemt = Commemt;
        this.setCode = setCode;
    }


    public String getFengci() throws Exception {

        //����ͣ�ô��ļ�

//        BufferedReader StopWordFileBr = new BufferedReader(new InputStreamReader(new FileInputStream(stopWordTable)));
        BufferedReader StopWordFileBr = new BufferedReader(new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream("/data/stopWord/stopWord.txt"))));

        //�������ͣ�ôʵļ���
        Set<String> stopWordSet = new HashSet<>();

        //���绯ͣ�ôʼ�
        String stopWord = null;
        for (; (stopWord = StopWordFileBr.readLine()) != null; ) {
            stopWordSet.add(stopWord);
        }

        //��������֮ǰץȡ�ľ�������,ע������ʽ
        BufferedReader jdComFileBr = new BufferedReader(new InputStreamReader(jdCommemt, setCode));

        //�����ִʶ���
        String text = null;
        StringBuffer FT = new StringBuffer();
        try {
            while ((text = jdComFileBr.readLine()) != null) {
                StringReader sr = new StringReader(text);
                IKSegmenter ik = new IKSegmenter(sr, true);
                Lexeme lex = null;

                //�ִ�
                while ((lex = ik.next()) != null) {
                    //ȥ��ͣ�ô�   �Լ������\n,p8
                    if (stopWordSet.contains(lex.getLexemeText())) {
                        continue;
                    }
                    //	            System.out.print(lex.getLexemeText()+"\t");
                    FT.append(lex.getLexemeText() + " ");
                }
                FT.append("\r\n");
            }
            StopWordFileBr.close();//�ر���
            jdComFileBr.close();

        } catch (NullPointerException e) {
//            saveFengci(path, FT.toString());//����
            System.out.println("\r\n" + "�ִ���ɣ�(^.^)");
        }

        return FT.toString();
    }


    //����ִ�
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
            System.out.print("�ִ��ڱ�������ʱ�����IO����");
            e.printStackTrace();
        }
    }


}
