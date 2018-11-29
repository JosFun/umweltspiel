package game;

public class Engine {
    final double MAX_VELOCITY = 130;

    private EmissionClass emission; /* This engine's EmissionClass. */
    private double velocity; /* The current velocity of this engine. */

    public Engine ( double v, EmissionClass e ) {
        this.velocity = v;
        this.emission = e;
    }

    public EmissionClass getEmission() {
        return emission;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity ( double v ) {
        this.velocity = v;
    }
}
