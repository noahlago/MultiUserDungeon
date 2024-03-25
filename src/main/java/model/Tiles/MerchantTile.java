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
        this.generator = new ItemGeneration();
        this.goods = generator.getSpecificNumberItems(3);

    }

    public void accept(Visitor visitor){
        //Double dispatch, object calls visitors proper method
        visitor.visitMerchantTile(this);
    }

    public List<Item> getGoods(){
        return goods;
    }

    public void setGoods(List<Item> items){
        this.goods = items;
    }

    @Override
    public String toString(){
        return "[ M ]";
    }
    
    
}
