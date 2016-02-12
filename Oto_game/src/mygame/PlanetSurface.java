/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author ko163
 */
public class PlanetSurface extends Node {
    
    public PlanetSurface(SimpleApplication sa) {
        Sphere planet = new Sphere(200, 200, 200f);
        Geometry gPlanet = new Geometry("Planet", planet);
        Material mPlanet = new Material(sa.getAssetManager(),
                "Common/MatDefs/Misc/Unshaded.j3md");
        mPlanet.setTexture("ColorMap", sa.getAssetManager().loadTexture("Textures/planet.jpg"));
        gPlanet.setMaterial(mPlanet);
        //gPlanet.rotate(0, 0, 90f * FastMath.DEG_TO_RAD);
        this.attachChild(gPlanet);
    }
}
