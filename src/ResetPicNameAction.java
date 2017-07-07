import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.xml.actions.xmlbeans.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by Administrator on 2017/7/7 0007.
 */
public class ResetPicNameAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        VirtualFile selectedFile = DataKeys.VIRTUAL_FILE.getData(e.getDataContext());
        File[] files = new File(selectedFile.getPath()).listFiles();
        for(int i = 0;i<files.length;i++){
            File file = files[i];
            if(file.getName().endsWith(".png")
                    ||file.getName().endsWith(".jpg")
                    ||file.getName().endsWith(".jpeg")){
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    BufferedImage sourseImg=  ImageIO.read(fileInputStream);
                    String name = file.getName();
                    String suffix = name.substring(name.lastIndexOf("."));
                    if(name.contains("@")){
                        //
                        name = name.substring(0,name.indexOf("@"));
                        System.out.println(name);
                    }else{
                        name = name.substring(0,name.indexOf("."));
                        System.out.println(name);
                    }
                    String picSize;
                    if(sourseImg.getWidth() == sourseImg.getHeight()){
                        picSize="_"+sourseImg.getWidth()+"px";
                    }else{
                        picSize=sourseImg.getWidth()+"px_"+sourseImg.getHeight()+"px";
                    }
                    fileInputStream.close();
                    if(name.contains(picSize)){
                        continue;
                    }
                    name += picSize+suffix;
                    System.out.println(file.getAbsolutePath());
                    System.out.println(file.getParent()+"\\"+name);

                    file.renameTo(new File(file.getParent() + "\\" + name));

                } catch (Exception e1) {
                    e1.printStackTrace();
                    System.out.println("Exception:"+e1);
                }
            }
        }
    }

}
