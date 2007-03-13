package org.drools.clp;

import java.util.Map;

public abstract class BaseFunction extends BaseValueHandler implements Function {
    
    protected ValueHandler[] parameters;
    
    public BaseFunction() {
        this.parameters = new ValueHandler[0];
    }
    
    public BaseFunction(ValueHandler[] parameters) {
        if( parameters == null ) {
            this.parameters = new ValueHandler[0];
        } else {
            this.parameters = parameters;
        }
    }
    
    public void addParameter(ValueHandler valueHandler) {
        ValueHandler[] temp = new ValueHandler[ parameters.length + 1 ];
        System.arraycopy( this.parameters, 0, temp, 0, this.parameters.length );
        temp[ temp.length - 1] = valueHandler;
        this.parameters = temp;          
    }
    
    public ValueHandler[] getParameters() {
        return this.parameters;
    }
    
    public void setValue(ExecutionContext context,
                         Object object) {
        throw new RuntimeException( "You cannot set the value on a Function" );
    }
    
    public void replaceTempTokens(Map variables) {
        for ( int i = 0; i < this.parameters.length; i++ ) {
            if ( this.parameters[i] instanceof TempTokenVariable ) {
                TempTokenVariable var = ( TempTokenVariable ) this.parameters[i]; 
                this.parameters[i] = ( ValueHandler ) variables.get( var.getIdentifier() );
            } else if ( this.parameters[i] instanceof Function ) {
                Function function = ( Function ) this.parameters[i];
                function.replaceTempTokens( variables );
            }
        }
        
    }
}
