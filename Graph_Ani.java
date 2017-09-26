/*Created by : Anirudh Lakshminarayanan
  Date : 22/04/2017
  File Name: Graph.java
  Description : Implemented Dijikstra's Algorithm using min Heap and graph is built
*/

import java.io.FileReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.NoSuchElementException;

//Following is the implementation of binary min heap
class MinHeap
{
    Float[] minheap;
    int size;
    int total;
    int high;
    Vertex[] vertexdist;
 
    private static final int start = 1;

    MinHeap(int high, Vertex source)
    {
        this.high = high;
        this.size = 0;
        this.total = 0;
        minheap = new Float[this.high + 1];
        vertexdist = new Vertex[this.high + 1];
        vertexdist[0]=source;
        minheap[0] = Float.MIN_VALUE;
    }
   
    //code to change the vertex if it does not satisfy heap order
    private void minheapify(int value)
    {
    	Float tmp;

        if (!(value>= (size / 2) && value<=size))
        { 
        	int i = 2*value;
        	int j = (2*value)+1;
        	
			if(i<high+1 && j<high+1)
        	{
        		if ( (minheap[value] > minheap[i]  || minheap[value] > minheap[i]) && i<high+1 && j<high+1)
                {
                    if (minheap[value*2] < minheap[(value*2)+1])
                    {
						tmp = minheap[value];
						minheap[value] = minheap[value*2];
						minheap[value*2] = tmp;
						
						Vertex temp_var;
						temp_var = vertexdist[value];
					
						vertexdist[value] = vertexdist[value*2];
						vertexdist[value*2] = temp_var;
						
						minheapify(value*2);
						
					}
					
					else
					{
						tmp = minheap[value];
						minheap[value] = minheap[(value*2)+1];
						minheap[(value*2)+1] = tmp;
						
						Vertex temp_var;
						temp_var = vertexdist[value];
						vertexdist[value] = vertexdist[(value*2)+1];
						vertexdist[(value*2)+1] = temp_var;
                
            	        minheapify((value*2)+1);
                    }
                }
        	}
        }
    }
	
    //insert vertex into heap
    public void buildheapify(Float element , Vertex v)
    {
		
    	minheap[++size] = element;
    	vertexdist[++total] = v;
        int current = size;
 
        while (minheap[current] < minheap[current/2])
        {
			
        	Float tmp;
            tmp = minheap[current];
            minheap[current] = minheap[current/2];
            minheap[current/2] = tmp;
            
            Vertex temp_var;
            temp_var = vertexdist[current];
            vertexdist[current] = vertexdist[current/2];
            vertexdist[current/2] = temp_var;
            current = current/2;
			
        }	
    }
    
    // to extract the vertex with minimum value
    public Vertex extractminimum()
    {
		
    	Vertex temp = vertexdist[start];   	
    	minheap[start] = minheap[size--];         
        vertexdist[start] = vertexdist[total--]; 
        
        if (!(start >=  (size / 2)  &&  start <= size))
        { 
	
        	int i=(start*2);
        	int j=(start*2)+1;
        	
			if(i<high+1&&j<high+1)
        	{
        		if ( (minheap[start] > minheap[i]  || minheap[start] > minheap[i])&&i<high+1&&j<high+1)
                {
                    if (minheap[(start*2)] < minheap[(start*2)+1])
                    {
						
						Float tmp;
						tmp = minheap[start];
						minheap[start] = minheap[start*2];
						minheap[start*2] = tmp;
						
						Vertex temp_var;
						temp_var = vertexdist[start];
						vertexdist[start] = vertexdist[start*2];
						vertexdist[start*2] = temp_var;
					   
						minheapify((start*2));
						
					}
					
					else
					{
						Float tmp;
						tmp = minheap[start];
						minheap[start] = minheap[(start*2)+1];
						minheap[(start*2)+1] = tmp;
								
						Vertex temp_var;
						temp_var = vertexdist[start];
						vertexdist[start] = vertexdist[(start*2)+1];
						vertexdist[(start*2)+1] = temp_var;
								
						minheapify((start*2)+1);
					}
                }
        	}
            
        }
       
	   return temp;
    }
    
}

class GraphException extends RuntimeException
{
    public GraphException( String name )
    {
        super( name );
    }
}


//Vertex class to store vertex , previous vertex,status and vistied or not
class Vertex
{
    public String     name;   
    public List       adj;    
    public double     dist;   
    public Vertex     prev;   
    public int        scratch;
    public Vertex pos;
    public String status="up";
    public int visited=0;

