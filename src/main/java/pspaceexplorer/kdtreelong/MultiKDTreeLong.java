package pspaceexplorer.kdtreelong;

import pspaceexplorer.kdtreelong.KDTree;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MultiKDTreeLong<T>
{

	private KDTree<Set<T>> tree;


	public MultiKDTreeLong(int dimensions)
	{
		tree = new KDTree(dimensions);
	}

	public void delete(double[] key)
	{
		throw new NotImplementedException();
	}

	public void insert(double[] key , T object)
	{
		Set<T> bucket = tree.search(key);
		if(bucket != null)
		{
			bucket.add(object);
		}
		else
		{
			bucket = new HashSet<T>();
			bucket.add(object);
			tree.insert(key, bucket);
		}
	}

	public List<T> nearest(double[] key, int n)
	{
		ArrayList<T> nearest = new ArrayList<T>();
		for(Set<T> o : tree.nearest(key, n))
		{
			nearest.addAll(o);
		}
		return nearest;
	}

	public Set<T> nearest(double[] key)
	{
		return (Set<T>) tree.nearest(key);
	}

	public ArrayList<T> range(double[] upperKey, double[] lowerKey)
	{
		ArrayList<T> rangeContents = new ArrayList<T>();
		for(Object o : tree.range(upperKey, lowerKey))
		{
			rangeContents.addAll((Set<T>)o);
		}
		return rangeContents;
	}

	public Set<T> search(double[] key)
	{
		return (Set<T>) tree.search(key);
	}

	public String toString()
	{
		return tree.toString();
	}
}
