/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.Scanner;

/**
 *
 * @author Mauricio
 */
public class BackTracking {
    public BackTracking(){
        int persona[] = {0,0};
        int meta[] = {9,9};
        int maze[][] = {
            {1, 1, 0, 1, 1, 1, 1, 0, 1, 1}, 
            {0, 1, 0, 1, 0, 0, 1, 1, 1, 1}, 
            {1, 1, 1, 1, 1, 0 ,1 , 1, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 1, 1, 1}, 
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, 
            {1, 0, 1, 0, 1, 1, 0, 1, 1, 1}, 
            {1, 1, 1, 0, 1, 0, 0, 1, 0, 1}, 
            {1,0, 1, 1, 0, 1, 0, 1, 1, 1},
            {1, 0, 0, 1, 1, 1, 1, 0, 0, 1},
            {1, 1, 1, 1, 0, 0, 1, 1, 1, 1}
        };

        int already_passed_through_maze[][] = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        imprimirMaze(maze, persona, meta);

        littleBacktracking(persona, meta, maze, -1, already_passed_through_maze);
    }

    //direction: 0 - left, 1 - up, 2 - right, 3 - down
    public boolean littleBacktracking(int[] persona, int[] meta, int[][] maze, int direction, int[][] aptm)
    {
        if(persona[0] == meta[0] && persona[1] == meta[1])
        {
            return true;
        }

        if(persona[0] >= 0 && persona[0] < maze.length && persona[1] >= 0 && persona[1] < maze[persona[0]].length)
        {
            if(maze[persona[0]][persona[1]] == 0)
            {
                return false;
            }
        }
        else
        {
            return false;
        }

        if(aptm[persona[0]][persona[1]] == 1)
        {
            return false;
        }

        aptm[persona[0]][persona[1]] = 1;

        Scanner sc = new Scanner(System.in);
        System.out.println("persona: " + persona[0] + ", " + persona[1] + "\t" + "meta: " + meta[0] + ", " + meta[1]);
        imprimirMaze(maze, persona, meta);
        sc.nextLine();

        //Izquierda
        if(direction != 2)
        {
            System.out.println("Izquierda");
            int new_persona[] = {persona[0] , persona[1] - 1};
            if(littleBacktracking(new_persona, meta, maze, 0, aptm))
            {
                return true;
            }
        }

        //Arriba
        if(direction != 3)
        {
            System.out.println("Arriba");
            int new_persona[] = {persona[0] - 1, persona[1]};
            if(littleBacktracking(new_persona, meta, maze, 1, aptm))
            {
                return true;
            }
        }

        //Derecha
        if(direction != 0)
        {        
            System.out.println("Derecha");
            int new_persona[] = {persona[0], persona[1] + 1};
            if(littleBacktracking(new_persona, meta, maze, 2, aptm))
            {
                return true;
            }
        }

        //Abajo
        if(direction != 1)
        {        
            System.out.println("Abajo");
            int new_persona[] = {persona[0] + 1, persona[1]};
            if(littleBacktracking(new_persona, meta, maze, 3, aptm))
            {
                return true;
            }
        }

        return false;
    }

    public void imprimirMaze(int Maze[][], int persona[], int meta[])
    {
        System.out.println("############");
        for(int i = 0; i<Maze.length; i++){
            System.out.print("#");
            for(int j = 0; j<Maze[i].length; j++){
                if(i == persona[0] && j == persona[1])
                {
                   System.out.print("P");
                }
                else if(i == meta[0] && j == meta[1])
                {
                   System.out.print("X");
                }
                else
                {
                    if(Maze[i][j] == 0){
                         System.out.print("#");
                    }
                    else{
                        System.out.print(".");
                    }
                }
            }
            System.out.println("#");
        }
        System.out.println("############\n\n");
    }
    
    public static void main(String[] args)
    {
        BackTracking backtrackiing = new BackTracking();
    }
}
