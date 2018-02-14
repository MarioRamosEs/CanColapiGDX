package alex.mario.game.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class CameraSystem {
    OrthographicCamera camera;
    float w = Gdx.graphics.getWidth();
    float h = Gdx.graphics.getHeight();
    private MyGdxGame game;
    private int zoom = 0;
    CameraSystem(MyGdxGame game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
    }

    void setPosition(Vector2 pos){
        //camera.translate(pos.x, pos.y);

        //Cambio la posición de la cámara al valor introducido más el zoom (Vector3)
        camera.position.set(new Vector3(pos.x, pos.y, this.zoom));
    }

    void update(){
         //Actualizo la posición de la cámara respecto a la posición del jugador
        this.setPosition(this.game.getPlayer().getPosition());

        //Se actualiza la cámara Orthographic
        this.camera.update();
    }
    void resetPosition(){
        camera.setToOrtho(false,w,h);
    }
}
