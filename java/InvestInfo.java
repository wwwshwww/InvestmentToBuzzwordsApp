/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buzzbuzz;

/**
 *
 * @author wwwsh
 */
public class InvestInfo {
    private String id;
    private String word;
    private double investValue;
    private double priceThen;

    public InvestInfo(String id, String word, double investValue, double priceThen) {
        this.id = id;
        this.word = word;
        this.investValue = investValue;
        this.priceThen = priceThen;
    }

    public String getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public double getInvestValue() {
        return investValue;
    }

    public double getPriceThen() {
        return priceThen;
    }
    
    
}