    public Vertex( String nm )
    {
		name = nm; 
		adj = new LinkedList( ); 
		reset( ); 
	}

    public void reset( )
    {
		dist = Graph.INFINITY; 
		prev = null;
		pos = null;
		scratch = 0;
		visited=0;
	}  
    
}


//Edge class to store destination vertex,cost ans status of the edge
class Edge
{
    public Vertex     dest;   
    public float     cost;   
    public String status="up";
    
	public Edge( Vertex d, float c )
    {
        dest = d;
        cost = c;
    }
}


/*Graph class to build graph,add edge,delete edge,change status of edge or vertex
 ,shortest path using dijikstras,print graph,reachable vertices
*/
public class Graph
{
    public static final double INFINITY = Double.MAX_VALUE;
    private Map<String,Vertex> vertexMap = new HashMap<String,Vertex> ( ); // Maps String to Vertex
	
    /**
     * Add a new edge to the graph.
     */
    
    //adds the edge
    public void addEdge( String sourceName, String destName, float cost )
    {
		
        Vertex v = getVertex( sourceName );
        Vertex w = getVertex( destName );
        v.adj.add( new Edge( w, cost ) );
    
	}
	
    
    // This deletes the edge
    public void deleteEdge( String sourceName, String destName)
    {
        Vertex v = getVertex( sourceName );
        Vertex w = getVertex( destName );
       
		String next="";
        Edge delete = null;
    
		for( Iterator itr = v.adj.iterator( ); itr.hasNext( ); )
        {
            Edge e = (Edge) itr.next( );
            Vertex x = e.dest;
            
			if(w.name==x.name)
			{			
				next="delete";	
				delete = e;       
			}
        }
		
        if(next.equals("delete"))
		{
			v.adj.remove(delete);
		}
    }
    
    // This changes the edge status based on user input
    public void edgestatus(String sourceName,String destName,String Status)
	{	
		Vertex v = getVertex( sourceName );
        Vertex w = getVertex( destName );
        Edge changestatus = null;
     
		for( Iterator itr = v.adj.iterator( ); itr.hasNext( ); )
        {
            Edge e = (Edge) itr.next( );
            Vertex x = e.dest;
        
			if(w.name==x.name)
			{            	
            	changestatus = e;
            }
        }	
		
        changestatus.status=Status;			
    }
	
    /* This Sorts the vertices and calls Reachable funtion to get reachable vertices .
    This also calls printPath_reachable to print the reachable vertices in sorted order.
    */
   
	public void reachableVertex(Graph g)
    {		
		List<String> mapValues = new ArrayList<>(vertexMap.keySet());
		Collections.sort(mapValues);
		
		for(int i=0;i<mapValues.size();i++)
		{
			Vertex start = (Vertex) vertexMap.get( mapValues.get(i) );
			
			if(!(start.status.equals("down")))
			{
				System.out.println(mapValues.get(i));
			
				g.reachable(mapValues.get(i));
				g.printPath_reachable(mapValues.get(i));
			
			}
		}
	}
    
    //sorts the graph and calls printPath_graph to print graph with vertex and edge status if its down
    public void printGraph(Graph g)
    {
		
    	List<String> mapValues = new ArrayList<>(vertexMap.keySet());
    	Collections.sort(mapValues);
		
		for(int i=0;i<mapValues.size();i++)
		{
			
			Vertex start = (Vertex) vertexMap.get( mapValues.get(i) );
			
			if(start.status.equals("down"))
			{
				System.out.println(mapValues.get(i)+" down");
			}
			
			else
			{
				System.out.println(mapValues.get(i));
			}
			
			g.printPath_graph(mapValues.get(i));
		}
	}
	
    // This changes the vertex status based on the user input
    public void vertexstatus(String sourceName,String Status)
	{
		
		Vertex v = getVertex( sourceName );
		v.status=Status;
    
	}
   
	public void printPath( String destName )
    {
		
    	DecimalFormat df = new DecimalFormat("#.00000");
    	Vertex w = (Vertex) vertexMap.get( destName );
        
		if( w == null )
            throw new NoSuchElementException( "Destination vertex not found" );
        
		else if( w.dist == INFINITY )
            System.out.println( destName + " is unreachable" );
		
		else
        {
            printPath( w );
            System.out.print( " "+ df.format(w.dist)  );
            System.out.println( );
        }		
    }

