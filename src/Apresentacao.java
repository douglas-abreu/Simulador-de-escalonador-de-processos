import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class Apresentacao extends javax.swing.JFrame {
    int numProcesso = 0;
    int idProximoProcesso = 0;
    Processo processoEmExec;
    Processador processador = new Processador(false);
    LinkedList<Processo> listaProcessos = new LinkedList<>();
    Timer timer = new Timer();
    
    public void criarProcesso(){
        listaProcessos.add(new Processo(numProcesso));
        listaProcessos.getLast().setIdProximoProcesso(listaProcessos.getFirst().getIdentificador());
        dadosEncaminhados.setText(dadosEncaminhados.getText()+
        "Processo "+listaProcessos.getLast().getIdentificador()+": Pronto  \n");
        numProcesso++;
        verificarSeHaProcessos();
    }
    
    public void verificarSeHaProcessos(){
        if(!listaProcessos.isEmpty()){ // se lista de processos não estiver vazia
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    dadosEncaminhados.setText(dadosEncaminhados.getText()+
                    "Há "+listaProcessos.size()+" processo(s) pronto(s). \n");
                }
            }, 4*1000);
            
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    execucaoDoProcesso();
                }
            }, 4*1000);
            
        }else{
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    dadosEncaminhados.setText(dadosEncaminhados.getText()+
                    "Não há processo pronto, aguardando novos processos. \n");
                }
            }, 4*1000);
        }
    }
    
    public void execucaoDoProcesso(){
        if(listaProcessos.size() > 1 && processador.isOcupado()){ // se processador estiver ocupado e lista superior a 1 processo
            processoEmExec.setIdProximoProcesso(listaProcessos.getLast().getIdentificador()); // vou no meu processo em execução passo a ele o indentificador do ultimo processo criado.
        }else if(listaProcessos.size() > 1 && !processador.isOcupado()){ // se tiver processos na lista e o processador estiver livre
            processador.setOcupado(true);
            listaProcessos.get(processoEmExec.getIdProximoProcesso()).setExecucao(true); // execute o processo que for o próximo da lista
            timer.schedule(new TimerTask() {
            @Override
                public void run() {
                    dadosEncaminhados.setText(dadosEncaminhados.getText()+
                    "Processo "+idProximoProcesso+": Execução  \n");
                    listaProcessos.getLast().setCiclo(listaProcessos.get(idProximoProcesso).getCiclo()+1);
                    System.out.println("Ciclo processo "+listaProcessos.getLast().getIdentificador()+":"+listaProcessos.getLast().getCiclo());
                }
            }, 2*1000);
            finalizarProcesso();
        }else{ // se minha lista estiver vazia, executo o último da lista 
            processador.setOcupado(true);
            listaProcessos.getLast().setExecucao(true);
            processoEmExec = listaProcessos.getLast();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    dadosEncaminhados.setText(dadosEncaminhados.getText()+
                    "Processo "+processoEmExec.getIdentificador()+": Execução  \n");
                    processoEmExec.setCiclo(processoEmExec.getCiclo()+1);
                    System.out.println("Ciclo processo "+processoEmExec.getIdentificador()+":"+processoEmExec.getCiclo());
                }
            }, 2*1000);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    dadosEncaminhados.setText(dadosEncaminhados.getText()+
                    "Processo "+processoEmExec.getIdentificador()+": Pronto  \n");
                    finalizarProcesso(); // e faço a finalização desse processo
                }
            }, 4*1000);
        }
    }
    
    public void finalizarProcesso(){
        if (processoEmExec.getCiclo() == 2){ //procurar o processador que está em execução e verificar se seu ciclo ultrapassou 2
            timer.schedule(new TimerTask() {
                @Override
                    public void run() {
                        dadosEncaminhados.setText(dadosEncaminhados.getText()+
                        "Processo "+processoEmExec.getIdentificador()+": Finalizado \n");
                    }
            }, 2*1000);                     
            idProximoProcesso = processoEmExec.getIdProximoProcesso();
            listaProcessos.remove(processoEmExec); // se sim, remover esse processo da lista
        }else{
            idProximoProcesso = processoEmExec.getIdProximoProcesso();  
            processoEmExec.setExecucao(false);
            timer.schedule(new TimerTask() {
                @Override
                    public void run() {
                        dadosEncaminhados.setText(dadosEncaminhados.getText()+
                        "Processador agora está disponível.\n");
                    }
            }, 2*1000);
        }        
        processador.setOcupado(false); // e libera o processo
        verificarSeHaProcessos();
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
