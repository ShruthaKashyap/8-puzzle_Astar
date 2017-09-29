import java.util.*;

public class Solver {

	static int size=3; //3X3 board
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] initial={{1,2,3},{7,5,6},{8,4,0}};
		
		int[][] goal={{1,2,3},{7,5,6},{8,0,4}};
		
		int i_blank=2,j_blank=2;
		
		int moves=solve(initial,i_blank,j_blank,goal);
		
		System.out.println("Total no of moves needed: "+moves);
		
	}
	
	static int solve(int[][]initial,int i_blank,int j_blank, int[][] goal)
	{
		
		PriorityQueue<Node> minPQ=new PriorityQueue<Node>();
		
		Node root=new Node(i_blank,j_blank,initial,null,0);
		
		root.computeManhattanSum(initial, goal);
		
		root.compute_f_of_n(root.g, root.h);
		
		minPQ.add(root);
		
		int noOfMoves=0;
		
		while(minPQ.isEmpty()==false)
		{
			//noOfMoves++;
			ArrayList<Node> children;
			Node current=minPQ.poll(); 
			
			if(Arrays.deepEquals(current.puzzle, goal))
			{
				System.out.println("goal state reached!!");
				break;
			}
			
			children=expand(current); //branch out
			
			for(Node child: children)
			{
				//compute heuristic
				child.computeManhattanSum(child.puzzle, goal);
				child.compute_f_of_n(child.g, child.h);
				
				//add child to PQ
				minPQ.add(child);
			}
			
			noOfMoves++;
		}
		
		return noOfMoves;
	}
	
	
	//4 possible moves or children for a given puzzle
	static ArrayList<Node> expand(Node current)
	{
		Node copy=current.clone();
		
		int i=copy.x, j=copy.y; //blank tile coordinates
		int[][] data;
		int[][] puzzle_original=copy.clonePuzzle();
		int[][] keep=new int[3][3];
		
		for(int f=0;f<3;f++)
		{
			for(int h=0;h<3;h++)
			{
				keep[f][h]=puzzle_original[f][h];
			}
		}
		
		//puzzle_original=copy.puzzle;
		int level=current.g;
		
		ArrayList<Node> children=new ArrayList<Node>();
		
		Node child1, child2, child3, child4;
		
		//forward
		if(j+1<2)
		{
			data=puzzle_original.clone();
			
			//swap with the node in front of it
			int temp=data[i][j];
			data[i][j]=data[i][j+1];
			data[i][j+1]=temp;
			
			puzzle_original=keep.clone();
			child1=new Node(i,j+1,data,current,level+1);
			
			children.add(child1);
		}
		
		//downward
		if(i+1<2)
		{
			data=puzzle_original.clone();
			
			//swap with the node in front of it
			int temp=data[i][j];
			data[i][j]=data[i+1][j];
			data[i+1][j]=temp;

			puzzle_original=keep.clone();
			child2=new Node(i+1,j,data,current,level+1);
			
			children.add(child2);
		}
		
		//upward
		if(i-1>0)
		{
			data=puzzle_original.clone();
			
			//swap with the node in front of it
			int temp=data[i][j];
			data[i][j]=data[i-1][j];
			data[i-1][j]=temp;

			puzzle_original=keep.clone();
			child3=new Node(i-1,j,data,current,level+1);
			
			children.add(child3);
		}
		
		
		//backward
		if(j-1>0)
		{
			data=puzzle_original.clone();
			
			//swap with the node in front of it
			int temp=data[i][j];
			data[i][j]=data[i][j-1];
			data[i][j-1]=temp;

			puzzle_original=keep.clone();
			child4=new Node(i,j-1,data,current,level+1);
			
			children.add(child4);
		}
		
		return children;
	}

}
