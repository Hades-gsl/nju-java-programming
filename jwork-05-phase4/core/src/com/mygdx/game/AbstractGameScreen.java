package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.bullet.Bullet;
import com.mygdx.character.Enemy;
import com.mygdx.character.Hero;
import com.mygdx.config.Config;
import com.mygdx.matrix.Map;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This abstract class represents a game screen. It extends the BaseScreen class. It contains common
 * properties and methods for game screens.
 *
 * @author Hades
 */
public abstract class AbstractGameScreen extends BaseScreen {
  protected List<Texture> heroTextures;
  protected List<Texture> enemyTextures;
  protected Texture bulletTexture;
  protected List<Hero> heroes;
  protected List<Enemy> enemies;
  protected List<Bullet> bullets;
  protected Map map;

  /**
   * Constructor for the AbstractGameScreen class. It initializes the game and textures.
   *
   * @param game The game instance.
   */
  public AbstractGameScreen(MyGdxGame game) {
    super(game);

    initTexture();
  }

  /**
   * This method initializes the textures for the heroes, enemies and bullets. It loads the textures
   * from the files specified in the Config class.
   */
  protected void initTexture() {
    heroTextures = new CopyOnWriteArrayList<>();
    enemyTextures = new CopyOnWriteArrayList<>();
    bulletTexture = new Texture(Gdx.files.internal(Config.BULLET_PATH));
    for (int i = 0; i < Config.INIT_HERO_COUNT; i++) {
      heroTextures.add(
          new Texture(Gdx.files.internal(Config.HERO_PATH + " (" + (i + 1) + ").png")));
    }
    for (int i = 0; i < Config.INIT_ENEMY_COUNT; i++) {
      enemyTextures.add(
          new Texture(Gdx.files.internal(Config.ENEMY_PATH + " (" + (i + 1) + ").png")));
    }
  }

  /**
   * This method loads the textures for the heroes, enemies and bullets. It sets the textures and
   * sprites for the heroes, enemies and bullets. It also sets the sound for the bullets.
   */
  protected void loadTexture() {
    for (int i = 0; i < heroes.size(); i++) {
      Hero hero = heroes.get(i);
      hero.setBulletTexture(bulletTexture);
      hero.setCharaterTexture(heroTextures.get(i));
      hero.setSprite(new Sprite(heroTextures.get(i)));
      hero.setDieTexture(new Texture(Gdx.files.internal(Config.DIE_PATH)));
      if (hero.getHp() <= 0) {
        hero.changeDieTexture();
      }
    }
    for (int i = 0; i < enemies.size(); i++) {
      Enemy enemy = enemies.get(i);
      enemy.setBulletTexture(bulletTexture);
      enemy.setCharaterTexture(enemyTextures.get(i));
      enemy.setSprite(new Sprite(enemyTextures.get(i)));
      enemy.setDieTexture(new Texture(Gdx.files.internal(Config.DIE_PATH)));
      if (enemy.getHp() <= 0) {
        enemy.changeDieTexture();
      }
    }
    for (Bullet bullet : bullets) {
      bullet.setSprite(new Sprite(bulletTexture));
      bullet.getSprite().setRotation(bullet.getRotation());
      bullet.setSound(Gdx.audio.newSound(Gdx.files.internal(Config.SHOOT_PATH)));
    }
  }
}
