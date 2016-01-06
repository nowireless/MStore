package org.nowireless.common.massive;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.nowireless.common.massive.collections.MassiveList;
import org.nowireless.common.massive.collections.MassiveSet;
import org.nowireless.common.massive.collections.MassiveTreeSet;


public class MUtil
{
	public final static Random random = new Random();

	
	
	// -------------------------------------------- //
	// UUID
	// -------------------------------------------- //
	
	public static UUID asUuid(String string)
	{
		// Null
		if (string == null) return null;
		
		// Avoid Exception
		if (string.length() != 36) return null;
		
		// Try
		try
		{
			return UUID.fromString(string);
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	public static boolean isUuid(String string)
	{
		return asUuid(string) != null;
	}
	
	// -------------------------------------------- //
	// IS(NT) NPC, SENDER, PLAYER
	// -------------------------------------------- //
	
	
	
	// -------------------------------------------- //
	// STACK TRACE: GET
	// -------------------------------------------- //
	
	public static List<StackTraceElement> getStackTrace(Thread thread, int skip)
	{
		// Cut away this method.
		// Cut away system line.
		// We must check if it's the current thread.
		if (thread.equals(Thread.currentThread())) skip += 2;
		
		StackTraceElement[] elements = thread.getStackTrace();
		elements = Arrays.copyOfRange(elements, skip, elements.length);
		return new ArrayList<StackTraceElement>(Arrays.asList(elements));
	}
	
	public static List<StackTraceElement> getStackTrace(Thread thread)
	{
		int skip = 0;
		
		// Cut away this method.
		// We must check if it's the current thread.
		if (thread.equals(Thread.currentThread())) skip += 1;
		
		return getStackTrace(thread, skip);
	}
	
	public static List<StackTraceElement> getStackTrace(int skip)
	{
		// Cut away this method.
		// We already know it's going to be the current thread.
		skip++;
		
		Thread thread = Thread.currentThread();
		
		return getStackTrace(thread, skip);
	}
	
	public static List<StackTraceElement> getStackTrace()
	{
		// Cut away this method.
		// We already know it's going to be the current thread.
		int skip = 1;
		
		Thread thread = Thread.currentThread();
		
		return getStackTrace(thread, skip);
	}

	
	// -------------------------------------------- //
	// LIST OPERATIONS
	// -------------------------------------------- //
	
	public static <T> List<T> repeat(T object, int times)
	{
		List<T> ret = new ArrayList<T>(times);
		for (int i = 1; i <= times; i++)
		{
			ret.add(object);
		}
		return ret;
	}
	
	public static void keepLeft(List<?> list, int maxlength)
	{
		if (list.size() <= maxlength) return;
		list.subList(maxlength, list.size()).clear();
	}
	
	public static void keepRight(List<?> list, int maxlength)
	{
		if (list.size() <= maxlength) return;
		list.subList(0, maxlength).clear();
	}
	
	public static <T> void padLeft(List<T> list, T object, int length)
	{
		if (list.size() >= length) return;
		list.addAll(0, repeat(object, length - list.size()));
	}
	
	public static <T> void padRight(List<T> list, T object, int length)
	{
		if (list.size() >= length) return;
		list.addAll(repeat(object, length - list.size()));
	}
	
	// -------------------------------------------- //
	// MAP OPERATIONS
	// -------------------------------------------- //
	
	public static void keepLeft(Map<?, ?> map, int maxSize)
	{
		int i = 0;
		Iterator<?> iter = map.entrySet().iterator();
		while (iter.hasNext())
		{
			iter.next();
			i++;
			if (i > maxSize) iter.remove();
		}
	}
	
	// -------------------------------------------- //
	// ITERABLE MATH
	// -------------------------------------------- //
	
	public static <T extends Number> double getSum(Iterable<T> numbers)
	{
		if (numbers == null) throw new NullPointerException("numbers");
		
		double sum = 0;
		for (T number : numbers)
		{
			sum += number.doubleValue();
		}
		
		return sum;
	}
	
	public static <T extends Number> double getAverage(Iterable<T> numbers)
	{
		if (numbers == null) throw new NullPointerException("numbers");
		
		double sum = 0;
		int count = 0;
		for (T number : numbers)
		{
			sum += number.doubleValue();
			count++;
		}
		
		if (count == 0) throw new IllegalArgumentException("numbers empty");
		
		return sum / count;
	}
	
	// -------------------------------------------- //
	// TABLE OPERATIONS
	// -------------------------------------------- //
	
	public static <T> List<List<T>> rotateLeft(List<List<T>> rows)
	{
		List<List<T>> ret = transpose(rows);
		flipVertically(ret);
		return ret;
	}
	
	public static <T> List<List<T>> rotateRight(List<List<T>> rows)
	{
		List<List<T>> ret = transpose(rows);
		flipHorizontally(ret);
		return ret;
	}
	
	public static <T> List<List<T>> transpose(List<List<T>> rows)
	{
		List<List<T>> ret = new ArrayList<List<T>>();
		
		final int n = rows.get(0).size();
		
		for (int i = 0; i < n; i++)
		{
			List<T> col = new ArrayList<T>();
			for (List<T> row : rows)
			{
				col.add(row.get(i));
			}
			ret.add(col);
		}
		
		return ret;
	}
	
	public static <T> void flipHorizontally(List<List<T>> rows)
	{
		for (List<T> row : rows)
		{
			Collections.reverse(row);
		}
	}
	
	public static <T> void flipVertically(List<List<T>> rows)
	{
		Collections.reverse(rows);
	}
	
	// -------------------------------------------- //
	// COLOR INT CODE
	// -------------------------------------------- //
	
	
	
	// -------------------------------------------- //
	// ENTITY DAMAGE EVENT
	// -------------------------------------------- //
	// The EntityDamageEvent#setDamge(double damage) is somehow broken.
	// The MAGIC modifier does not scale as one would expect it to.
	// It jumps all over the place every time the method is called.
	// And finally it has accumulated so much randomness that players may suddenly be healed instead of harmed.
	// Or they may instantly die. For this reason we take inspiration from MCMMO who rolled their own setDamage function.
	// This method sets the BASE damage modifier and scales all other modifiers proportionally.
	
	
	
	// -------------------------------------------- //
	// GET IP
	// -------------------------------------------- //
	
	
	
	// -------------------------------------------- //
	// PICK
	// -------------------------------------------- //
	
	public static <T> T regexPickFirstVal(String input, Map<String, T> regex2val)
	{
		if (regex2val == null) return null;
		T ret = null;
		
		for (Entry<String, T> entry : regex2val.entrySet())
		{
			ret = entry.getValue();
			if (input == null) continue;
			String regex = entry.getKey();
			if (Pattern.matches(regex, input)) break;
		}
		
		return ret;
	}
	
	public static <E, T> T equalsPickFirstVal(E input, Map<E, T> thing2val)
	{
		if (thing2val == null) return null;
		T ret = null;
		
		for (Entry<E, T> entry : thing2val.entrySet())
		{
			ret = entry.getValue();
			if (input == null) continue;
			E thing = entry.getKey();
			if (MUtil.equals(input, thing)) break;
		}
		
		return ret;
	}
	
	public static <T> T recurseResolveMap(T input, Map<T, T> map)
	{
		T output = map.get(input);
		if (output == null) return input;
		return recurseResolveMap(output, map);
	}
	
	// -------------------------------------------- //
	// SIMPLE CONSTRUCTORS
	// -------------------------------------------- //
	
	@SafeVarargs
	public static <T> List<T> list(T... items)
	{
		return new MassiveList<T>(Arrays.asList(items));
	}
	
	@SafeVarargs
	public static <T> Set<T> set(T... items)
	{
		return new MassiveSet<T>(Arrays.asList(items));
	}
	
	public static Set<String> treeset(String... items)
	{
		return new MassiveTreeSet<String, CaseInsensitiveComparator>(CaseInsensitiveComparator.get(), Arrays.asList(items));
	}
	
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> map(K key1, V value1, Object... objects)
	{
		Map<K, V> ret = new LinkedHashMap<K, V>();
		
		ret.put(key1, value1);
		
		Iterator<Object> iter = Arrays.asList(objects).iterator();
		while (iter.hasNext())
		{
			K key = (K) iter.next();
			V value = (V) iter.next();
			ret.put(key, value);
		}
		
		return ret;
	}
	
	public static <K, V> Map<V, K> flippedMap(Map<K, V> map)
	{
		Map<V, K> ret = new LinkedHashMap<V, K>();
		
		for(Entry<K, V> entry : map.entrySet())
		{
			V value = entry.getValue();
			K key = entry.getKey();
			
			if (value == null) continue;
			ret.put(value, key);
		}
		
		return ret;
	}
	
	public static <K, V> Map<V, Set<K>> reverseIndex(Map<K, V> map)
	{
		Map<V, Set<K>> ret = new LinkedHashMap<V, Set<K>>();
		
		for (Entry<K, V> entry : map.entrySet())
		{
			K key = entry.getKey();
			V value = entry.getValue();
			
			Set<K> set = ret.get(value);
			if (set == null)
			{
				set = new HashSet<K>();
				ret.put(value, set);
			}
			set.add(key);
		}
		
		return ret;
	}
	
	// -------------------------------------------- //
	// COLLECTION MANIPULATION
	// -------------------------------------------- //
	
	public static <T> T removeByIndex(Collection<T> coll, int index)
	{
		if (coll == null) throw new NullPointerException("coll");
		
		if (coll instanceof List<?>)
		{
			return ((List<T>)coll).remove(index);
		}
		
		if (index < 0) throw new IndexOutOfBoundsException("index < 0");
		if (index >= coll.size()) throw new IndexOutOfBoundsException("index > collection size");
		
		int i = -1;
		Iterator<T> iter = coll.iterator();
		while (iter.hasNext())
		{
			i++;
			T ret = iter.next();
			if (i != index) continue;
			iter.remove();
			return ret;
		}
		
		return null;
	}
	
	// -------------------------------------------- //
	// LE NICE RANDOM
	// -------------------------------------------- //
	
	public static <T> T random(Collection<T> coll)
	{
		if (coll.size() == 0) return null;
		if (coll.size() == 1) return coll.iterator().next();
		
		List<T> list = null;
		if (coll instanceof List<?>)
		{
			list = (List<T>)coll;
		}
		else
		{
			list = new ArrayList<T>(coll);
		}
		
		int index = random.nextInt(list.size());
		return list.get(index);
	}
	
	public static <T> List<T> randomSubset(Collection<T> coll, int count)
	{
		// Clean Input
		if (count < 0) count = 0;
		
		// Create Ret
		List<T> ret = new ArrayList<T>(coll);
		while (ret.size() > count)
		{
			int index = random.nextInt(ret.size());
			ret.remove(index);
		}
		
		// Return Ret
		return ret;
	}
	
	public static <E> List<E> random(List<E> list, int count)
	{
		// Create Ret
		List<E> ret = new MassiveList<E>();
		
		// Empty
		if (list.isEmpty()) return ret;
		
		// Fill Ret
		for (int i = 0; i < count; i++)
		{
			ret.add(MUtil.random(list));
		}
		
		// Return Ret
		return ret;
	}
	
	// -------------------------------------------- //
	// LE NICE EQUALS and compare
	// -------------------------------------------- //
	
	public static boolean equals(Object herp, Object derp)
	{
		if (herp == null) return derp == null;
		if (derp == null) return false;
		return herp.equals(derp);
	}
	
	public static <T> int compare(Comparable<T> herp, T derp)
	{
		Integer ret = compareNulls(herp, derp);
		if (ret != null) return ret;
		return herp.compareTo(derp);
	}
	
	public static Integer compareNulls(Object one, Object two)
	{
		if (one == null && two == null) return 0;
		if (one == null) return -1;
		if (two == null) return +1;
		return null;
	}
	
	public static Integer compareWithList(Object one, Object two, List<? extends Object> list)
	{
		int oneIndex = list.indexOf(one);
		int twoIndex = list.indexOf(two);
		if (oneIndex != -1 && twoIndex != -1) return oneIndex - twoIndex;
		if (oneIndex != -1) return -1;
		if (twoIndex != -1) return +1;
		return null;
	}
	
	// -------------------------------------------- //
	// SORTING
	// -------------------------------------------- //
	
	//http://stackoverflow.com/questions/2864840/treemap-sort-by-value
	/*public static <K,V extends Comparable<? super V>> SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
		SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
			new Comparator<Map.Entry<K,V>>() {
				@Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
					int res = e1.getValue().compareTo(e2.getValue());
					return res != 0 ? res : 1; // Special fix to preserve items with equal values
				}
			}
		);
		sortedEntries.addAll(map.entrySet());
		return sortedEntries;
	}*/
	
	// http://stackoverflow.com/questions/2864840/treemap-sort-by-value
	public static <K,V extends Comparable<? super V>> SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map)
	{
		return entriesSortedByValues(map, true);
	}
	
	public static <K,V extends Comparable<? super V>> SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map, final boolean ascending)
	{
		SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
			new Comparator<Map.Entry<K,V>>()
			{
				@Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2)
				{
					int res;
					if (ascending)
					{
						res = e1.getValue().compareTo(e2.getValue());
					}
					else
					{
						res = e2.getValue().compareTo(e1.getValue());
					}
					return res != 0 ? res : 1;
				}
			}
		);
		sortedEntries.addAll(map.entrySet());
		return sortedEntries;
	}
	
	// -------------------------------------------- //
	// MATH
	// -------------------------------------------- //
	public static <T extends Number> T limitNumber(T d, T min, T max)
	{
		if (min instanceof Number && d.doubleValue() < min.doubleValue())
		{
			return min;
		}
		
		if (max instanceof Number && d.doubleValue() > max.doubleValue())
		{
			return max;
		}
		
		return d;
	}
	
	public static long probabilityRound(double val)
	{
		long ret = (long) Math.floor(val);
		double prob = val % 1;
		if (random.nextDouble() < prob) ret += 1;
		return ret;
	}
	
	public static double round(double value, int places)
	{
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	// -------------------------------------------- //
	// EXTRACTION
	// -------------------------------------------- //
	
	protected static Map<Class<?>, Map<String, Set<Extractor>>> classesPropertiesExtractors = new HashMap<Class<?>, Map<String, Set<Extractor>>>();
	protected static Map<String, Set<Extractor>> getPropertiesExtractors(Class<?> propertyClass)
	{
		Map<String, Set<Extractor>> ret = classesPropertiesExtractors.get(propertyClass);
		if (ret == null)
		{
			ret = new HashMap<String, Set<Extractor>>();
			classesPropertiesExtractors.put(propertyClass, ret);
		}
		return ret;
	}
	
	protected static Set<Extractor> getExtractors(Class<?> propertyClass, String propertyName)
	{
		Map<String, Set<Extractor>> propertiesExtractors = getPropertiesExtractors(propertyClass);
		Set<Extractor> ret = propertiesExtractors.get(propertyName);
		if (ret == null)
		{
			ret = new HashSet<Extractor>();
			propertiesExtractors.put(propertyName, ret);
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T extract(Class<T> propertyClass, String propertyName, Object o)
	{
		Object ret = null;
		for (Extractor extractor : getExtractors(propertyClass, propertyName))
		{
			ret = extractor.extract(o);
			if (ret != null) break;
		}
		return (T) ret;
	}
	
	public static <T> void registerExtractor(Class<T> clazz, String propertyName, Extractor extractor)
	{
		getExtractors(clazz, propertyName).add(extractor);
	}
}
	