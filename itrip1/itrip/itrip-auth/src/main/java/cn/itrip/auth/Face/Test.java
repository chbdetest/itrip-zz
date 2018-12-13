package cn.itrip.auth.Face;

import java.io.File;

public class Test {

    
    public float[] pd(String path) {

        String[] str = {"jpg", "gif", "png", "jpeg"};
        System.out.println("锟斤拷取目录锟铰碉拷图片");
        File dir = new File("C:\\Users\\Administrator\\Desktop\\img");
        File[] files = dir.listFiles();
        float ff[] = new float[files.length];
        System.out.println(files.length + "```````````````````````");
        for (int i = 0; i < files.length; i++) {
            //锟斤拷锟剿凤拷图片
            String fileType = files[i].getName().substring(files[i].getName().lastIndexOf('.') + 1, files[i].getName().length());
            // System.out.println(files[i].getPath());
            System.out.println("第" + (i + 1) + "次");
            ff[i] = FaceMatch.match(path, files[i].getPath());

             /* for(int t=0;t<str.length;t++){
                 if(str[t].equals(fileType.toLowerCase())){


                 }
             }*/
        }
        return ff;
    }
}
