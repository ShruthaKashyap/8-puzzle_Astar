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
	
	public Node(int x, int y, int newX, int newY, Node parent,int g, int[][] puz)
	{
		
		for(int f=0;f<3;f++)
		{
			for(int h=0;h<3;h++)
			{
				this.puzzle[f][h]=puz.clone()[f][h];
			}
		}
		
		this.parent=parent;
		this.g=g;
		
		//swap the elements at x,y and newX,newY
		int temp=this.puzzle[x][y];
		this.puzzle[x][y]=this.puzzle[newX][newY];
		this.puzzle[newX][newY]=temp;
		
		this.x=newX;
		this.y=newY;
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
	
}
