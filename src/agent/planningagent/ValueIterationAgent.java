package agent.planningagent;

import environnement.Action;
import environnement.Etat;
import environnement.MDP;
import environnement.gridworld.ActionGridworld;
import environnement.gridworld.GridworldMDP;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cet agent met a jour sa fonction de valeur avec value iteration et choisit
 * ses actions selon la politique calculee.
 *
 * @author laetitiamatignon
 *
 */
public class ValueIterationAgent extends PlanningValueAgent {

    /**
     * discount facteur
     */
    protected double gamma;
    //*** VOTRE CODE
    
    protected Map<Etat, Double> v;
    
    /**
     *
     * @param gamma
     * @param mdp
     */
    public ValueIterationAgent(double gamma, MDP mdp) {
        super(mdp);
        this.gamma = gamma;
      
        v = new HashMap<Etat, Double>();
   
    }

    public ValueIterationAgent(MDP mdp) {
        this(0.9, mdp);

    }

    /**
     *
     * Mise a jour de V: effectue UNE iteration de value iteration
     */
    @Override
    public void updateV() {
        //delta est utilise pour detecter la convergence de l'algorithme
        //lorsque l'on planifie jusqu'a convergence, on arrete les iterations lorsque
        //delta < epsilon
        this.delta = 0.0;
        //*** VOTRE CODE
        
        List<Etat> listeEtats = this.getMdp().getEtatsAccessibles();
        
        Map<Etat, Double> nouveauV = new HashMap<>();
        
        for (Etat e : listeEtats) {
            List<Action> listeActions = this.getMdp().getActionsPossibles(e);
            
            Double meilleureAction = 0.;
            
            for (Action a : listeActions) {
                try {
                    Map<Etat, Double> listetransitions = this.getMdp().getEtatTransitionProba(e, a);
                    Double somme = 0.;
                    
                    for (Map.Entry<Etat, Double> s : listetransitions.entrySet()) {
                        somme += s.getValue() * (this.getMdp().getRecompense(e, a, s.getKey()) + this.gamma * this.v.get(e));
                    }
                    
                    if (somme > meilleureAction) {
                        meilleureAction = somme;
                    }
                 
                } catch (Exception ex) {
                    Logger.getLogger(ValueIterationAgent.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        
        // mise a jour vmax et vmin pour affichage du gradient de couleur:
        //vmax est la valeur de max pour tout s de V
        //vmin est la valeur de min pour tout s de V
        // ...
        //******************* a laisser a la fin de la methode
        this.notifyObs();
    }

    /**
     * renvoi l'action executee par l'agent dans l'etat e
     */
    @Override
    public Action getAction(Etat e) {
        
        return this.getPolitique(e).get(0);
    }

    @Override
    public double getValeur(Etat _e) {
        //*** VOTRE CODE

        return 0.0;
    }

    /**
     * renvoi la (les) action(s) de plus forte(s) valeur(s) dans l'etat e
     * (plusieurs actions sont renvoyees si valeurs identiques, liste vide si
     * aucune action n'est possible)
     */
    @Override
    public List<Action> getPolitique(Etat _e) {
        List<Action> l = new ArrayList<Action>();
        //*** VOTRE CODE

        return l;

    }

    @Override
    public void reset() {
        super.reset();
        
        this.v.clear();
        
        List<Etat> listeEtats = this.getMdp().getEtatsAccessibles();

        for (Etat e : listeEtats) {
            this.v.put(e, 0.);
        }
        /*-----------------*/
        this.notifyObs();

    }

    @Override
    public void setGamma(double arg0) {
        this.gamma = arg0;
    }

}
