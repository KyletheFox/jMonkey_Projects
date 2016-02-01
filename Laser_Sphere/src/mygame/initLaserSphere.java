/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Line;
import com.jme3.system.AppSettings;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author kyle
 */
public class initLaserSphere {
    SimpleApplication sa;
    public static Material matRed, matGreen, matBlue;
    
    public initLaserSphere(SimpleApplication sa) {
        this.sa = sa;
        initAppScreen(sa);
        initGUI();
        initMaterials();
        initCoordCross();
        initCam();
    }
    
     public static void initAppScreen(SimpleApplication app) {
        AppSettings aps = new AppSettings(true);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        screen.width *= 0.75;
        screen.height *= 0.75;
        aps.setResolution(screen.width, screen.height);
        app.setSettings(aps);
        app.setShowSettings(false);
    }

    
     private void initGUI() {
        //setDisplayFps(false);
        sa.setDisplayStatView(false);
        sa.getFlyByCamera().setEnabled(false);
    }
    
    
     private void initCoordCross() {
        Line xAxis = new Line(new Vector3f(3, 0, 0), Vector3f.ZERO);
        Line yAxis = new Line(new Vector3f(0, 3, 0), Vector3f.ZERO);
        Line zAxis = new Line(new Vector3f(0, 0, 3), Vector3f.ZERO);
        Geometry geomX = new Geometry("xAxis", xAxis);
        Geometry geomY = new Geometry("yAxis", yAxis);
        Geometry geomZ = new Geometry("zAxis", zAxis);
        geomX.setMaterial(matRed);
        geomY.setMaterial(matGreen);
        geomZ.setMaterial(matBlue);
        sa.getRootNode().attachChild(geomX);
        sa.getRootNode().attachChild(geomY);
        sa.getRootNode().attachChild(geomZ);
    }

    private void initMaterials() {
        matRed = new Material(sa.getAssetManager(),
                "Common/MatDefs/Misc/Unshaded.j3md");
        matRed.setColor("Color", ColorRGBA.Red);
        matGreen = new Material(sa.getAssetManager(),
                "Common/MatDefs/Misc/Unshaded.j3md");
        matGreen.setColor("Color", ColorRGBA.Green);
        matBlue = new Material(sa.getAssetManager(),
                "Common/MatDefs/Misc/Unshaded.j3md");
        matBlue.setColor("Color", ColorRGBA.Blue);
    }
    
     private void initCam() {
        sa.getFlyByCamera().setEnabled(true);
        sa.getCamera().setLocation(new Vector3f(5f, 5f, 5f));
        sa.getCamera().lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        sa.getFlyByCamera().setMoveSpeed(10f);
        sa.getFlyByCamera().setRotationSpeed(5f);
    }

}
