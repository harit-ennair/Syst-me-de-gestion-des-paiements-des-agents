package dao.interfaces;

import model.Paiement;

import java.util.List;

public interface PaiementInter {
    public void addPaiement(Paiement paiement);
    public void updatePaiement(Paiement paiement);
    public void deletePaiement(int id);
    public Paiement getPaiementById(int id);
    public List<Paiement> getAllPaiements();
}
