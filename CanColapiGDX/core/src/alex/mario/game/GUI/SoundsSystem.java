package alex.mario.game.GUI;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SoundsSystem {
    public final static File folder = new File(System.getProperty("user.dir"));
    public static HashMap<String, Music> sounds = new HashMap<String, Music>();
    public static ArrayList<String> textureFormats = new ArrayList<String>();

    public SoundsSystem(){
        textureFormats.add("mp3");
        loadTextures();
    }

    public static Music getMusic(String soundName){
        return sounds.get(soundName);
    }
    public static void loadTextures(){
        loadTexturesFolder(folder);
    }
    private static void loadTexturesFolder(File path){
        for (final File file : path.listFiles()) {
            if (file.isDirectory()) {
                loadTexturesFolder(file);//Recursividad cargar archivos de la carpeta encontrada
            } else {
                if(textureFormats.contains(getExtension(file.getName()))){
                    //Format available
                    Music temp = Gdx.audio.newMusic(Gdx.files.internal(file.getAbsolutePath()));
                    preLoad(temp);
                    sounds.put(file.getName(), temp);
                }
            }
        }
    }
    public static String getExtension(String fileName){
        //Source: https://stackoverflow.com/a/3571239/6832219
        String extension = "";

        int i = fileName.lastIndexOf('.');
        int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

        if (i > p) {
            extension = fileName.substring(i+1);
        }
        return extension;
    }

    private static void preLoad(Music temp){
        temp.setVolume(0);
        temp.play();
        temp.stop();
        temp.setVolume(1);
    }
}
