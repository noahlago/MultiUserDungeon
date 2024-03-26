package model.Tiles;

import model.Item;
import model.ItemGeneration;
import model.Visitor;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

public class MerchantTile extends ConcreteTile{
    @JsonProperty("goods") private List<Item> goods;
    private ItemGeneration generator;

    @JsonCreator
    public MerchantTile(){
        //declares new item generator
        this.generator = new ItemGeneration();
        
        //item generator gives 3 random items
        this.goods = generator.getSpecificNumberItems(3);
    }

    public void accept(Visitor visitor){
        //Double dispatch, object calls visitors proper method
        visitor.visitMerchantTile(this);
    }

    public List<Item> getGoods(){
        return goods;
    }

    public Item getGood(int index){
        return goods.get(index);
    }

    public void removeItem(int index){
        goods.remove(index);
    }

    public void setGoods(List<Item> items){
        this.goods = items;
    }

    @Override
    public String toString(){
        return "[ M ]";
    }
    
    
}
