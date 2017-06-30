package services.microservices;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Ordering;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;

import model.products.ProductList;
import model.products.SelectedProduct;

public class RecommendationService implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -8735532598538235828L;
	private Integer[][] products;
	private ArrayList<SortedSetMultimap<Integer, Integer>> sorted;
//	private SortedSetMultimap<Integer, Integer> prods;
	@Transactional
	public void initializeProducts(Integer size){
		products = new Integer[size-1][size-1];
		sorted = new ArrayList<SortedSetMultimap<Integer,Integer>>(size-1);
		for(int i=0; i<size-1; i++){
			for(int j=0; j<size-1; j++){
				products[i][j] = 0;
			}
			sorted.add(TreeMultimap.create(Collections.reverseOrder(), Ordering.natural()));
		}
	}

	@Transactional
	public void updateProducts(ProductList pl){
		try{
			for(SelectedProduct sp1 : pl.getAllProducts()){
				Integer looking1 = sp1.getProduct().getId()-1;
				for(SelectedProduct sp2 : pl.getAllProducts()){
					Integer looking2 = sp2.getProduct().getId()-1;
					if(looking1 != looking2){
						sorted.get(looking1).remove(products[looking1][looking2], looking2);
						products[looking1][looking2] += 1;
						sorted.get(looking1).put(products[looking1][looking2], looking2);
					}
				}
			}
		}
		catch(Exception e){
			// System.out.println(e.getMessage());
		}
	}

	//quantity <= products.size()
	@Transactional
	public Collection<Integer> getRecommendationFor(Integer productId, Integer quantity){
		return getListReco(productId-1, quantity);
	}

	@Transactional
	private Collection<Integer> getListReco(Integer productId, Integer quantity) {
		Collection<Integer> res = sorted.get(productId).values();
		for(int i = res.size(); i >= quantity; i--){
			res.remove(i);
		}
		return res;
	}
	@Transactional
	public Integer[][] getProducts() {
		return products;
	}

}
