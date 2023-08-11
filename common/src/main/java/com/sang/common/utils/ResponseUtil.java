package com.sang.common.utils;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.sang.common.constants.StringConst;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static cn.hutool.core.util.CharsetUtil.UTF_8;

/**
 * 响应工具类
 */
public class ResponseUtil {



    /**
     * 发送流到http响应对象中
     * @param resourceAsStream 流
     * @param contentType 内容
     * @param filename 文件名
     * @throws IOException
     */
    public static void sendStream(InputStream resourceAsStream, String contentType, String filename) throws IOException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletResponse httpServletResponse = requestAttributes.getResponse();
        assert httpServletResponse != null;
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        try {
            // 设置信息给客户端不解析
            // 设置CONTENT_TYPE，即告诉客户端所发送的数据属于什么类型
            httpServletResponse.setHeader(StringConst.CONTENT_TYPE, contentType);

            // 设置扩展头，当Content-Type 的类型为要下载的类型时 , 这个信息头会告诉浏览器这个文件的名字和类型。
            httpServletResponse.setCharacterEncoding(UTF_8);
            httpServletResponse.setHeader(StringConst.CONTENT_DISPOSITION, StrUtil.format(StringConst.ATTACHMENT_FILENAME, URLEncoder.encode(filename, StandardCharsets.UTF_8)));
            IoUtil.copy(resourceAsStream, outputStream);
        } finally {
            if (resourceAsStream != null) {
                IoUtil.close(resourceAsStream);
            }
            if (outputStream != null) {
                IoUtil.close(outputStream);
            }
        }
    }


}
