package fr.soudepriezleroux.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import fr.soudepriezleroux.entity.EntityManager;
import lombok.Getter;

public class Map {

    //La texture de la map
    private final Sprite sprite;

    public Map() {
        //Import et mise a la bonne taille et position de la texture de la map
        this.sprite = new Sprite(new Texture(Gdx.files.internal("img.png")),81,93);
        sprite.setSize(810,930);
        sprite.setPosition(0,0);
    }

    //Affichage de la texture de la map
    public void render(){
        sprite.draw(EntityManager.getBatch());
    }

    //Liberation des ressources de la texture
    public void dispose(){
        sprite.getTexture().dispose();
    }

}
