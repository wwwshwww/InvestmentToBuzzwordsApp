/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buzzbuzz;

import java.sql.Timestamp;

/**
 *
 * @author wwwsh
 */
public class PostedWordInfo {
    private String word;
    private double price;
    private Timestamp postedTime;

    public PostedWordInfo(String word, double price, Timestamp postedTime) {
        this.word = word;
        this.price = price;
        this.postedTime = postedTime;
    }

    public String getWord() {
        return word;
    }

    public double getPrice() {
        return price;
    }

    public Timestamp getPostedTime() {
        return postedTime;
    }
}
