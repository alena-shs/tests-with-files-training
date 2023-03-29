package com.alenashomanova;

public class Pokemon {

    public String name;
    public Boolean isPokemon;
    public Integer index;
    public String type;
    public String[] pokedexes;
    public Attack strongestAttack;

    public static class Attack {

        public String attackname;
        public Integer power;
    }
}
