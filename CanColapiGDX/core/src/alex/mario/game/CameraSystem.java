package alex.mario.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class CameraSystem {
    OrthographicCamera camera;

    CameraSystem(){
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
    }

    void setPosition(Vector2 pos){
        camera.translate(pos.x, pos.y);
        camera.update();
    }
}
