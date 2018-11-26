package game;

import observers.DisplaySizeSubject;

import java.awt.Point;

public class Tree extends GraphicalObject implements Textures {

   public Tree (Point position, double width, double height, DisplaySizeSubject displaySize ) {
      super ( position, width, height, displaySize );
      this.setTexture ( TEXTURE_TREE );
   }
}
