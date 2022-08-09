package com.cst2335.cocktail_database;

//Drink Object can be use to read database
public class DrinkInfo {
    String drinkName;

    public  DrinkInfo (String drinkName){
        this.drinkName = drinkName;
    }

    public String getDrinkName (){
        return drinkName;
    }

    public void setDrinkNameA(String drinkName) {
        this.drinkName = drinkName;
    }
}
