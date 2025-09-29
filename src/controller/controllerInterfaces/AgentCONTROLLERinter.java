package controller.controllerInterfaces;

public interface AgentCONTROLLERinter {
    public void addAgent(String nom, String prenom, String email, String telephone, String adresse, String typeAgent, int departementId);
    public void updateAgent(int id, String nom, String prenom, String email, String telephone, String adresse, String typeAgent, int departementId);
    public void deleteAgent(int id);
    public void getAgentById(int id);
    public void getAllAgents();
    public void getAgentWithDepartement(int id);
}
