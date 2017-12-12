/*
 * Copyright (C) 2012 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */
package view;

import java.awt.Color;
import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;

import gov.nasa.worldwind.Configuration;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.globes.EarthFlat;
import gov.nasa.worldwind.layers.LatLonGraticuleLayer;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.Path;
import gov.nasa.worldwind.render.ShapeAttributes;
import gov.nasa.worldwind.util.WWUtil;
import gov.nasa.worldwindx.examples.ShapeEditingExtension.Arrow;
import model.WindBarb;

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
public class FlatWorld extends ApplicationTemplate {
  protected static final String SURFACE_POLYGON_IMAGE_PATH = "gov/nasa/worldwindx/examples/images/georss.png";

  public static class AppFrame extends ApplicationTemplate.AppFrame {
    public AppFrame() {
      this.makePaths();
    }

    protected void makePaths() {
      insertBeforePlacenames(getWwd(), new LatLonGraticuleLayer());
      RenderableLayer layer = new RenderableLayer();
      layer.setName("Paths");
      ArrayList<RenderableLayer> layers = new ArrayList<>();
      layers.add(layer);
      List l = new List();
      l.add(layer.getName());
      this.getControlPanel().add(l);

      // Path over Florida.
      ShapeAttributes attrs = new BasicShapeAttributes();
      attrs.setOutlineMaterial(Material.GREEN);
      attrs.setInteriorOpacity(0.5);
      attrs.setOutlineOpacity(0.8);
      attrs.setOutlineWidth(3);

      double originLat = 28;
      double originLon = -82;
      Iterable<Position> locations = Arrays.asList(Position.fromDegrees(originLat + 5.0, originLon + 2.5, 100e3),
          Position.fromDegrees(originLat + 5.0, originLon - 2.5, 100e3),
          Position.fromDegrees(originLat + 2.5, originLon - 5.0, 100e3),
          Position.fromDegrees(originLat - 2.5, originLon - 5.0, 100e3),
          Position.fromDegrees(originLat - 5.0, originLon - 2.5, 100e3),
          Position.fromDegrees(originLat - 5.0, originLon + 2.5, 100e3),
          Position.fromDegrees(originLat - 2.5, originLon + 5.0, 100e3),
          Position.fromDegrees(originLat + 2.5, originLon + 5.0, 100e3),
          Position.fromDegrees(originLat + 5.0, originLon + 2.5, 100e3));
      Path shape = new Path(locations);
      shape.setAttributes(attrs);
      layer.addRenderable(shape);

      Arrow arr = new Arrow(LatLon.fromDegrees(48.390394, -4.486076), LatLon.fromDegrees(48.390000, -4.486000),
          50000.0);
      attrs.setImageScale(0.5);
      arr.setAttributes(attrs);
      layer.addRenderable(arr);

      RenderableLayer symbolLayer = new RenderableLayer();
      symbolLayer.setName("Tactical Symbols");

      // Test barbs
      WindBarb b1 = new WindBarb(Position.fromDegrees(48.390394, -4.486076, 3000), 1.0, 20.0);
      layer.addRenderable(b1);

      locations = Arrays.asList(Position.fromDegrees(20, -170, 100e3), Position.fromDegrees(15, 170, 100e3),
          Position.fromDegrees(10, -175, 100e3), Position.fromDegrees(5, 170, 100e3),
          Position.fromDegrees(0, -170, 100e3), Position.fromDegrees(20, -170, 100e3));
      shape = new Path(locations);
      shape.setAttributes(attrs);
      layer.addRenderable(shape);

      // Path around the north pole.
      attrs = new BasicShapeAttributes();
      attrs.setOutlineMaterial(new Material(WWUtil.makeColorBrighter(Color.GREEN)));
      attrs.setInteriorOpacity(0.5);
      attrs.setOutlineOpacity(0.8);
      attrs.setOutlineWidth(3);

      locations = Arrays.asList(Position.fromDegrees(80, 0, 100e3), Position.fromDegrees(80, 90, 100e3),
          Position.fromDegrees(80, 180, 100e3),
          // Position.fromDegrees(80, -180, 100e3),
          Position.fromDegrees(80, -90, 100e3), Position.fromDegrees(80, 0, 100e3));
      shape = new Path(locations);
      shape.setAttributes(attrs);
      layer.addRenderable(shape);

      // this.controlPanel.add(new view.LayerPanel(getWwd()), BorderLayout.SOUTH);
      ApplicationTemplate.insertBeforePlacenames(this.getWwd(), layer);

      // Initialise point of view
      getWwd().getView().setEyePosition(Position.fromDegrees(48.39039, -4.486076, 42000));
    }
  }

  public static void main(String[] args) {
    // Adjust configuration values before instantiation
    Configuration.setValue(AVKey.GLOBE_CLASS_NAME, EarthFlat.class.getName());
    Configuration.setValue(AVKey.PROJECTION_NAME,
        gov.nasa.worldwind.globes.projections.ProjectionMercator.class.getName());
    start("World Wind Flat World", AppFrame.class);
  }
}
