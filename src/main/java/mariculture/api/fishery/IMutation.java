package mariculture.api.fishery;

import java.util.ArrayList;

import mariculture.api.fishery.fish.FishSpecies;

public interface IMutation {
    /**
     * Add a mutation, specify the mother, father and baby and the chance of the
     * mutation **/
    public void addMutation(FishSpecies father, FishSpecies mother, FishSpecies baby, double chance);

    public ArrayList<Mutation> getMutations(FishSpecies mother, FishSpecies father);

    public ArrayList<Mutation> getMutations();

    public static class Mutation {
        public String father;
        public String mother;
        public String baby;
        public double chance;

        public Mutation(String father, String mother, String baby, double chance) {
            this.father = father;
            this.mother = mother;
            this.baby = baby;
            this.chance = chance;
        }
    }
}
