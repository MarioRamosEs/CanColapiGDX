package alex.mario.game.GUI;

import alex.mario.game.Interfaces.iSystem;
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
    public static HashMap<String, Music> soundsPlaying = new HashMap<String, Music>();

    public SoundsSystem() {
        textureFormats.add("mp3");
        loadSounds();
    }

    public static Music getMusic(String soundName) {
        return sounds.get(soundName);
    }

    public static void loadSounds() {
        loadSoundsFolder(folder);
    }

    private static void loadSoundsFolder(File path) {
        for (final File file : path.listFiles()) {
            if (file.isDirectory()) {
                loadSoundsFolder(file);//Recursividad cargar archivos de la carpeta encontrada
            } else {
                if (textureFormats.contains(getExtension(file.getName()))) {
                    //Format available
                    Music temp = Gdx.audio.newMusic(Gdx.files.internal(file.getAbsolutePath()));
                    preLoad(temp);
                    sounds.put(file.getName(), temp);
                }
            }
        }
    }

    public static String getExtension(String fileName) {
        //Source: https://stackoverflow.com/a/3571239/6832219
        String extension = "";

        int i = fileName.lastIndexOf('.');
        int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

        if (i > p) {
            extension = fileName.substring(i + 1);
        }
        return extension;
    }

    public void play(String sound) {
        cleanSoundsPlaying();
        if (!soundsPlaying.containsKey(sound)) { //No solapar dos veces el mismo audio
            Music tempSound = SoundsSystem.getMusic(sound);
            tempSound.play();
            soundsPlaying.put(sound, tempSound);
        }
    }

    private static void preLoad(Music temp) {
        temp.setVolume(0);
        temp.play();
        temp.stop();
        temp.setVolume(1);
    }

    public void playLoop(String sound) {
        cleanSoundsPlaying();
        if (!soundsPlaying.containsKey(sound)) { //No solapar dos veces el mismo audio
            Music tempSound = SoundsSystem.getMusic(sound);
            tempSound.setLooping(true);
            tempSound.play();
            soundsPlaying.put(sound, tempSound);
        }
    }

    public void stop(String sound) {
        cleanSoundsPlaying();
        if (soundsPlaying.containsKey(sound)) {
            Music tempSound = soundsPlaying.get(sound);
            tempSound.stop();
            soundsPlaying.remove(sound);
        }
    }

    public void stopAll() {
        cleanSoundsPlaying();

        ArrayList<String> cue = new ArrayList<>();
        for (Map.Entry<String, Music> entry : soundsPlaying.entrySet()) {
            String key = entry.getKey();

            Music tempSound = soundsPlaying.get(key);
            tempSound.stop();
            cue.add(key);
        }
        remove(cue);
    }

    private void cleanSoundsPlaying() { //Elimina los audios que no están sonando
        ArrayList<String> cue = new ArrayList<>();
        for (Map.Entry<String, Music> entry : soundsPlaying.entrySet()) {
            String key = entry.getKey();
            Music tempSound = soundsPlaying.get(key);

            if (!tempSound.isPlaying()) cue.add(key); //soundsPlaying.remove(key);
        }
        remove(cue);
    }

    private void remove(ArrayList<String> cue) {
        for (String element : cue) {
            soundsPlaying.remove(element); //dispose
        }
    }

}
