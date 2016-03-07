package simulation;

import javax.swing.SwingUtilities;

import vueGridworld.VueGridworldValue;
import agent.planningagent.AgentRandom;
import environnement.gridworld.GridworldEnvironnement;
import environnement.gridworld.GridworldMDP;

public class testRandomAgent {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try
        {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        }
        catch(ClassNotFoundException|InstantiationException|IllegalAccessException|javax.swing.UnsupportedLookAndFeelException e)
        {
            java.util.logging.Logger.getLogger(testRandomAgent.class.getName()).log(java.util.logging.Level.SEVERE, e.getMessage(), e);
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                GridworldMDP gmdp = GridworldMDP.getBookGrid();

                GridworldEnvironnement g = new GridworldEnvironnement(gmdp);
                GridworldEnvironnement.setDISP(true);//affichage transitions

                AgentRandom a = new AgentRandom(gmdp);
                VueGridworldValue vue = new VueGridworldValue(g, a);

                vue.setVisible(true);
            }
        });

    }

}
