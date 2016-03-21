package agent.planningagent;

import environnement.Action;
import environnement.Etat;
import environnement.MDP;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

        this.v = new HashMap<>();
        this.reset();
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
            if(!this.getMdp().estAbsorbant(e))
            {
                List<Action> listeActions = this.getMdp().getActionsPossibles(e);
                double meilleureAction = -Double.MAX_VALUE; // ATTENTION

                for (Action a : listeActions) {
                    try {
                        Map<Etat, Double> listetransitions = this.getMdp().getEtatTransitionProba(e, a);
                        Double somme = 0.;

                        for (Map.Entry<Etat, Double> s : listetransitions.entrySet()) {
                            // s == Etat e
                            // a == Action a
                            // s' == Etat s.getKey()
                            somme += s.getValue() * (this.getMdp().getRecompense(e, a, s.getKey()) + this.gamma * this.v.get(s.getKey()));
                        }

                        if (somme > meilleureAction) {
                            meilleureAction = somme;
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(ValueIterationAgent.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                // Mise à jour de V_k
                nouveauV.put(e, meilleureAction);
            }
            else
            {
                nouveauV.put(e, 0.);
            }
        }

        // Mise à jour du delta
        double deltaTest;

        for (Etat e : listeEtats) {
            deltaTest = Math.abs(this.v.get(e) - nouveauV.get(e));

            if (this.delta < deltaTest) {
                this.delta = deltaTest;
            }
        }

        // Mémorisation de V_k
        this.v = nouveauV;

        // mise a jour vmax et vmin pour affichage du gradient de couleur:
        //vmax est la valeur de max pour tout s de V
        //vmin est la valeur de min pour tout s de V
        for (Map.Entry<Etat, Double> s : this.v.entrySet()) {
            if (this.vmin > s.getValue()) {
                this.vmin = s.getValue();
            }

            if (this.vmax < s.getValue()) {
                this.vmax = s.getValue();
            }
        }

        //******************* a laisser a la fin de la methode
        this.notifyObs();
    }

    /**
     * renvoi l'action executee par l'agent dans l'etat e
     *
     * @param e
     * @return
     */
    @Override
    public Action getAction(Etat e) {
        return this.getPolitique(e).size() > 0 ? this.getPolitique(e).get(0) : null;
    }

    @Override
    public double getValeur(Etat _e) {
        //*** VOTRE CODE

        return this.v.getOrDefault(_e, 0.);
    }

    /**
     * renvoi la (les) action(s) de plus forte(s) valeur(s) dans l'etat e
     * (plusieurs actions sont renvoyees si valeurs identiques, liste vide si
     * aucune action n'est possible)
     *
     * @param _e
     * @return
     */
    @Override
    public List<Action> getPolitique(Etat _e) {
        Map<Action, Double> resultats = new HashMap<>();
        List<Action> listeActions = this.getMdp().getActionsPossibles(_e);

        for (Action a : listeActions) {
            try {
                Map<Etat, Double> listeTransitions = this.getMdp().getEtatTransitionProba(_e, a);
                Double somme = 0.;

                for (Map.Entry<Etat, Double> s : listeTransitions.entrySet()) {
                    // s == Etat e
                    // a == Action a
                    // s' == Etat s.getKey()
                    somme += s.getValue() * (this.getMdp().getRecompense(_e, a, s.getKey()) + this.gamma * this.v.get(s.getKey()));
                }

                resultats.put(a, somme);
            } catch (Exception ex) {
                Logger.getLogger(ValueIterationAgent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // Trouver les meilleures actions
        List<Action> meilleuresActions = new ArrayList<>();
        double topV = -Double.MAX_VALUE; // ATTENTION
        
        for(Map.Entry<Action, Double> entry : resultats.entrySet())
        {
            if(entry.getValue() > topV)
            {
                topV = entry.getValue();
                meilleuresActions.clear();
                meilleuresActions.add(entry.getKey());
            }
            else if(entry.getValue() == topV)
            {
                meilleuresActions.add(entry.getKey());
            }
        }
        
        return meilleuresActions;
    }

    /**
     * 
     */
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

    /**
     * 
     */
    @Override
    public void setGamma(double arg0) {
        this.gamma = arg0;
    }
}
