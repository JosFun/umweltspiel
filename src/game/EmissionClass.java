package game;

public enum EmissionClass implements Textures {
    EMISSION_CLASS_GREEN, EMISSION_CLASS_YELLOW, EMISSION_CLASS_RED, EMISSION_CLASS_NONE;

    @Override
    public String toString ( ) {
        if ( this == EMISSION_CLASS_GREEN ) return ( "Green" );
        else if ( this == EMISSION_CLASS_YELLOW ) return ( "Yellow" );
        else if ( this == EMISSION_CLASS_RED ) return ( "Red" );
        else return ( "none" );
    }
}
