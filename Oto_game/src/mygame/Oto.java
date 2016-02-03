package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

/**
 *
 * @author Rolf
 */
public class Oto {

    SimpleApplication sa;
    private AnimChannel channel;
    private AnimControl control;
    private String requestedState = "";
    Node otoNode;
    Control otoControl;
    private float groundSpeed = 0.0f;
    float left = 0;
    float right = 0;
    //
    // -------------------------------------------------------------------------
    // the key action listener: set requested state
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (keyPressed) {
                requestedState = name;
            }
        }
    };
    private AnalogListener analogListener = new AnalogListener() {
        final float SPEED_SCALE = 6.0f;

        public void onAnalog(String name, float value, float tpf) {
            if (name.equals("Left") && left < 1) {
                left += tpf;
                right -= tpf;
                otoNode.setLocalTranslation(otoNode.getLocalTranslation().addLocal(-tpf * SPEED_SCALE, 0, 0));
            }
            if (name.equals("Right") && right < 1) {
                right += tpf;
                left -= tpf;
                otoNode.setLocalTranslation(otoNode.getLocalTranslation().addLocal(tpf * SPEED_SCALE, 0, 0));
            }
        }
    };

    // -------------------------------------------------------------------------
    public Oto(SimpleApplication sa) {
        this.sa = sa;
        initKeys();
        initModel();
    }

    // -------------------------------------------------------------------------  
    // set ground speed. Used for walking adjustment in control.
    public void setGroundSpeed(float spd) {
        this.groundSpeed = spd;
    }

    // -------------------------------------------------------------------------  
    // Custom Keybindings: Mapping a named action to a key input.
    private void initKeys() {
        sa.getInputManager().addMapping("Left", new KeyTrigger(KeyInput.KEY_J));
        sa.getInputManager().addMapping("Right", new KeyTrigger(KeyInput.KEY_L));
        sa.getInputManager().addMapping("Push", new KeyTrigger(KeyInput.KEY_V));
        sa.getInputManager().addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        sa.getInputManager().addListener(actionListener, new String[]{"Push", "Jump"});
        sa.getInputManager().addListener(analogListener, "Left", "Right");
    }

    // -------------------------------------------------------------------------
    // init model
    // load a model that contains animation
    private void initModel() {
        otoNode = (Node) sa.getAssetManager().loadModel("Models/Oto/Oto.mesh.xml");
        otoNode.setLocalScale(0.5f);
        otoNode.setLocalTranslation(0, 2.5f, 0f);
        otoNode.rotate(0, 180f * FastMath.DEG_TO_RAD, 0);
        sa.getRootNode().attachChild(otoNode);
        //
        // Create a controller and channels.
        control = otoNode.getControl(AnimControl.class);
        channel = control.createChannel();
        channel.setAnim("stand");
        //
        // add control
        otoControl = new OtoControl();
        otoNode.addControl(otoControl);
    }

    // -------------------------------------------------------------------------
    // OtoControl
    class OtoControl extends AbstractControl {

        private final int STATE_WALK = 0;
        private final int STATE_STAND = 1;
        private final int STATE_JUMP = 2;
        private int state;
        private boolean stateIsInitialized = false;
        private float stateTime;

        public OtoControl() {
            switchState(STATE_STAND);
        }

        // ---------------------------------------------------------------------
        @Override
        protected void controlUpdate(float tpf) {
            stateTime += tpf;
            // state machine
            String reqState = requestedState;
            requestedState = "";

            // just for debugging purpose: toggle ground speed
            if (reqState.equals("Push")) {
                groundSpeed = groundSpeed > 0 ? 0 : 1.0f;
            }

            // ----------------------------------------
            switch (state) {
                case (STATE_STAND):
                    if (!stateIsInitialized) {
                        stateIsInitialized = true;
                        channel.setAnim("stand", 0.0f);
                    }
                    if (reqState.equals("Jump")) {
                        switchState(STATE_JUMP);
                    }//
                    // if the earth spins, immediately switch to walk.
                    else if (groundSpeed > 0.0f) {
                        switchState(STATE_WALK);
                    }
                    break;
                case (STATE_JUMP):
                    final float JUMP_HEIGHT = 6f;
                    System.out.println(otoNode.getLocalTranslation());
                    if (!stateIsInitialized) {
                        stateIsInitialized = true;
                        channel.setAnim("pull");
                    }
                    // Jump
                    float y = JUMP_HEIGHT * FastMath.sin(stateTime * 5);
                    otoNode.setLocalTranslation(otoNode.getWorldTranslation().x, y + 2.5f, 0);
                    //
                    // end of state?
                    if (y <= 0.0f) {
                        otoNode.setLocalTranslation(otoNode.getWorldTranslation().x, 2.5f, 0);
                        switchState(STATE_STAND);
                    }
                    break;
                case (STATE_WALK):
                    if (!stateIsInitialized) {
                        stateIsInitialized = true;
                        channel.setAnim("Walk");
                        channel.setSpeed(groundSpeed);
                    }
                    // state action: adjust to groundspeed
                    channel.setSpeed(groundSpeed);
                    //
                    // end of state?
                    if (groundSpeed == 0.0f) {
                        switchState(STATE_STAND);
                    }
                    if (reqState.equals("Jump")) {
                        switchState(STATE_JUMP);
                    }
                    break;
            }
        }

        @Override
        protected void controlRender(RenderManager rm, ViewPort vp) {
        }

        // ---------------------------------------------------------------------
        private void switchState(int state) {
            stateIsInitialized = false;
            this.state = state;
            stateTime = 0.0f;
        }
    }
}
