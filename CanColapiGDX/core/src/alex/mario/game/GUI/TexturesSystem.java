package alex.mario.game.GUI;

import com.badlogic.gdx.graphics.Texture;

import java.io.File;
import java.io.FilenameFilter;
import java.net.FileNameMap;
import java.nio.file.spi.FileTypeDetector;
import java.util.ArrayList;
import java.util.HashMap;

public class TexturesSystem {
    public final static File folder = new File(System.getProperty("user.dir"));
    public static HashMap<String, Texture> textures = new HashMap<String, Texture>();
    public static ArrayList<String> textureFormats = new ArrayList<String>(){{add("png");}};
    public static Texture getTexture(String textureName){
        return textures.get(textureName);
    }
    public static void loadTextures(){
        loadTexturesFolder(folder);
    }
    private static void loadTexturesFolder(File path){
        for (final File file : path.listFiles()) {
            if (file.isDirectory()) {
                loadTexturesFolder(file);
            } else {
                if(textureFormats.contains(getExtension(file.getName()))){
                    //Format available
                    textures.put(file.getName(), new Texture(file.getAbsolutePath()));
                }
            }
        }
    }
    public static String getExtension(String fileName){
        String extension = "";

        int i = fileName.lastIndexOf('.');
        int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

        if (i > p) {
            extension = fileName.substring(i+1);
        }
        return extension;
    }
}
