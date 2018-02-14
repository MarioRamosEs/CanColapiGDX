package alex.mario.game.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class CameraSystem {
    OrthographicCamera camera;
    float w = Gdx.graphics.getWidth();
    float h = Gdx.graphics.getHeight();
    private MyGdxGame game;
    CameraSystem(MyGdxGame game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
    }

    void setPosition(Vector2 pos){
        camera.translate(pos.x, pos.y);
        camera.update();
    }
    void update(){
        //Actualizo la camara
        this.setPosition(this.game.getPlayer().getPosition());
    }
    void resetPosition(){
        camera.setToOrtho(false,w,h);
    }
}
