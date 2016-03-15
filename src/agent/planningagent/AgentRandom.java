package agent.planningagent;

import environnement.Action;
import environnement.Etat;
import environnement.MDP;
import java.util.List;

/**
 * Cet agent choisit une action aleatoire parmi toutes les autorisees dans
 * chaque etat
 *
 * @author lmatignon
 *
 */
public class AgentRandom extends PlanningValueAgent {
    public AgentRandom(MDP _m) {
        super(_m);
    }

    @Override
    public Action getAction(Etat e) {
        List<Action> list = this.getMdp().getActionsPossibles(e);
        
        return list.size() > 0 ? list.get(this.rand.nextInt(list.size())) : null;
    }

    @Override
    public double getValeur(Etat _e) {
        return 0.0;
    }

    @Override
    public List<Action> getPolitique(Etat _e) {
        List<Action> list = this.getMdp().getActionsPossibles(_e);

        return list;
    }

    @Override
    public void updateV() {
        System.out.println("l'agent random ne planifie pas");
    }

    @Override
    public void setGamma(double parseDouble) {
        // TODO Auto-generated method stub
    }
}