    //prints the graph with status in sorted order
    public void printPath_graph(String source)
    {
		
		List<String> mapValues = new ArrayList<>(vertexMap.keySet());
		Collections.sort(mapValues);
		
		Vertex v = (Vertex) vertexMap.get( source );
		
		for(int i=0;i<mapValues.size();i++)
		{
			Vertex w = (Vertex) vertexMap.get( mapValues.get(i) );
			
			for( Iterator itr = v.adj.iterator( ); itr.hasNext( ); )
			{
				Edge e=(Edge) itr.next( );
				Vertex dest=e.dest;
				
				if(w.name==dest.name)
				{
					if((e.status.equals("down")))
					{
						System.out.println(" "+w.name+" "+e.cost+" down");
					}
					
					else
					{
						System.out.println(" "+w.name+" "+e.cost);
					}
				
				}
			}
		}
    }
    
    //prints reachable vertices in sorted order
    public void printPath_reachable(String source)
    {
		List<String> mapValues = new ArrayList<>(vertexMap.keySet());
		Collections.sort(mapValues);
		Vertex start = (Vertex) vertexMap.get( source );
		
		for(int i=0;i<mapValues.size();i++)
		{
			
			Vertex w = (Vertex) vertexMap.get( mapValues.get(i) );
			
			if(w!=null && w.visited==1&&start!=w)
			{
				System.out.println(" "+ mapValues.get(i) );
			}
		}
    }
    
    //calculates reachable vertices for the vertex which is passed
    public void reachable( String startName )
    {
        
		clearAll( ); 

        Vertex start = vertexMap.get( startName );
        if( start == null )
            throw new NoSuchElementException( "Start vertex not found" );

        Queue<Vertex> q = new LinkedList<Vertex>( );
        q.add( start ); start.visited = 1;

        while( !q.isEmpty( ) )
        {
			
            Vertex v = q.remove( );

            for( Iterator itr = v.adj.iterator( ); itr.hasNext( ); )
            {
                Edge e = (Edge) itr.next( );
                Vertex w = e.dest;
                if( w.visited == 0&&!(e.status.equals("down"))&&!(w.status.equals("down")) )
                {
                    w.visited=1;
                    q.add( w );
                }
            }
        }
    }
    
    //returns the vertex object if the vertex name is passed 
    private Vertex getVertex( String vertexName )
    {
        
		Vertex v = (Vertex) vertexMap.get( vertexName );
        
		if( v == null )
        {
            v = new Vertex( vertexName );
            vertexMap.put( vertexName, v );
        }
        
		return v;
    }

    //prints the path between two vertices
    private void printPath( Vertex dest )
    {
		
        if( dest.prev != null )
        {
			
            printPath( dest.prev );
            System.out.print( " " );
       
   	    }
        
		System.out.print( dest.name );
    }
    
    //calls reset function for each vertex
    private void clearAll( )
    {
        for( Iterator itr = vertexMap.values( ).iterator( ); itr.hasNext( ); )
            ( (Vertex)itr.next( ) ).reset( );
    }

    
	// Dijkstra Implementation using Priority Queue and Min Heap
    public void dijkstra( String source,Graph graph)
    {
		
    	Vertex dest;	
    	Vertex start;
    	start=graph.getVertex(source);
		MinHeap min=new MinHeap(vertexMap.size(),start);			
		clearAll();
		start.dist=0.0;
	
		List<Vertex> set=new ArrayList<Vertex>();
		List<Vertex> mapValues = new ArrayList<>(vertexMap.values());
	 
		for(int i=0;i<mapValues.size();i++)
		{	 
			min.buildheapify((float)mapValues.get(i).dist,mapValues.get(i) );
		}
		
		while(min.size>0)
		{	
	
			dest = min.extractminimum();							
			set.add(dest);					
				
			for( Iterator itr = dest.adj.iterator( ); itr.hasNext( ); )
			{
				
				Edge e = (Edge) itr.next( );
				Vertex w = e.dest;
				double cvw = e.cost;
							
				if( cvw < 0 )
					throw new GraphException( "Graph has negative edges" );
								
				if( w.dist > dest.dist + cvw && !(e.status.equals("down")) && !(dest.status.equals("down")) && !(w.status.equals("down")))
				{
					w.dist = dest.dist +cvw;
					w.prev = dest;	
				}
			}
				
			for(int i=1;i<min.high;i++)
			{
				
				min.minheap[i]=(float) 0.0;
				min.vertexdist[i]=null;
				min.size=0;
				min.total=0;
				
			}
				
			for(int i=0;i<mapValues.size();i++)
			{
				if(!set.contains(mapValues.get(i)))
				{
					min.buildheapify((float)mapValues.get(i).dist, mapValues.get(i));
				}
			}				
		}			
    }

