import java.util.*;

public class Node implements Comparable<Node>{

	int x, y; //blank cell coordinates
	
	int[][] puzzle=new int[3][3];
	
	Node parent;
	
	int g; //step cost or depth
	int h; //heuristic function - Manhattan Distance
	int f; //g+h
	
	HashMap<Integer,Integer> rows=new HashMap<Integer,Integer>();
	HashMap<Integer,Integer> columns=new HashMap<Integer,Integer>();
	
	public Node(int x, int y, int[][] puzzle, Node parent,int g)
	{
		this.x=x;
		this.y=y;
		this.puzzle=puzzle;
		this.parent=parent;
		this.g=g;
	}
	
	void computeManhattanSum(int[][] puzzle, int[][] goal)
	{
		int sum=0;
		
		setGoalCoordinates(goal); //store the row and column of each cell of goal state in hashmaps
		
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				int key=puzzle[i][j];
				if(key!=0)
				{
					sum+= Math.abs(i-rows.get(key))+ Math.abs(j-columns.get(key));
				}
			}
		}
		
		this.h=sum;
	}
	
	void setGoalCoordinates(int[][] goal)
	{
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				rows.put(goal[i][j],i);
				columns.put(goal[i][j],j);
			}
		}
	}
	
	void compute_f_of_n(int g,int h)
	{
		f=g+h;
	}


	@Override
	public int compareTo(Node o) {
		// TODO Auto-generated method stub
		if(this.f>o.f)
		{
			return 1;
		}
		else if(this.f<o.f)
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}
	
	public int[][] clonePuzzle()
	{
		int[][] puz=new int[3][3];
		puz=this.puzzle;
		
		return puz;
	}
	
	protected Node clone()
	{
		Node copy=new Node(this.x,this.y,this.puzzle,this.parent,this.g);
		copy.h=this.h;
		copy.f=this.f;
		
		return copy;
	}
}
