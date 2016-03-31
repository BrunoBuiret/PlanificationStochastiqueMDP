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
 * @author Bruno Buiret (bruno.buiret@etu.univ-lyon1.fr)
 * @author Thomas Arnaud (thomas.arnaud@etu.univ-lyon1.fr)
 */
public class AgentRandom extends PlanningValueAgent
{
    /**
     * 
     * @param _m 
     */
    public AgentRandom(MDP _m)
    {
        super(_m);
    }
    
    /**
     * 
     * @param e
     * @return 
     */
    @Override
    public Action getAction(Etat e)
    {
        List<Action> list = this.getPolitique(e);

        return list.size() > 0 ? list.get(this.rand.nextInt(list.size())) : null;
    }

    /**
     * 
     * @param _e
     * @return 
     */
    @Override
    public double getValeur(Etat _e)
    {
        return 0.0;
    }

    /**
     * 
     * @param _e
     * @return 
     */
    @Override
    public List<Action> getPolitique(Etat _e)
    {
        return this.getMdp().getActionsPossibles(_e);
    }

    /**
     * 
     */
    @Override
    public void updateV()
    {
        System.out.println("L'agent random ne planifie pas.");
    }

    /**
     * 
     * @param gamma 
     */
    @Override
    public void setGamma(double gamma)
    {
    }
}