    /* This function processes the user input.acceptable values are
      1.addedge dest,source
      2.deleteedge dest,source
      3.edgedown dest,source
      4.edgeup dest,source
      5.vertexdown vertex
      6.vertexup vertex
      7.reachable
      8.print
      9.path source dest
      10.exit
      
      
      Program gets terminated on input "exit"
    */
    public static boolean processRequest( BufferedReader in, Graph g )
    {
     
		String startName = null;
        String destName = null;
        String alg = null;

        try
        {
			
            System.out.print( "Enter action with parameters" );            
            String input = in.readLine( );
            StringTokenizer value=new StringTokenizer(input);
            String action=value.nextToken(); 
            
            if(action.equals("path"))
            {
				
            	String source=value.nextToken();
            	String destination=value.nextToken();
            	
            	g.dijkstra( source,g );
            	g.printPath( destination );
				
            }
            
            if(action.equals("addedge")) // ADD EDGE 
            {
				
            	String source=value.nextToken();
            	String destination=value.nextToken();
            	
            	Vertex v=g.getVertex(source);
            	Vertex w=g.getVertex(destination);
            	String next="";
            	
            	Float cost=Float.parseFloat( value.nextToken( ) );
            	
				for( Iterator itr = v.adj.iterator( ); itr.hasNext( ); )
                {
					Edge e = (Edge) itr.next( );
					Vertex x = e.dest;
					
					if(x.name==w.name)
					{
						next="delete";	
					}
                }
				
            	if(next.equals("delete"))
				{
					g.deleteEdge( source,destination);
            	}
            	
				g.addEdge( source, destination, cost );
            }
            
			if(action.equals("deleteedge"))
            {
            	
				String source=value.nextToken();
				String destination=value.nextToken();            	
            	g.deleteEdge( source,destination);			
            
			}
			
            if(action.equals("edgedown")) // EDGE DOWN
            {
            
				String source=value.nextToken();            	
				String destination=value.nextToken();           	
            	g.edgestatus( source,destination,"down");
            
			}
            
			if(action.equals("edgeup")) // EDGE UP
            {
            	
				String source=value.nextToken();            	
				String destination=value.nextToken();            	
            	g.edgestatus( source,destination,"up");
				
            }
			
            if(action.equals("vertexdown")) // VERTEX DOWN
            {	
            
				String source=value.nextToken();
            	g.vertexstatus( source,"down");
            
			}
			
            if(action.equals("vertexup")) // VERTEX UP
            {
            	String source=value.nextToken();
            	g.vertexstatus( source,"up");
            }
			
			if(action.equals("reachable")) // REACHABLE 
            {
            	g.reachableVertex(g);	
            }
            
			if(action.equals("print")) // PRINTING THE GRAPH
            {
				g.printGraph(g);
            }
			
            if(action.equals("quit")) // QUIT
            {
				return false;	
            }

        }
        
		catch( IOException e )
        { System.err.println( e ); }
        
		catch( NoSuchElementException e )
        { System.err.println( e ); }          
        
		catch( GraphException e )
        { System.err.println( e ); }
        
		return true;   
	}

	//main class   
	public static void main( String [ ] args )
    {
        Graph g = new Graph( );
       
	    try
        {
            FileReader fin = new FileReader( args[0] );
            BufferedReader graphFile = new BufferedReader( fin );    
            String line;
            
			while( ( line = graphFile.readLine( ) ) != null )
            {
                StringTokenizer st = new StringTokenizer( line );

                try
                {
                    if( st.countTokens( ) != 3 )
                    {                  
						System.err.println( "Skipping ill-formatted line " + line );
                        continue;                   
					}
					
                    String source  = st.nextToken( ); 
                    String dest    = st.nextToken( );
                    Float  cost    = Float.parseFloat( st.nextToken( ) );
                    g.addEdge( source, dest, cost );
                    g.addEdge( dest, source, cost );
                    
                }
				
                catch( NumberFormatException e )
                {
					System.err.println( "Skipping ill-formatted line " + line ); 
				}
            }
        }
		
        catch( IOException e )
        {
			System.err.println( e );
		}

        System.out.println( "File read..." );
        System.out.println( g.vertexMap.size( ) + " vertices" );

        BufferedReader in = new BufferedReader( new InputStreamReader( System.in ) );
        while( processRequest( in, g ) )
            ;
    }
}