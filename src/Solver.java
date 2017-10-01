
import java.util.*;

public class Solver {

	static int size=3; //3X3 board
	static PriorityQueue<Node> minPQ=new PriorityQueue<Node>();
	static ArrayList<int[][]> closedList=new ArrayList<int[][]>(); //to keep track of the visited nodes
	static ArrayList<Node> children=new ArrayList<Node>();
	static int nodesGenerated=0;
	static int nodesExpanded=0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		int[][] initial=new int[size][size];
		int[][] goal=new int[size][size];
		
		int i_blank = 0,j_blank = 0;
		
		System.out.println("Enter values for initial state:");
		
		for (int i = 0; i < size; i++)
		{
		    for(int j = 0; j < size; j++) {
		        System.out.println("Row ["+i+"]:  Column ["+j+"]:");
		        initial[i][j] = sc.nextInt();
		    }
		}
		
		System.out.println("Enter values for goal state:");
		
		for (int i = 0; i < size; i++)
		{
		    for(int j = 0; j < size; j++) {
		        System.out.println("Row ["+i+"]:  Column ["+j+"]:");
		        goal[i][j] = sc.nextInt();
		    }
		}
		
		sc.close();
		
		for (int i = 0; i < size; i++)
		{
		    for(int j = 0; j < size; j++) {
		        if(initial[i][j]==0)
		        {
		        	i_blank=i;
		        	j_blank=j;
		        }
		    }
		}
		
		solve(initial,i_blank,j_blank,goal);
		
		System.out.println("No of nodes generated: "+nodesGenerated);
		System.out.println("No of nodes expanded: "+nodesExpanded);
		
	}
	
	static void solve(int[][]initial,int i_blank,int j_blank, int[][] goal)
	{
		
		Node root=new Node(i_blank,j_blank,initial,null,0);
		
		root.computeManhattanSum(initial, goal);
		
		root.compute_f_of_n(root.g, root.h);
		
		nodesGenerated++;
		
		minPQ.add(root);
	
		
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
			
			HashMap<int[][],Node> map=new HashMap<int[][],Node>(); //to track duplicate nodes
			
			for(Node n:children)
			{
				if(!closedList.contains(n.puzzle)) //checks if the state is same as its ancestor
				{
					if(minPQ.size()==0)
					{
						n.computeManhattanSum(n.puzzle, goal);
						n.compute_f_of_n(n.g, n.h);
						minPQ.add(n); 
						map.put(n.puzzle, n);
					}
					
					else
					{
						
						if(map.containsKey(n.puzzle))
						{
							if(map.get(n.puzzle).g>n.g) //avoid repeated states in the queue
							{
								minPQ.remove(map.get(n.puzzle)); //remove the node with higher g value
								n.computeManhattanSum(n.puzzle, goal);
								n.compute_f_of_n(n.g, n.h);
								minPQ.add(n);
								map.put(n.puzzle, n);
							}
						}
						else
						{
							n.computeManhattanSum(n.puzzle, goal);
							n.compute_f_of_n(n.g, n.h);
							minPQ.add(n);
							map.put(n.puzzle, n);
						}
					}
				}
			}
			
			children.clear();
		}	
	}
	
	
	//4 possible moves or children for a given puzzle
	static void expand(Node current,int[][] goal)
	{
		nodesExpanded++;
		closedList.add(current.puzzle);
		
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
				nodesGenerated++;
				children.add(child);
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
	    System.out.println("g(n)= "+root.g);
	    System.out.println("h(n)= "+root.h);
	    System.out.println("f(n)= "+root.f);

	    System.out.println();
	}
}
