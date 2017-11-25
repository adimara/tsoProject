package main;

import java.util.Random;

public class CIMethod {
    private Protein protein;
    private Grid grid;
    private Random random;
    private int[][] ranges;
    private int endRanges;
    private final int NUM_TO_RANDOMIZE = 5;
    private int[] randomMonomers;

    public CIMethod(Protein protein){
        this.protein = protein;
        this.grid = protein.getGrid();
        this.random = protein.random;
        this.endRanges = 0;
        this.ranges = new int[2][(int) Math.floor((protein.getSize() - 1)/2)];
        this.randomMonomers = new int[NUM_TO_RANDOMIZE];

        this.updateRanges();
    }

    public int selectStartMonomer(){
        int numToRandomize = Math.min(NUM_TO_RANDOMIZE, this.protein.getSize());
        this.generateRandomMonomers(numToRandomize);

        int maxMonomerIndex = randomMonomers[0];
        int maxFval = getFval(maxMonomerIndex);
        for(int i = 1; i < numToRandomize; i++){
            int currFval = getFval(randomMonomers[i]);
            if(currFval > maxFval){
                maxFval = currFval;
                maxMonomerIndex = randomMonomers[i];
            }
        }
        return  maxMonomerIndex;
    }

    public void updateProtein(Protein protein){
        this.reset();
        this.protein = protein;
        this.updateRanges();
    }

    private void generateRandomMonomers(int numToRandomize){
        int count = 0;
        while(count < numToRandomize){
            int randomMonomer = random.nextInt(protein.size() - 1) + 1;
            boolean isFound = false;
            for(int i = 0; i < count && !isFound; i ++){
                if(this.randomMonomers[i] == randomMonomer){
                    isFound = true;
                }
            }
            if(!isFound){
                this.randomMonomers[count] = randomMonomer;
                count++;
            }
        }
    }


    private void updateRanges() {
        int[] neighbours;
        for (int i=0; i < protein.getSize(); i++){
            neighbours = this.grid.returnContacts(protein.getMonomerAt(i));
            if(neighbours != null){
                for(int j=0; j<neighbours.length; j++){
                    if (neighbours[j] != -1 && neighbours[j] > i){
                        this.addRange(i, neighbours[j]);
                    }
                }
            }
        }
    }

    private void addRange(int min, int max) {
        ranges[0][endRanges] = min;
        ranges[1][endRanges] = max;
        endRanges++;
    }

    private void reset(){
        endRanges = 0;
    }

    private int getFval(int numMonomer){
        int fVal = 0;
        for (int i = 0; i < endRanges; i++){
            fVal += getChainVal(i,numMonomer);
        }
        return fVal;
    }

    private int getChainVal(int arrayIndex, int numMonomer) {
        if(ranges[0][arrayIndex] <= numMonomer && ranges[1][arrayIndex] >= numMonomer){
            return getGVal(ranges[1][arrayIndex] - ranges[0][arrayIndex]);
        }
        return 0;
    }

    private int getGVal(int k) {
        return -1*20/(k+17);
    }


}
