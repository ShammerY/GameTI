package com.example.tigame;

public class CharacterSelection {

    private int characterID;
    private static CharacterSelection instance = null;
    public CharacterSelection(){}
    public static CharacterSelection getInstance(){
        if(instance==null){
            instance = new CharacterSelection();
        }
        return instance;
    }
    public int getCharacterID(){return characterID;}
    public void setCharacterID(int n){
        characterID = n;
    }
}
