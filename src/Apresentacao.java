import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class Apresentacao extends javax.swing.JFrame {
    int numProcesso = 0;
    int numCiclo;
    Processo processoEmExec;
    Processador processador = new Processador(false);
    LinkedList<Processo> listaProcessos = new LinkedList<>();
    Timer timer = new Timer();
    Escalonador escalonador = new Escalonador();

    public void criarProcesso(int qtdProcessos) {
        for (int i = 0; qtdProcessos > i; qtdProcessos--) {
            listaProcessos.add(new Processo(numProcesso));
            dadosEncaminhados.setText(dadosEncaminhados.getText()
                    + "Processo " + listaProcessos.getLast().getIdentificador() + ": Pronto \n");
            numProcesso++; //1
        }
        if (listaProcessos.size() > 1) {
            escalonador.setIdProxProcesso(1);
        }
        verificarSeHaProcessos();
    }

    public void verificarSeHaProcessos() {
        if (!listaProcessos.isEmpty()) { // se lista de processos não estiver vazia
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    dadosEncaminhados.setText(dadosEncaminhados.getText()
                            + "Há " + listaProcessos.size() + " processo(s) pronto(s). \n \n");
                }
            }, 2 * 1000);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    execucaoDoProcesso();
                }
            }, 2 * 1000);
        } else {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    dadosEncaminhados.setText(dadosEncaminhados.getText()
                            + "Não há processo pronto, aguardando novos processos. \n \n");
                    btnCriarProcessos.setEnabled(true);
                }
            }, 2 * 1000);
        }
    }

    public void execucaoDoProcesso() {
        if (listaProcessos.size() > 1 && !processador.isOcupado()) { // se tiver processos na lista e o processador estiver livre
            processador.setOcupado(true);
            if (processoEmExec == null) { //se não houver histórico, execute o primeiro processo da lista.
                processoEmExec = listaProcessos.getFirst();
                processoEmExec.setExecucao(true);
            } else {
                processoEmExec = listaProcessos.get(escalonador.getIdProcessoExec());
                processoEmExec.setExecucao(true); // execute o processo que for o próximo da lista
            }
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    dadosEncaminhados.setText(dadosEncaminhados.getText()
                            + "Processo " + processoEmExec.getIdentificador() + ": Execução  \n");
                    processoEmExec.setCiclo(processoEmExec.getCiclo() + 1);
                    System.out.println("Ciclo processo " + processoEmExec.getIdentificador() + ":" + processoEmExec.getCiclo());
                }
            }, 2 * 1000);
            processoEmExec.setCiclo(processoEmExec.getCiclo() + 1);
            finalizarProcesso();
        } else { // se minha lista estiver vazia, executo o último da lista 
            processador.setOcupado(true);
            listaProcessos.getLast().setExecucao(true);
            processoEmExec = listaProcessos.getLast();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    dadosEncaminhados.setText(dadosEncaminhados.getText()
                            + "Processo " + processoEmExec.getIdentificador() + ": Execução  \n");
                    processoEmExec.setCiclo(processoEmExec.getCiclo() + 1);
                    System.out.println("Ciclo processo " + processoEmExec.getIdentificador() + ":" + processoEmExec.getCiclo());
                }
            }, 2 * 1000);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    finalizarProcesso(); // e faço a finalização desse processo
                }
            }, 2 * 1000);
        }
    }

    public void finalizarProcesso() {
        if (processoEmExec.getCiclo() >= numCiclo) { //procurar o processador que está em execução e verificar se seu ciclo ultrapassou 2
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    dadosEncaminhados.setText(dadosEncaminhados.getText()
                            + "Processo " + processoEmExec.getIdentificador() + ": Finalizado \n");
                }
            }, 2 * 1000);
            listaProcessos.remove(processoEmExec);
            atualizarEscalonador();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    dadosEncaminhados.setText(dadosEncaminhados.getText()
                            + "Processador agora está disponível.\n");
                            escalonador.reiniciarIds();
                }
            }, 4 * 1000);
        } else {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    dadosEncaminhados.setText(dadosEncaminhados.getText()
                            + "Processo " + processoEmExec.getIdentificador() + ": Pronto \n");

                }
            }, 2 * 1000);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    dadosEncaminhados.setText(dadosEncaminhados.getText()
                            + "Processador agora está disponível.\n");
                            atualizarEscalonador();
                }
            }, 4 * 1000);
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                processador.setOcupado(false); // e libera o processo
                verificarSeHaProcessos();
            }
        }, 4 * 1000);

    }

    public void atualizarEscalonador() {
        if (listaProcessos.size() >= 2 && processoEmExec == listaProcessos.getLast()) {
            escalonador.reiniciarIds();
            escalonador.setIdProxProcesso(1);
        } else if (listaProcessos.size() >= 2) {
            escalonador.atualizarIds();
        } else {
            escalonador.reiniciarIds();
        }
    }

    public Apresentacao() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        dadosEncaminhados = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnCriarProcessos = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        qtdCiclos = new javax.swing.JTextField();
        qtdProcessos = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        dadosEncaminhados.setEditable(false);
        dadosEncaminhados.setColumns(20);
        dadosEncaminhados.setRows(5);
        jScrollPane1.setViewportView(dadosEncaminhados);

        jButton1.setText("Limpar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Ciclos:");

        btnCriarProcessos.setText("Criar Processo(s)");
        btnCriarProcessos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCriarProcessosActionPerformed(evt);
            }
        });

        jLabel2.setText("N° de Processos:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(qtdCiclos, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCriarProcessos)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(qtdProcessos, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jLabel1)
                    .addComponent(qtdCiclos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(qtdProcessos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnCriarProcessos)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(jTextPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCriarProcessosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCriarProcessosActionPerformed
        criarProcesso(Integer.parseInt(qtdProcessos.getText()));
        numCiclo = (Integer.parseInt(qtdCiclos.getText()));
        btnCriarProcessos.setEnabled(false);
    }//GEN-LAST:event_btnCriarProcessosActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dadosEncaminhados.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() -> {
            new Apresentacao().setVisible(true);
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCriarProcessos;
    private javax.swing.JTextArea dadosEncaminhados;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextField qtdCiclos;
    private javax.swing.JTextField qtdProcessos;
    // End of variables declaration//GEN-END:variables
}
