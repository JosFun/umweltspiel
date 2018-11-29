package observers;

import javafx.scene.Node;
/* Every graphical object of the game descends from DisplaySizeObserver and is therefore a javafx node as well.
* We need this in order to be able to process mouse click events etc.*/
public abstract class DisplaySizeObserver extends Node {
    protected DisplaySizeSubject subject;
    public abstract void update ( );
}
