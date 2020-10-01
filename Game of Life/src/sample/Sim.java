package sample;

import javafx.scene.shape.Line;

import java.util.LinkedList;
import java.util.Random;

public class Sim {

public class Track{
    int[][] newgrid;
    public Track(int[][] grid){
        this.newgrid = grid;
    }

    public int[][] getNewgrid() {
        return newgrid;
    }
}

public LinkedList<Track> grids = new LinkedList<>();
    public int[][] grid = new int[128][72];


    public Sim(){
        {
            for(int i = 0;i<128;i++){
                for (int j =0;j<72;j++){
                    grid[i][j] = 0;
                }

            }
        }

    }

    public void setALive(int x,int y,int[][] g){

        g[x][y] = 1;
    }
    public void kill(int x,int y,int[][] g){
        g[x][y] = 0;
    }

    public boolean isAlive(int x,int y){
        if(grid[x][y] ==1){
            return true;
        }else{
            return false;
        }
    }

    public int  checkAliveNeighbours(int x,int y){
        if(!(x-1<0 || x+1>128||y-1<0||y+1>72)){
            return grid[x-1][y-1] + grid[x][y-1]+grid[x+1][y-1]+grid[x-1][y]+grid[x+1][y]+grid[x-1][y+1]+grid[x][y+1]+grid[x+1][y+1];
        }else{
            return 0;
        }

    }
    public void simulate(){
        int[][] newgrid = new int[128][72];

        grids.add(new Track(grid));

        for(int i = 0;i<128-1;i++){
            for (int j =0;j<72-1;j++){
                int aliveNeighbours = checkAliveNeighbours(i,j);
              if(isAlive(i,j)){
                  if(aliveNeighbours<2){
                      kill(i,j,newgrid);
                  }
                  if(aliveNeighbours==2 || aliveNeighbours==3){
                      setALive(i,j,newgrid);
                  }
                  if(aliveNeighbours>3){
                      kill(i,j,newgrid);
                  }
              }else {
                  if(aliveNeighbours ==3){
                      setALive(i,j,newgrid);
                  }
              }
            }

        }

        grids.add(new Track(newgrid));

        this.grid = newgrid;

    }


    public void undo(){
        if(grids.size()>1){
            grid = grids.getLast().getNewgrid();
            grids.removeLast();
        }
    }


    public void simulate_p(){
        int r1 = (int) (Math.random()*3);
        int r2 = (int) (Math.random()*3);
        int[][] newgrid = new int[128][72];

        for(int i = 0;i<128-1;i++) {
            for (int j = 0; j < 72 - 1; j++) {
                   if(checkAliveNeighbours(i,j)%2==1){
                       setALive(i,j,grid);
                   }
                   if(checkAliveNeighbours(i,j)%3==1){
                       kill(i,j,grid);
                   }
            }
        }

        newgrid = grid.clone();
//        grids.add(new Track(newgrid));

    }

}
