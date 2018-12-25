package game;
import observers.DisplaySizeObserver;
import observers.DisplaySizeSubject;

/* A Score whose size adapts to the current displaySize. */
public class Score extends DisplaySizeObserver {
    private static final double NATIVE_XPOS = 10;
    private static final double NATIVE_YPOS = 25;
    private static final int NATIVE_FONT_SIZE = 21;

    private double xPos;
    private double yPos;
    private int points;
    private int fontSize;

    public Score ( int initScore, DisplaySizeSubject sub ) {
        this.xPos = NATIVE_XPOS;
        this.yPos = NATIVE_YPOS;
        this.fontSize = NATIVE_FONT_SIZE;
        this.points = initScore;
        this.subject = sub;
        this.subject.add ( this ); /* Score adds itself to listen to the subject. */
        this.update ( );
    }

    public void incScore ( int diff ) {
        this.points += diff;
    }

    public int getScore ( ) {
        return ( this.points );
    }

    public void setScore ( int newScore ) { this.points = newScore; }

    public double getXPos ( ) {
        return ( this.xPos );
    }

    public double getYPos ( ) {
        return ( this.yPos );
    }

    public int getFontSize ( ) {
        return ( this.fontSize );
    }

    @Override
    public void update ( ) {
        /* Adapt position and fontSize of the score according to the size of the screen it is being displayed on. */
        this.xPos = this.NATIVE_XPOS * ( this.subject.getWidth() / ( 80 * this.NATIVE_XPOS ) );
        this.yPos = this.NATIVE_YPOS * ( this.subject.getHeight( ) / ( 24 * this.NATIVE_YPOS ) );
        this.fontSize = this.NATIVE_FONT_SIZE * ( ( int ) ( 3 * (  this.subject.getWidth ( ) + this.subject.getHeight ( ) ) / ( 200 * this.NATIVE_FONT_SIZE ) ));
    }
}
