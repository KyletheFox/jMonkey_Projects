package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.shadow.DirectionalLightShadowRenderer;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(new Vector3f(-0.1f, 1f, 1f).normalizeLocal());
        rootNode.addLight(dl);  
        
        initCam();
        initLightShadows();
        
        Oto oto = new Oto(this);
        oto.setGroundSpeed(1.5f);
        
        Planet planet = new Planet(this);
        rootNode.attachChild(planet);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
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
}
