package observers;

import java.util.*;

/* This subject is part of the observer infrastrucutre of this software.
* It contains the current dimension of the stage in use.*/
public class DisplaySizeSubject {
    private List<DisplaySizeObserver> obs = new ArrayList <> ( );
    private double width;
    private double height;

    public void add ( DisplaySizeObserver o ) {
        this.obs.add ( o );
    }

    public void execute ( ) {
        for ( DisplaySizeObserver obsv: this.obs ) {
            obsv.update();
        }
    }
    /* Getters and setters for the subject's state. */
    public void setWidth ( double width ) {
        this.width = width;
    }

    public void setHeight ( double height ) {
        this.height = height;
    }

    public double getWidth ( ) {
        return ( this.width );
    }

    public double getHeight ( ) {
        return ( this.height );
    }
}
