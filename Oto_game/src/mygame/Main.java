package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.CollisionResults;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.shape.Box;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.system.AppSettings;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    
    SimpleApplication sa = this;
    Node PlanetNode;
    ObjectField objField;
    Oto oto;
    PlanetSurface planet;
    float time = 0;
    
    public static void main(String[] args) {
        Main app = new Main();
        AppSettings settings = new AppSettings(true);
        initAppScreen(app);
        app.setShowSettings(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        
          initCam();
          initLightShadows();
          initGUI();
          
          StartScreen s = new StartScreen();
          stateManager.attach(s);
    }

    @Override
    public void simpleUpdate(float tpf) {
       time += tpf;
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    protected static void clearJMonkey(Main m) {
        m.guiNode.detachAllChildren();
        m.rootNode.detachAllChildren();
        m.inputManager.clearMappings();
    }
    
    public void initCam() {
        flyCam.setEnabled(true);
        cam.lookAt(new Vector3f(0, -5, 0), Vector3f.UNIT_Y); 
        cam.setLocation(new Vector3f(0f, 15f, 15f));
        flyCam.setMoveSpeed(10f);
        flyCam.setRotationSpeed(5f);
    }
    
    public void initLightShadows() {
       
        // Light1: white, directional
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.7f, -1.3f, -0.9f)).normalizeLocal());
        sun.setColor(ColorRGBA.Gray);
        rootNode.addLight(sun);

        // Light 2: Ambient, gray
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(new ColorRGBA(0.7f, 0.7f, 0.7f, 1.0f));
        rootNode.addLight(ambient);

        // SHADOW
        // the second parameter is the resolution. Experiment with it! (Must be a power of 2)
        DirectionalLightShadowRenderer dlsr = new DirectionalLightShadowRenderer(assetManager, 512, 1);
        dlsr.setLight(sun);
        viewPort.addProcessor(dlsr);
    
    }
    
    private static void initAppScreen(SimpleApplication app) {
        AppSettings aps = new AppSettings(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        screen.width *= 0.75;
        screen.height *= 0.75;
        aps.setResolution(screen.width, screen.height);
        app.setSettings(aps);
        app.setShowSettings(false);
    }
    
    private void initGUI() {
        setDisplayStatView(false);
        setDisplayFps(true);
    }
    
     public AppSettings getSettings() {
        return (settings);
    }
    
//    public class PlanetControl extends AbstractControl {
//
//        @Override
//        protected void controlUpdate(float tpf) {
//            time += tpf;   
//            Quaternion q = new Quaternion();
//            q.fromAngleAxis(time * 0.12f, Vector3f.UNIT_X);
//            this.spatial.setLocalRotation(q);
//            
//            if (FastMath.floor(time * 0.12f) == 260f * FastMath.DEG_TO_RAD) {
//                PlanetNode.detachChildAt(1);
//                ObjectField field = new ObjectField(sa,150);
//                PlanetNode.attachChild(field);
//            }
//        }
//
//        @Override
//        protected void controlRender(RenderManager rm, ViewPort vp) {
//        }
//        
//    };
    
    
}
