package game;

import javafx.geometry.Point2D;
import observers.DisplaySizeSubject;

import java.awt.Point;

public class Tree extends GraphicalObject {

   public Tree (Point2D position, double width, double height, DisplaySizeSubject displaySize ) {
      super ( position, width, height, displaySize );
      this.setTexture ( TEXTURE_TREE );
   }
}
