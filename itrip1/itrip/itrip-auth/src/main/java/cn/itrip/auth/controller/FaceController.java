package cn.itrip.auth.controller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import cn.itrip.auth.Face.Test;
import com.alibaba.fastjson.JSON;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

@Controller
@RequestMapping(value = "/Face")
public class FaceController {
    /**
     * 功能描述：base64字符串转换成图片
     *
     * @since 2016/5/24
     */
    public boolean GenerateImage(String imgStr, String filePath, String fileName) {
        try {
            if (imgStr == null) {
                return false;
            }
            BASE64Decoder decoder = new BASE64Decoder();
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            //如果目录不存在，则创建
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            //生成图片
            OutputStream out = new FileOutputStream(filePath + fileName);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    @RequestMapping(value = "/img")
    @ResponseBody
    public Object img(HttpServletRequest request, HttpServletResponse response){
        String basePath = "\\upload\\";
        String filePath = request.getSession().getServletContext().getRealPath("\\") + basePath;
        // String fileName = DateUtils.getDate("yyyyMMddHHmmss") + ".png";
        String fileName = (new Date()).getTime()+".png";
        //默认传入的参数带类型等参数：data:image/png;base64,
        String imgStr = request.getParameter("image");
        if (null != imgStr) {
            imgStr = imgStr.substring(imgStr.indexOf(",") + 1);
        }
        Boolean flag = GenerateImage(imgStr, filePath, fileName);
        System.out.println("..............."+filePath+".........."+fileName);
        String result = "";
        if (flag) {
            result = basePath + fileName;
        }
        System.out.println(JSON.toJSON(result)+"1111111111111111");
       return JSON.toJSON(result);
    }

    @RequestMapping(value = "/pd")
    @ResponseBody
    public  String pd(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("进来了.....................PD");
        String path = request.getParameter("path");
        System.out.println(path+"kkkkkkkkkkkkkkk");
        String pd=null;
        boolean result = false;
        Test ts=new Test();
        float [] ff=ts.pd(path);
        String s="D:\\fail.wav";
        if(ff.length>0){
            pd="1";
        }
        for (int i = 0; i < ff.length; i++) {
            if(ff[i]>85){
                result=true;
                break;
            }else{
                result=false;
            }
        }
        if(null!=pd){
            if(result){
                s="D:\\success.wav";
                Speak(s);
            }else{
                Speak(s);
            }
        }else{
            Speak(s);
        }


        return JSON.toJSONString(result);
    }

    public  void Speak(String s)throws Exception, IOException{
        AudioInputStream audioInputStream;// 文件流
        AudioFormat audioFormat;// 文件格式
        SourceDataLine sourceDataLine;// 输出设备
        File file = new File(s);


        // 取得文件输入流
        audioInputStream = AudioSystem.getAudioInputStream(file);
        audioFormat = audioInputStream.getFormat();
        // 转换文件编码
        if (audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
            audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    audioFormat.getSampleRate(), 16, audioFormat.getChannels(),
                    audioFormat.getChannels() * 2, audioFormat.getSampleRate(),
                    false);
            audioInputStream = AudioSystem.getAudioInputStream(audioFormat,
                    audioInputStream);
        }

        // 打开输出设备
        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,
                audioFormat, AudioSystem.NOT_SPECIFIED);
        sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
        sourceDataLine.open(audioFormat); // 打开具有指定格式的行，这样可以使行获得所有所需的系统资源并变得可操作
        sourceDataLine.start();  // 允许某一数据行执行数据I/O

        byte tempBuffer[] = new byte[320];
        try {
            int cnt;
            // 读取数据到缓存区
            // 从音频流读取指定的最大数量的数据字节，并将其放入给定的字节数组中。
            // return: 读入缓冲区的总字节数；如果因为已经到达流末尾而不再有更多数据，则返回-1
            while ((cnt = audioInputStream.read(tempBuffer, 0,
                    tempBuffer.length)) != -1) {
                if (cnt > 0) {
                    // 写入缓存数据
                    sourceDataLine.write(tempBuffer, 0, cnt); // 通过此源数据行将音频数据写入混频器
                }
            }
            // Block等待临时数据被输出为空
            // 通过在清空数据行的内部缓冲区之前继续数据I/O，排空数据行中的列队数据
            sourceDataLine.drain();
            // 关闭行，指示可以释放的该行使用的所有系统资源。如果此操作成功，则将行标记为 closed，并给行的侦听器指派一个 CLOSE 事件。
            sourceDataLine.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
