package org.jeometry.simple.geom3D.neighbor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeometry.Jeometry;
import org.jeometry.geom3D.mesh.indexed.IndexedFace;
import org.jeometry.geom3D.neighbor.AdjacencyMap;
import org.jeometry.geom3D.point.Point3D;

/**
 * A simple implementation of an {@link AdjacencyMap adjacency map}.
 * @param <T> The type of underlying 3D points
 * @author Julien Seinturier - COMEX S.A. - <a href="mailto:contact@jorigin.org">contact@jorigin.org</a> - <a href="https://github.com/jorigin/jeometry">https://github.com/jorigin/jeometry</a>
 * @version {@value Jeometry#version} build {@value Jeometry#BUILD}
 * @since 1.0.0
 */
public class SimpleIndexedAdjencyMap<T extends Point3D> implements AdjacencyMap<T> {

	/**
	 * The map.
	 */
	private Map<IndexedFace<T>, List<IndexedFace<T>>> map = null;

	@Override
	public List<IndexedFace<T>> getAdjacencies(IndexedFace<T> face) {
		return map.get(face);
	}

	@Override
	public void setAdjacencies(IndexedFace<T> face, List<IndexedFace<T>> adjacencies) {
		map.put(face, adjacencies);
	}

	@Override
	public boolean addAdjacent(IndexedFace<T> face, IndexedFace<T> adjacent) {

		List<IndexedFace<T>> l = map.get(face);

		if (l == null){
			l = new ArrayList<IndexedFace<T>>();
			map.put(face, l);
		}

		return l.add(adjacent);
	}

	@Override
	public boolean removeAdjacent(IndexedFace<T> face, IndexedFace<T> adjacent) {
		List<IndexedFace<T>> l = map.get(face);

		if (l != null){
			return l.remove(adjacent);
		}

		return false;
	}

	/**
	 * Create a new empty adjacency map.
	 */
	public SimpleIndexedAdjencyMap(){
		map = new HashMap<IndexedFace<T>,List<IndexedFace<T>>>();
	}
}
