package com.techbuzz.katraj.drunkpersondetection;

/**
 * Created by admin on 14-Sep-17.
 */

public class Person {
    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    int rid;
    int recipeeid;

    public Person(int recipeeid, int ridss) {
    }



    public int getRecipeeid() {
        return recipeeid;
    }

    public void setRecipeeid(int recipeeid) {
        this.recipeeid = recipeeid;
    }
}
