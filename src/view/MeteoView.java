package view;

/*
 * Copyright (C) 2012 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

import java.awt.Color;
import java.util.ArrayList;

import gov.nasa.worldwind.Configuration;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.globes.EarthFlat;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.SurfacePolyline;
import gov.nasa.worldwind.util.WWUtil;

/**
 * Example of displaying a flat globe instead of a round globe. The flat globe
 * displays elevation in the same way as the round globe (mountains rise out of
 * the globe). One advantage of using a flat globe is that a user can see the
 * entire globe at once. The globe can be configured with different map
 * projections.
 *
 * @author Patrick Murris
 * @version $Id: FlatWorld.java 2219 2014-08-11 21:39:44Z dcollins $
 * @see gov.nasa.worldwind.globes.FlatGlobe
 * @see EarthFlat
 */
public class MeteoView extends ApplicationTemplate {
  protected static final String SURFACE_POLYGON_IMAGE_PATH = "gov/nasa/worldwindx/examples/images/georss.png";

  public static class AppFrame extends ApplicationTemplate.AppFrame {
    public AppFrame() {
      this.makePaths();
      this.makeSurfaceShapes();
    }

    protected void makePaths() {

      // ApplicationTemplate.insertBeforePlacenames(this.getWwd(), layer);
    }

    protected void makeSurfaceShapes() {
      RenderableLayer lineLayer = new RenderableLayer();
      lineLayer.setName("Surface Shapes");
      ArrayList<LatLon> points = new ArrayList<LatLon>();
      points.add(LatLon.fromDegrees(48.400434, -4.501203));
      points.add(LatLon.fromDegrees(48.390413, -4.486005));

      SurfacePolyline line1 = new SurfacePolyline(points);
      BasicShapeAttributes attrs = new BasicShapeAttributes();
      attrs.setInteriorMaterial(Material.CYAN);
      attrs.setOutlineMaterial(new Material(WWUtil.makeColorBrighter(Color.CYAN)));
      attrs.setInteriorOpacity(0.5);
      attrs.setOutlineOpacity(0.8);
      attrs.setOutlineWidth(3);
      line1.setAttributes(attrs);

      lineLayer.addRenderable(line1);

      // Surface sector over Lake Tahoe.
      /*
       * attrs = new BasicShapeAttributes(); attrs.setInteriorMaterial(Material.CYAN);
       * attrs.setOutlineMaterial(new Material(WWUtil.makeColorBrighter(Color.CYAN)));
       * attrs.setInteriorOpacity(0.5); attrs.setOutlineOpacity(0.8);
       * attrs.setOutlineWidth(3);
       * 
       * shape = new SurfaceSector(new Sector(Angle.fromDegrees(38.9),
       * Angle.fromDegrees(39.3), Angle.fromDegrees(-120.2),
       * Angle.fromDegrees(-119.9))); shape.setAttributes(attrs);
       * layer.addRenderable(shape);
       */

      // Surface polyline spanning the international dateline.
      /*
       * attrs = new BasicShapeAttributes(); attrs.setOutlineMaterial(Material.RED);
       * attrs.setOutlineWidth(3); attrs.setOutlineStippleFactor(2);
       * 
       * locations = Arrays.asList(LatLon.fromDegrees(-10, 170),
       * LatLon.fromDegrees(-10, -170)); shape = new SurfacePolyline(locations);
       * shape.setAttributes(attrs); ((SurfacePolyline) shape).setClosed(false);
       * layer.addRenderable(shape);
       */

      ApplicationTemplate.insertBeforePlacenames(this.getWwd(), lineLayer);
    }
  }

  public static void main(String[] args) {
    // Adjust configuration values before instantiation
    Configuration.setValue(AVKey.GLOBE_CLASS_NAME, EarthFlat.class.getName());
    start("World Wind Flat World", AppFrame.class);
  }
}
