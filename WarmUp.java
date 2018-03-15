import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.alg.NaiveLcaFinder;
import org.jgrapht.io.DOTImporter;
import org.jgrapht.io.ImportException;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class WarmUp 
{
    public static void main(String[] args)throws IOException, ImportException  {
        
	if(args.length>3){
	    System.out.println("Taking First Two Input Names!");}
	if(args.length>=3)
	{
	    File file = new File(args[0]);
	    if(file.exists() && file.isFile())
		getAns(file,args[1],args[2]);
	    else
		System.out.println("File Does Not Exist Or Is A Directory!");
	}
	else 
	    System.out.println("Invalid Input!\nInput Format-<DotFileName> <PersonName1> <PersonName2>");
    }
    public static void getAns(File file, String person1, String person2) throws IOException, ImportException
    {
		BufferedReader filereader = new BufferedReader(new FileReader(file));
                DOTImporter<String, DefaultEdge> importer=new DOTImporter<>((id, map) -> id, (from, to, label, map) -> new DefaultEdge());
        	Graph<String, DefaultEdge> family=new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
        	importer.importGraph(family, filereader);
		
                if (!family.containsVertex(person1))
                    System.out.println(person1+" Does Not Exist");
                else if (!family.containsVertex(person2))
                    System.out.println(person2+" Does Not Exist");
                else if(person1.equals(person2))
                    System.out.println("Same Names!");
                else 
		{
		    NaiveLcaFinder<String, DefaultEdge> lcaFinder = new NaiveLcaFinder<>(family);
                    Set<String> ancestors = null;
		    try 
		    {
            		ancestors = lcaFinder.findLcas(person1, person2);
        	    } 
		    catch (IllegalArgumentException e) 
		    {
                	    System.err.println("error while finding lca");
            		    System.exit(1);
        	    }
                    if (ancestors.size() == 0)
                        System.out.println("No Common Ancestor(s) Of "+person1+" and "+person2);
                    else
                        System.out.print("Lowest common Ancestor(s) of "+person1+" and "+person2+" :- "+Arrays.toString(ancestors.toArray(new String[0])));
                }
    }
}

