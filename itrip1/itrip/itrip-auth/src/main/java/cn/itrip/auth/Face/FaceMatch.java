package cn.itrip.auth.Face;
import cn.itrip.common.Base64Util;
import cn.itrip.common.FileUtil;
import cn.itrip.common.GsonUtils;
import cn.itrip.common.HttpUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 人脸对比
 */
public class FaceMatch {

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */

    // 修改前 public static String match(String img1,String img2) {
    public static float match(String img1,String img2) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/match";
        try {

            byte[] bytes1 = FileUtil.readFileByBytes(img1);
            byte[] bytes2 = FileUtil.readFileByBytes(img2);
            String image1 = Base64Util.encode(bytes1);
            String image2 = Base64Util.encode(bytes2);

            List<Map<String, Object>> images = new ArrayList<>();

            Map<String, Object> map1 = new HashMap<>();
            map1.put("image", image1);
            map1.put("image_type", "BASE64");
            map1.put("face_type", "LIVE");
            map1.put("quality_control", "LOW");
            map1.put("liveness_control", "NORMAL");

            Map<String, Object> map2 = new HashMap<>();
            map2.put("image", image2);
            map2.put("image_type", "BASE64");
            map2.put("face_type", "LIVE");
            map2.put("quality_control", "LOW");
            map2.put("liveness_control", "NORMAL");

            images.add(map1);
            images.add(map2);
            //  String param = JSONObject.toJSONString(images);
            String param = GsonUtils.toJson(images);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "24.e2e473c60eb77f5b72fc905c58dc3521.2592000.1546755740.282335-15056249";

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            JsonModel model=GsonUtils.fromJson(result, JsonModel.class);
            System.out.println("相识度"+model.result.score);
            return model.result.score;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

/*    public static void main(String[] args) {
    	String[] str = {"jpg","gif","png","jpeg"};
        System.out.println("获取目录下的图片");
        File dir =new File("C:\\Users\\Administrator\\Desktop\\img");
        File[] files = dir.listFiles();
        for(int i=0;i<files.length;i++){
            //过滤非图片
             String fileType = files[i].getName().substring(files[i].getName().lastIndexOf('.')+1,files[i].getName().length());
             for(int t=0;t<str.length;t++){
                 if(str[t].equals(fileType.toLowerCase())){
                    // System.out.println(files[i].getPath());
                	  System.out.println("第"+(i+1)+"次");
                     FaceMatch.match(files[i].getPath(),files[i].getPath());

                 }
             }

        }
    }*/





}