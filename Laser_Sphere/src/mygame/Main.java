package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.Arrow;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Line;
import com.jme3.util.SkyFactory;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    
    initLaserSphere initLaser;
    LaserBall laserBall;
    Mosquito[] theSwarm;
    Lasers laserBeams;
    SingleBurstParticleEmitter explosion;
    float time = 0;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        initLaser = new initLaserSphere(this);
        
        addLaserBall();
        addMosquitos();
        addLasers();
        addSky();
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
        time += tpf;
        laserBall.updateSize(tpf);
        //rootNode.getChild(22).setLocalRotation(new Quaternion(1f, 0f, 0f, time));
        
        for(int i=0; i < theSwarm.length; i++) {
            if (theSwarm[i].position.length() <= 2) {
                explosion = new SingleBurstParticleEmitter(this, rootNode, theSwarm[i].position);
                theSwarm[i].detachAllChildren();
                laserBeams.fireLasers(theSwarm[i].position);
                Mosquito bug = new Mosquito(this);
                theSwarm[i] = bug;
                rootNode.attachChild(bug);
            }
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    public void addLaserBall() {
        laserBall = new LaserBall(this);
        rootNode.attachChild(laserBall);
    }
    
    public void addMosquitos() {
        Node swarmNode = new Node();
        theSwarm = new Mosquito[20];
        
        for(int i=0; i < theSwarm.length; ++i) {
            Mosquito bug = new Mosquito(this);
            theSwarm[i] = bug;
            swarmNode.attachChild(bug);
        }
        
        rootNode.attachChild(swarmNode);
        
    }
    
    public void addLasers() {
        laserBeams = new Lasers(this);
        rootNode.attachChild(laserBeams);
    }
    
    public void addSky() {
        rootNode.attachChild(SkyFactory.createSky(
            assetManager, "Textures/Sky/Bright/BrightSky.dds", false));
    }

}
// --------------------------------------------------

