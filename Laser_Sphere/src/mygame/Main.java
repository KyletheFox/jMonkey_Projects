package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.debug.Arrow;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Line;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    
    initLaserSphere initLaser;
    LaserBall laserBall;
    
    Material matRed, matGreen, matBlue, matOrange, matArrow;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        initLaser = new initLaserSphere(this);
        
        addLaserBall();
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    public void addLaserBall() {
        laserBall = new LaserBall(this);
        rootNode.attachChild(laserBall);
    }

}
// --------------------------------------------------

