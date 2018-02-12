package alex.mario.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
    SpriteBatch batch;
    CameraSystem cs;
    MapSystem ms;

    Texture player;
    MapLayer playerLayer;
    TextureRegion textureRegion;

    Vector2 pos, dir;
    int vel = 4;

    Player(CameraSystem cs, MapSystem ms){
        this.cs = cs;
        this.ms = ms;
        batch = new SpriteBatch();
        player = new Texture("pokeTest.png");

        pos = new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
        dir = new Vector2(0,0);
    }

    void draw(){
        //Movimiento
        Vector2 comprobacionTemporal = pos.add(dir.cpy().scl(vel,vel));
        Rectangle rect = new Rectangle(comprobacionTemporal.x, comprobacionTemporal.y, player.getWidth(), player.getHeight());
        if(!ms.colisiono(rect)){
            pos.add(dir.cpy().scl(vel,vel)); //Movimiento básico
        }else{
            dir = new Vector2(0,0);
        }

        //Compruebo Triggers
        ms.comprobarTrigger(rect);

        //Pintado
        batch.begin();
        batch.setProjectionMatrix(cs.camera.combined);
        batch.draw(player, pos.x, pos.y);
        batch.end();

        //Actualizo la camara
        cs.setPosition(dir.cpy().scl(vel*2,vel*2));
    }

    void setDir(Vector2 dir){
        this.dir = dir;
    }
    void resetPos(){pos = new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);}

}
