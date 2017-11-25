package main;

import java.util.Random;

public class CIMethod {
    private Protein protein;
    private Grid grid;
    private Random random;
    private int[][] ranges;
    private int endRanges;

    public CIMethod(Protein protein){
        this.protein = protein;
        this.grid = protein.getGrid();
        this.random = protein.random;
        this.endRanges = 0;
        this.ranges = new int[2][(int) Math.floor((protein.getSize() - 1)/2)];

        this.updateRanges();
    }


    public void updateProtein(Protein protein){
        this.reset();
        this.protein = protein;
        this.updateRanges();
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
        return 0;
    }



}
