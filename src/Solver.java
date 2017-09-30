import java.util.*;

public class Solver {

	static int size=3; //3X3 board
	static PriorityQueue<Node> minPQ=new PriorityQueue<Node>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] initial={{1,2,3},{7,5,6},{8,4,0}};
		
		int[][] goal={{1,2,3},{4,5,6},{7,8,0}};
		
		int i_blank=2,j_blank=2;
		
		int moves=solve(initial,i_blank,j_blank,goal);
		
		System.out.println("Total no of moves needed: "+moves);
		
	}
	
	static int solve(int[][]initial,int i_blank,int j_blank, int[][] goal)
	{
		
		Node root=new Node(i_blank,j_blank,initial,null,0);
		
		root.computeManhattanSum(initial, goal);
		
		root.compute_f_of_n(root.g, root.h);
		
		minPQ.add(root);
		
		int noOfMoves=0;
		
		while(minPQ.isEmpty()==false)
		{

			Node current=minPQ.poll(); 
			
			if(Arrays.deepEquals(current.puzzle, goal))
			{
				System.out.println("goal state reached!!");
				printPath(current);
				break;
			}
			
			expand(current,goal); //Find the children of this node
				
			noOfMoves++;
		}
		
		return noOfMoves;
	}
	
	
	//4 possible moves or children for a given puzzle
	static void expand(Node current,int[][] goal)
	{
		// row and column indices to define the downward, backward, upward, forward movements respectively
		int[] rowMoves={1,0,-1,0};
		int[] colMoves={0,-1,0,1};
		int[][] keep=new int[size][size]; //in order to store the original parent puzzle
		
		for(int f=0;f<size;f++)
		{
			for(int h=0;h<size;h++)
			{
				keep[f][h]=current.puzzle[f][h];
			}
		}
		
		//since there are maximum four legal moves for any cell
		for(int n=0;n<4;n++)
		{
			
			if(validMove(current.x+rowMoves[n],current.y+colMoves[n]))
			{	
				
				Node child=new Node(current.x,
									current.y,
									current.x+rowMoves[n],
									current.y+colMoves[n],
									current,
									current.g+1,
									keep);
				
				child.computeManhattanSum(child.puzzle, goal);
				child.compute_f_of_n(child.g, child.h);
				
				minPQ.add(child);
			}
		}
	}
	
	static boolean validMove(int a, int b)
	{
		if(a>=0 && a<size && b>=0 && b<size)
		{
			return true;
		}
		
		return false;
	}
	
	static void printPuzzle(int[][] puzzle)
	{
		for(int f=0;f<size;f++)
		{
			for(int h=0;h<size;h++)
			{
				System.out.print(puzzle[f][h]+" ");
			}
			System.out.println();
		}
	}
	
	static void printPath(Node root)
	{
		if (root==null)return;
	    printPath(root.parent);
	    printPuzzle(root.puzzle);
	 
	    System.out.println();
	}
}
