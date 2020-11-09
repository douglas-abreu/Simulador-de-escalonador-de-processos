public class Processo {
    private boolean execucao;
    private boolean pronto;
    private int identificador;
    private int ciclo;

    public Processo(int identificador) {
        this.execucao = false;
        this.pronto = true;
        this.identificador = identificador;
    }
    
    public boolean isExecucao() {
        return execucao;
    }

    public void setExecucao(boolean execucao) {
        this.execucao = execucao;
    }

    public boolean isPronto() {
        return pronto;
    }

    public void setPronto(boolean pronto) {
        this.pronto = pronto;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public int getCiclo() {
        return ciclo;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }
    
    public void atualizarEstados(){
        if(this.pronto){
            this.setPronto(false);
            this.setExecucao(true);
        }else{
            this.setPronto(true);
            this.setExecucao(false);
        }
    }
}