package dao.interfaces;

import model.Agent;
import java.util.List;

public interface AgentInter {
    public void addAgent(Agent agent);
    public void updateAgent(Agent agent);
    public void deleteAgent(int id);
    public Agent getAgentById(int id);
    public List<Agent> getAllAgents();
    public Agent getAgentWithDepartement(int id);
    public Agent authenticateAgent(String email, String password);
}
