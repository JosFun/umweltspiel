package game;

import javafx.geometry.Point2D;
import observers.DisplaySizeSubject;

import java.awt.Point;

public class Tree extends GraphicalObject {
   private static final double NATIVE_TREE_HEIGHT = 80;
   private static final double NATIVE_TREE_WIDTH = 45;

   public Tree (Point2D position, DisplaySizeSubject displaySize ) {
      super ( position, NATIVE_TREE_WIDTH, NATIVE_TREE_HEIGHT, displaySize );
   }

   @Override
   public void determineTexture ( ) {
      this.setTexture ( TEXTURE_TREE );
   }
}
