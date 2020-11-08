public class Escalonador {
    private int idProcessoExec;
    private int idProxProcesso;

    public Escalonador() {
        this.idProcessoExec = 0;
        this.idProxProcesso = 0;
    }

    public int getIdProcessoExec() {
        return idProcessoExec;
    }

    public void setIdProcessoExec(int idProcessoExec) {
        this.idProcessoExec = idProcessoExec;
    }

    public int getIdProxProcesso() {
        return idProxProcesso;
    }

    public void setIdProxProcesso(int idProxProcesso) {
        this.idProxProcesso = idProxProcesso;
    }
    
    public void atualizarIds(){
        this.setIdProcessoExec(getIdProxProcesso());
        this.setIdProxProcesso(getIdProxProcesso()+1);
    }
    
    public void reiniciarIds(){
        setIdProcessoExec(0);
        setIdProxProcesso(0);
    }
}