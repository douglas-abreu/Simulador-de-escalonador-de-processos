import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class Apresentacao extends javax.swing.JFrame {
    int numProcesso = 0;
    int idProximoProcesso = 0;
    Processo processoExec;
    Processador processador = new Processador(false);
    LinkedList<Processo> listaProcessos = new LinkedList<>();
    Timer timer = new Timer();
    
    public void criarProcesso(){
        listaProcessos.add(new Processo(numProcesso));
        numProcesso++;
        dadosEncaminhados.setText(dadosEncaminhados.getText()+
        listaProcessos.getLast().getIdentificador()+"° processo: Pronto \n");
        execucaoDoProcesso();
    }
    
    public void execucaoDoProcesso(){
        if(listaProcessos.size() > 1 && processador.isOcupado()){
            listaProcessos.get(listaProcessos.size()-2).setIdProximoProcesso(listaProcessos.getLast().getIdentificador());
        }else if(listaProcessos.size() > 1 && !processador.isOcupado()){
            processador.setOcupado(true);
            idProximoProcesso = listaProcessos.getLast().getIdentificador();
            listaProcessos.get(idProximoProcesso).setExecucao(true);
            timer.schedule(new TimerTask() {
            @Override
                public void run() {
                    dadosEncaminhados.setText(dadosEncaminhados.getText()+idProximoProcesso+
                    "° processo: Execução  \n");
                    listaProcessos.getLast().setCiclo(listaProcessos.get(idProximoProcesso).getCiclo()+1);
                    System.out.println("Ciclo processo "+listaProcessos.getLast().getIdentificador()+":"+listaProcessos.getLast().getCiclo());
                }
            }, 2*1000);
            finalizarProcesso();
            
        }else{
            processador.setOcupado(true);
            listaProcessos.getLast().setExecucao(true);
            timer.schedule(new TimerTask() {
            @Override
                public void run() {
                    dadosEncaminhados.setText(dadosEncaminhados.getText()+listaProcessos.getLast().getIdentificador()+
                    "° processo: Execução  \n");
                    listaProcessos.getLast().setCiclo(listaProcessos.get(idProximoProcesso).getCiclo()+1); // MEXER AQUI, VER SE UTILIZANDO O ATRIBUTO PROCESSOEXEC EVITA ESSE ERRO
                    System.out.println("Ciclo processo "+listaProcessos.getLast().getIdentificador()+":"+listaProcessos.getLast().getCiclo());
                    finalizarProcesso();
                }
            }, 2*1000);
            
            
        }
    }
    
    public void finalizarProcesso(){
       listaProcessos.forEach((t) -> {if (t.isExecucao() == true && t.getCiclo() == 3){
                                                   timer.schedule(new TimerTask() {
                                                   @Override
                                                            public void run() {
                                                                dadosEncaminhados.setText(dadosEncaminhados.getText()+
                                                                t.getIdentificador()+"° processo: Finalizado \n");
                                                            }
                                                        }, 2*1000);
                                                    idProximoProcesso = t.getIdProximoProcesso();                                                   
                                                    listaProcessos.remove(t);
                                                  }
                                        });
                                        
                                        timer.schedule(new TimerTask() {
                                        @Override
                                            public void run() {
                                                processador.setOcupado(false);
                                                if(verificarSeHaProcessos()){
                                                    execucaoDoProcesso();
                                                }
                                            }
                                        }, 2*1000);
        
    }

    public Boolean verificarSeHaProcessos(){
        if(!listaProcessos.isEmpty()){
            dadosEncaminhados.setText(dadosEncaminhados.getText()+
            idProximoProcesso+"° processo: Pronto \n");
            dadosEncaminhados.setText(dadosEncaminhados.getText()+
            "Há "+listaProcessos.size()+" processo(s) pronto(s). \n");
            return true;
        }else{
            dadosEncaminhados.setText(dadosEncaminhados.getText()+
            "Não há processos pronto. \n");
            return false;
        }
    }

    public Apresentacao() {
        initComponents();
        
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        clickMe = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        dadosEncaminhados = new javax.swing.JTextArea();
        jProgressBar1 = new javax.swing.JProgressBar();
        jtextExecucao = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        clickMe.setText("Criar Processo");
        clickMe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clickMeActionPerformed(evt);
            }
        });

        dadosEncaminhados.setColumns(20);
        dadosEncaminhados.setRows(5);
        jScrollPane1.setViewportView(dadosEncaminhados);

        jtextExecucao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtextExecucaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(clickMe)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jtextExecucao, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 54, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(clickMe)
                .addGap(62, 62, 62)
                .addComponent(jtextExecucao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clickMeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clickMeActionPerformed
        if(listaProcessos.size() < 2){
            criarProcesso();
        }else{
            clickMe.setEnabled(false);
        }
        
        
    }//GEN-LAST:event_clickMeActionPerformed

    private void jtextExecucaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtextExecucaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtextExecucaoActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new Apresentacao().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clickMe;
    private javax.swing.JTextArea dadosEncaminhados;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jtextExecucao;
    // End of variables declaration//GEN-END:variables
}
