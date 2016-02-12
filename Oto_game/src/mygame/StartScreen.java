
package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;

/**
 *
 * @author Kyle O'Neill
 */
public class StartScreen extends AbstractAppState implements ActionListener{
    
    Main main;
    AppStateManager asm;
    BitmapText text;
    AppSettings s;
    boolean gameStarted = false;
    
    public void onAction(String name, boolean isPressed, float tpf) {
        if (isPressed) {
            if (name.equals("start") && !gameStarted) {
                System.out.println("Typed space");
                Game g = new Game();
                asm.detach(this);
                asm.attach(g);
                gameStarted = true;
            }
            if (name.equals("quit")) {
                System.exit(0);
            }
        }
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        main = (Main) app;
        asm = stateManager;
        s = main.getSettings();
        Main.clearJMonkey(main);
        
        main.getViewPort().setBackgroundColor(ColorRGBA.LightGray);
        
        // Start Screen Text 
        BitmapFont bmf = app.getAssetManager().loadFont("Interface/Fonts/Console.fnt");
        text = new BitmapText(bmf);
        text.setSize(bmf.getCharSet().getRenderedSize() * 10);
        text.setColor(ColorRGBA.Red);
        text.setText("Oto: The Game");
        text.setLocalTranslation(getCenterGui().add(-text.getLineWidth()/2f,s.getHeight()/4f,0));
        main.getGuiNode().attachChild(text);
        
        // set camera location
//        main.getFlyByCamera().setEnabled(false);
//        Node cameraTarget = new Node();
//        CameraNode camNode = new CameraNode("Camera Node", main.getCamera());
//        camNode.setLocalTranslation(new Vector3f(0f, 6f, 15f));
//        camNode.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
//        cameraTarget.attachChild(camNode);
//        main.getRootNode().attachChild(cameraTarget);
        
        // Input Listeners
        InputManager inputManager = main.getInputManager();
        inputManager.clearMappings();
        inputManager.addMapping("start", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("quit", new KeyTrigger(KeyInput.KEY_Q));
        inputManager.addListener(this, "start", "quit");
        
//        Game demoGame = new Game(); // true => demo mode
//        stateManager.attach(demoGame);

    }
    
    @Override
    public void cleanup() {
        main.getViewPort().setBackgroundColor(ColorRGBA.Black);
        Main.clearJMonkey(main);
    }

     @Override
    public void update(float tpf) {
        
    }
     
    private Vector3f getCenterGui() {
        return new Vector3f((s.getWidth())/2, s.getHeight()/2, 0);
    }
}
