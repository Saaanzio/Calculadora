package modelo;

import java.util.ArrayList;
import java.util.List;

public class Memoria {


    private static final Memoria instancia = new Memoria();
    private final List<MemoriaObservador> observadores = new ArrayList<>();
    private String textoAtual = "";
    private String textoBuffer = "";
    private boolean substituir;
    private TipoComando ultimaOperacao = null;
    private Memoria(){

    }
    public static Memoria getInstancia(){
        return instancia;
    }
    public String getTextoAtual(){
        return textoAtual.isEmpty() ? "0" : textoAtual;
    }
    public void adicionarObservador(MemoriaObservador o){
        observadores.add(o);
    }
    public void processarComando(String valor){
        TipoComando tipo = detectarTipo(valor);
        if(tipo == null){
            return;
        }else if(tipo == TipoComando.ZERAR){
            textoAtual = "";
            textoBuffer = "";
            substituir = false;
            ultimaOperacao = null;
        } else if (tipo == TipoComando.NUMERO || tipo == TipoComando.VIRGULA) {
            textoAtual = substituir ? valor : textoAtual + valor;
            substituir = false;
        }else if(tipo == TipoComando.INVERTER & !textoAtual.contains("-")){
            textoAtual = "-" + textoAtual;
        }else if(tipo == TipoComando.INVERTER & textoAtual.contains("-")){
            textoAtual = textoAtual.substring(1);
        }

        else{
            substituir = true;
            textoAtual = obterResultadoOperacao();
            textoBuffer = textoAtual;
            ultimaOperacao = tipo;
        }


        observadores.forEach(o -> o.valorAlterado(getTextoAtual()));
    }

    private String obterResultadoOperacao() {
        if(ultimaOperacao == null || ultimaOperacao == TipoComando.IGUAL){
            return textoAtual;
        }
        double numeroBuffer = Double.parseDouble(textoBuffer.replace(",","."));
        double numeroAtual = Double.parseDouble(textoAtual.replace(",","."));
        double resultado = 0;
        if(ultimaOperacao == TipoComando.SOMA){
            resultado = numeroBuffer + numeroAtual;
        } else if(ultimaOperacao == TipoComando.SUB){
            resultado = numeroBuffer - numeroAtual;
        }
        else if(ultimaOperacao == TipoComando.MULT){
            resultado = numeroBuffer * numeroAtual;
        }
        else if(ultimaOperacao == TipoComando.DIV){
            resultado = numeroBuffer / numeroAtual;
        }
        String resultadoString = Double.toString(resultado).replace(".",",");
        boolean inteiro = resultadoString.endsWith(",0");
        return inteiro ? resultadoString.replace(",0","") : resultadoString;
    }

    private TipoComando detectarTipo(String valor) {
        if(textoAtual.isEmpty() && valor.equals("0")){}

        try{
            Integer.parseInt(valor);
            return TipoComando.NUMERO;
        }catch(NumberFormatException e){
            //if not number
            if("AC".equals(valor)){
                return TipoComando.ZERAR;
            }
            else if("/".equals(valor)){
                return TipoComando.DIV;
            }
            else if("*".equals(valor)){
                return TipoComando.MULT;
            }
            else if("+".equals(valor)){
                return TipoComando.SOMA;
            }
            else if("-".equals(valor)){
                return TipoComando.SUB;
            }
            else if("=".equals(valor)){
                return TipoComando.IGUAL;
            }
            else if(",".equals(valor) && !textoAtual.contains(",")){
                return TipoComando.VIRGULA;
            }
            else if("±".equals(valor)){
                return TipoComando.INVERTER;
            }
        }
        return null;
    }
}
