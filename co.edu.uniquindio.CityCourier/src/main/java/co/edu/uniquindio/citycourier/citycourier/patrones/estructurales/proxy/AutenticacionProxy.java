package co.edu.uniquindio.citycourier.citycourier.patrones.estructurales.proxy;

public class AutenticacionProxy implements  Autenticacion{
    private final Autenticacion autenticacionReal;
    private int intentasFallidos = 0;
    private static final int MAX_INTENTOS = 3;
    private boolean bloqueado = false;

    public AutenticacionProxy(Autenticacion autenticacionReal) {
        this.autenticacionReal = autenticacionReal;
    }


    @Override
    public boolean autenticar(String id, String clave) {
        if(bloqueado){
            return false;
        }

        if(id==null || id.isBlank() || clave == null || clave.isBlank()){
            return false;
        }

        boolean resultado= autenticacionReal.autenticar(id, clave);
        if(!resultado){
            intentasFallidos++;
            if(intentasFallidos >= MAX_INTENTOS){
                bloqueado = true;
            }
        }else{
            intentasFallidos =0;
        }
        return resultado;
    }

    public boolean estaBloqueado() {
        return bloqueado;
    }
}
